package com.hanghae.naegahama.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

    public static final String ENTER_INFO = "ENTER_INFO";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> stringHashOpsEnterInfo;

    public void setSessionAlarm(String sessionId, String userId) {
        stringHashOpsEnterInfo.put(ENTER_INFO, sessionId, userId);
    }

    public String getSessionUserId(String sessionId) {
        return stringHashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }
    

    // sessionId 삭제
    public void removeUserEnterInfo(String sessionId) {
        stringHashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }
}
