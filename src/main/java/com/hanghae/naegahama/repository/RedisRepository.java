package com.hanghae.naegahama.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class RedisRepository {
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> userRoomMap;
    public static final String ENTER_INFO = "ENTER_INFO";

    public void mappingUserRoom(String sessionId, String roomId){
        userRoomMap.put(ENTER_INFO, sessionId, roomId);
    }

    public void removeUserRoomMapping(String sessionId){
        userRoomMap.delete(ENTER_INFO,sessionId);
    }
}
