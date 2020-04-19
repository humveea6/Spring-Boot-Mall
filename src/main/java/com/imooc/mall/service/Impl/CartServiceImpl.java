package com.imooc.mall.service.Impl;

import com.google.gson.Gson;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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

        return null;
    }

    @Override
    public ResponseVo<CartVo> listCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));

        return null;
    }
}
