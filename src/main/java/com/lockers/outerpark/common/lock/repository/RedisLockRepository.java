package com.lockers.outerpark.common.lock.repository;

import java.util.Collections;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisLockRepository {

	private final RedisTemplate<String, String> redisTemplate;

	// 락 획득 script
	private static final String LOCK_SCRIPT =
		// key가 존재하지 않으면(uuid를 value로) 설정하고
		"if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then " +
			// 설정된 key에 대해 만료시간(TTL) 지정 후
			"redis.call('pexpire', KEYS[1], ARGV[2]) " +
			// 성공적으로 락을 획득했으므로 true 반환
			"return true " +
			// key가 이미 존재하면 락 획득 실패 → false 반환
			"else return false end";

	// 락 해제 script
	private static final String UNLOCK_SCRIPT =
		// 현재 key의 value가 내 UUID와 같으면 (내가 락을 잡은 것이라면)
		"if redis.call('get', KEYS[1]) == ARGV[1] then " +
			// 해당 key 삭제(락 해제) 후 1 반환
			"return redis.call('del', KEYS[1]) " +
			// value가 다르면 해제하지 않음 -> 0 반환
			"else return 0 end";

	/**
	 * 락 획득 시도
	 * @param key Redis에 저장할 락 키 (ex: lock:concert:1:seat:50)
	 * @param uuid 락 소유자 식별용 UUID
	 * @param ttlMillis 락의 TTL (밀리초 단위)
	 * @return true = 락 획득 성공, false = 실패
	 */
	public boolean tryLock(String key, String uuid, long ttlMillis) {
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptText(LOCK_SCRIPT);
		redisScript.setResultType(Long.class);

		Long result = redisTemplate.execute(
			redisScript,
			Collections.singletonList(key),
			uuid,
			String.valueOf(ttlMillis)
		);

		return result != null && result == 1L;
	}

	/**
	 * 락 해제
	 * @param key 락 키
	 * @param uuid 락 소유자 UUID (소유자만 락 해제 가능)
	 * @return true = 락 해제 성공, false = 실패 또는 이미 해제됨
	 */
	public boolean unlock(String key, String uuid) {
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptText(UNLOCK_SCRIPT);
		redisScript.setResultType(Long.class);

		Long result = redisTemplate.execute(
			redisScript,
			Collections.singletonList(key),
			uuid
		);

		return result != null && result == 1L;
	}
}