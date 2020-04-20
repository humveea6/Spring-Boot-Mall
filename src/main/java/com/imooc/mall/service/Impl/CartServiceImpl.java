package com.imooc.mall.service.Impl;

import com.google.gson.Gson;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.pojo.ProductExample;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.vo.CartProductVo;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-19
 */
@Service
public class CartServiceImpl implements ICartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> addCart(Integer uid,CartAddForm cartAddForm) {

        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());

        //商品是否存在
        if(product == null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否在售
        if(!product.getStatus().equals(1)){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //库存是否充足
        if(product.getStock() <= 0){
            return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR);
        }

        //验证通过，写入redis
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        Cart cart;
        if(StringUtils.isNullOrEmpty(value)){
            cart = new Cart(product.getId(),1,cartAddForm.getSelected());
        }else {
            cart = gson.fromJson(value,Cart.class);
            cart.setQuantity(cart.getQuantity() + 1);
        }

        opsForHash.put(redisKey,
                String.valueOf(product.getId()),
                gson.toJson(cart));

        return listCart(uid);
    }

    @Override
    public ResponseVo<CartVo> listCart(Integer uid) {
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Integer> productIdList = new ArrayList<>();
        for(Map.Entry<String,String> entry:entries.entrySet()){
            Integer productId = Integer.valueOf(entry.getKey());
            productIdList.add(productId);
        }

        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andIdIn(productIdList);
        List<Product> productList = productMapper.selectByExample(productExample);

        Boolean selectAll = true;
        Integer totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(Product product:productList){
            Cart cart = gson.fromJson(entries.get(product.getId().toString()),Cart.class);

            totalQuantity += cart.getQuantity();

            if(!cart.getProductSelected()){
                selectAll = false;
            }

            CartProductVo cartProductVo = new CartProductVo(product.getId(),
                    cart.getQuantity(),
                    product.getName(),
                    product.getSubtitle(),
                    product.getMainImage(),
                    product.getPrice(),
                    product.getStatus(),
                    product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                    product.getStock(),
                    cart.getProductSelected());
            if(cartProductVo.getProductSelected()) {
                totalPrice = totalPrice.add(cartProductVo.getProductTotalPrice());
            }
            cartProductVoList.add(cartProductVo);
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(totalQuantity);
        cartVo.setCartTotalPrice(totalPrice);

        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> updateCart(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));

        Cart cart;
        if(StringUtils.isNullOrEmpty(value)){
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }else {
            cart = gson.fromJson(value, Cart.class);
            if(cartUpdateForm.getQuantity() != null &&cartUpdateForm.getQuantity() > 0) {
                cart.setQuantity(cartUpdateForm.getQuantity());
            }
            if(cartUpdateForm.getSelected() != null){
                cart.setProductSelected(cartUpdateForm.getSelected());
            }

            opsForHash.put(redisKey,
                    String.valueOf(productId),
                    gson.toJson(cart));
        }

        return listCart(uid);
    }

    @Override
    public ResponseVo<CartVo> deleteCart(Integer uid, Integer productId) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if(StringUtils.isNullOrEmpty(value)){
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }else {
            opsForHash.delete(redisKey,
                    String.valueOf(productId));
        }

        return listCart(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        for(Cart cart:listForCart(uid)){
            cart.setProductSelected(true);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }

        return listCart(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        for(Cart cart:listForCart(uid)){
            cart.setProductSelected(false);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }

        return listCart(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {

        List<Cart> cartList = listForCart(uid);

        Integer sum = 0;
        for (Cart cart : cartList) {
            sum += cart.getQuantity();
        }

        return ResponseVo.success(sum);
    }

    private List<Cart> listForCart(Integer uid){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(),Cart.class));
        }
        return cartList;
    }
}
