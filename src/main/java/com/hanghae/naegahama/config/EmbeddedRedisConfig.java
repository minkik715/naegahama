package com.hanghae.naegahama.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Profile("local")
@Profile("15.164.219.84")
@Configuration
@Slf4j
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        log.info("레디스 시작");
        redisServer = new RedisServer(redisPort);
        log.info("레디스 시작");
        redisServer.start();
        log.info("레디스 시작");

    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

}
