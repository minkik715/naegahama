package com.hanghae.naegahama.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
@Repository
@RequiredArgsConstructor
public class RedisRepository{

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> userRoomMap;
    public static final String ENTER_INFO = "ENTER_INFO";

    public void mappingUserRoom(String sessionId, String roomId){
        userRoomMap.put(ENTER_INFO, sessionId, roomId);
    }

    public String getUserEnterRoomId(String sessionId) {
        return userRoomMap.get(ENTER_INFO, sessionId);
    }

    public void removeUserEnterInfo(String sessionId) {
        userRoomMap.delete(ENTER_INFO, sessionId);
    }


}
