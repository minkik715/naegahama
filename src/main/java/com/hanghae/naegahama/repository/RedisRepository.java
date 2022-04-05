package com.hanghae.naegahama.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
<<<<<<< HEAD
import java.util.Map;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

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
