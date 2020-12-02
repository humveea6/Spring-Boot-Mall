package com.imooc.mall.controller;


import com.google.common.base.Throwables;
import com.google.common.util.concurrent.*;
import com.imooc.mall.pojo.Order;
import com.imooc.mall.utils.HlgGsonUtil;
import com.imooc.mall.vo.ResponseVo;
import com.sun.tools.corba.se.idl.constExpr.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-10-21
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class testController {

    private RowMapper<Order> rowMapper = new BeanPropertyRowMapper<>(Order.class);

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    @PostMapping("/SQL")
    public ResponseVo testSQL(){
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        final ListenableFuture<Integer> listenableFuture = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call execute..");
                throw new Exception();
            }
        });

        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println("get listenable future's result with callback " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(Throwables.getStackTraceAsString(t));
            }
        },executorService);

        return ResponseVo.success("success");
    }

    public void insert(Order order){
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update("insert into mall_order(order_no,user_id,shipping_id) values (:orderNo,:userId,:shippingId)",
                    new BeanPropertySqlParameterSource(order),keyHolder);
            System.out.println(keyHolder.getKey().longValue());
        }
        catch (Exception e){
            log.error("Error! "+e);
        }
    }

    public void select(){
        try{
            Order order = template.queryForObject("select * from mall_order where id=1", new EmptySqlParameterSource(), new BeanPropertyRowMapper<>(Order.class));
            log.info("order: "+HlgGsonUtil.toJson(order));
        }
        catch (Exception e){
            log.error("Error! "+e);
        }
    }

    public void selectList(long id){
        List<Order> orderNoList = template.query("select * from mall_order where id>:id", new MapSqlParameterSource("id", id), new BeanPropertyRowMapper<>(Order.class));
        log.info("order no list: "+HlgGsonUtil.toJson(orderNoList));

    }

    public void count(){
        Integer count = template.queryForObject("select count(*) from mall_order", new EmptySqlParameterSource(), Integer.class);
        log.info("count: "+count);
    }

    public static void main(String[] args) {
        byte[] bytes = {};
        String test = new String(bytes);
        System.out.println(test);
    }
}