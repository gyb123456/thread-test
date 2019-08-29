package com.example.threadtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：GYB
 * @date ：Created in 2019/8/27 18:17
 * @description：TODO
 * @version: 1.0
 */
//@CacheConfig

@RestController
@RequestMapping("test")
public class CacheCtrl {

    @Autowired
    CacheManager cacheManager;
    
    @GetMapping("/one/{id}")
    @Cacheable(value = "gyb",key = "#id")
    public String one(@PathVariable Integer id){
        System.out.println("进入one方法======，id："+id);
        System.out.println("cacheManager = "
                + cacheManager.getCacheNames()
                + cacheManager);
//        RedisCacheManager
//        EhCacheCacheManager
        return "aaa"+id;
    }
    @GetMapping("/two/{id}")
    @Cacheable(value = "two",key = "#id",cacheManager = "myehcahe")
    public String two(@PathVariable Integer id){
        System.out.println("进入two方法======，id："+id);
        return "bbb"+id;
    }
}
