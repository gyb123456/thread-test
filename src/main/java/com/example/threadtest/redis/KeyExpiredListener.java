package com.example.threadtest.redis;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author ：GYB
 * @date ：Created in 2019/8/28 14:18
 * @description：TODO
 * @version: 1.0
 */
//@Component
@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    //这里是回调函数失效的时候回调用这个函数
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("redis-key:  {}过期", new String(message.getBody()));
//        System.out.println("message.toString() = " + message.toString());
//        System.out.println(new String(message.getBody()));
//        System.out.println(new String(message.getChannel()));
//        System.out.println(new String(pattern));
        super.onMessage(message, pattern);
    }
}