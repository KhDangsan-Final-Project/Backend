package com.ms1.service;

import java.time.LocalDate;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ViewService {
	
	private RedisTemplate<String, Object> redisTemplate;

	public ViewService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public int updateViewCount(String ipAddress) {
        String viewKey = "view:visitors";
        String timeKey = "view:times";
        String totalCountKey = "view:totalCount";


        // IP 주소를 Set에 추가하여 고유 방문자 수 관리
        redisTemplate.opsForSet().add(viewKey, ipAddress);

        // 방문 시간 기록 (ZSet 사용, 시간값을 score로 저장)
        long currentTime = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(timeKey, ipAddress + ":" + currentTime, currentTime);

        // 현재 고유 방문자 수 반환
        Long totalVisitors = redisTemplate.opsForValue().increment(totalCountKey, 1);
        return totalVisitors != null ? totalVisitors.intValue() : 0;
    }
	
	
}
