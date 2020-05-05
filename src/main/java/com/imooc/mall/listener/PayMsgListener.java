package com.imooc.mall.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-05
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @RabbitHandler
    public void process(String msg){
        log.info("RabbitMQ Msg: "+ msg);
    }
}
