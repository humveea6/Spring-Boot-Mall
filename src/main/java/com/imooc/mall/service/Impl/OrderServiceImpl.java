package com.imooc.mall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.dao.OrderMapper;
import com.imooc.mall.dao.OrderitemMapper;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.dao.ShippingMapper;
import com.imooc.mall.enums.OrderStatusEnum;
import com.imooc.mall.enums.PaymentTypeEnum;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.pojo.*;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.service.IOrderService;
import com.imooc.mall.vo.OrderItemVo;
import com.imooc.mall.vo.OrderVo;
import com.imooc.mall.vo.ResponseVo;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 2020-05-04
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderitemMapper orderitemMapper;

    @Override
    @Transactional
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {

        ShippingExample shippingExample = new ShippingExample();
        shippingExample.createCriteria()
                .andUserIdEqualTo(uid)
                .andIdEqualTo(shippingId);
        List<Shipping> shippingList = shippingMapper.selectByExample(shippingExample);

        if(CollectionUtils.isEmpty(shippingList)){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        Shipping shipping = shippingList.get(0);

        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }

        List<Integer> productIdList = cartList.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        ProductExample productExample = new ProductExample();
        productExample.createCriteria()
                .andIdIn(productIdList);
        List<Product> productList = productMapper.selectByExample(productExample);
        Map<Integer, Product> productMap = productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product,(n1,n2)->n1));

        List<Orderitem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();
        for (Cart cart : cartList) {
            //根据productId查数据库
            Product product = productMap.get(cart.getProductId());
            //是否有商品
            if (product == null) {
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "商品不存在. productId = " + cart.getProductId());
            }
            //商品上下架状态
            if (!product.getStatus().equals(1)) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,
                        "商品不是在售状态. " + product.getName());
            }

            //库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR,
                        "库存不正确. " + product.getName());
            }

            Orderitem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);

            //减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);

        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        for (Orderitem orderitem : orderItemList) {
            int i = orderitemMapper.insertSelective(orderitem);
            if (i <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }

        for (Cart cart : cartList) {
            cartService.deleteCart(uid, cart.getProductId());
        }

        //构造orderVo
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria()
                .andUserIdEqualTo(uid);
        List<Order> orderList = orderMapper.selectByExample(orderExample);

        List<Long> orderNoList = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toList());

        OrderitemExample orderitemExample = new OrderitemExample();
        orderitemExample.createCriteria()
                .andOrderNoIn(orderNoList);
        List<Orderitem> orderitemList = orderitemMapper.selectByExample(orderitemExample);
        Map<Long, List<Orderitem>> orderItemMap = orderitemList.stream()
                .collect(Collectors.groupingBy(Orderitem::getOrderNo));

        List<Integer> shippingIdList = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toList());
        ShippingExample shippingExample = new ShippingExample();
        shippingExample.createCriteria()
                .andIdIn(shippingIdList);
        List<Shipping> shippingList = shippingMapper.selectByExample(shippingExample);

        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId, Function.identity()));

        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order,
                    orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVoList);

        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {

        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria()
                .andOrderNoEqualTo(orderNo);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        if(CollectionUtils.isEmpty(orders)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Order order = orders.get(0);
        if(!order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }

        OrderitemExample orderitemExample = new OrderitemExample();
        orderitemExample.createCriteria()
                .andOrderNoEqualTo(orderNo);
        List<Orderitem> orderitemList = orderitemMapper.selectByExample(orderitemExample);

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderitemList, shipping);

        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria()
                .andOrderNoEqualTo(orderNo);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        if(CollectionUtils.isEmpty(orders)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Order order = orders.get(0);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //只有[未付款]订单可以取消，看自己公司业务
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }

        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.successByMsg();
    }

    @Override
    public void paid(Long orderNo) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria()
                .andOrderNoEqualTo(orderNo);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        if(CollectionUtils.isEmpty(orders)){
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc() + "订单id:" + orderNo);
        }
        Order order = orders.get(0);
        if (order == null) {
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc() + "订单id:" + orderNo);
        }
        //只有[未付款]订单可以变成[已付款]，看自己公司业务
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getDesc() + "订单id:" + orderNo);
        }

        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPaymentTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            throw new RuntimeException("将订单更新为已支付状态失败，订单id:" + orderNo);
        }
    }

    private OrderVo buildOrderVo(Order order, List<Orderitem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(orderItemVoList);

        if (shipping != null) {
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }

        return orderVo;
    }

    private Order buildOrder(Integer uid,
                             Long orderNo,
                             Integer shippingId,
                             List<Orderitem> orderItemList
    ) {
        BigDecimal payment = orderItemList.stream()
                .map(Orderitem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        return order;
    }

    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private Orderitem buildOrderItem(Integer uid, Long orderNo, Integer quantity, Product product) {
        Orderitem item = new Orderitem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
