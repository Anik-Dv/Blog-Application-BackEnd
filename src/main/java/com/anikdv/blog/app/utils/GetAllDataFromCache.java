package com.anikdv.blog.app.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import com.anikdv.blog.app.configurations.RedisCacheConfiguration;
import com.anikdv.blog.app.payloads.PostDto;
import com.anikdv.blog.app.services.impl.PostServiceImpl;

/**
 * @see This Class for Fetch All Data From Cache
 * @author anikdv
 */
public class GetAllDataFromCache {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	private RedisCacheConfiguration configuration;

	/**
	 * @param pageNumber
	 * @param pageSize
	 * @return fetch all data from cache
	 */
	public List<PostDto> getAllDataCache(final Integer pageNumber, final Integer pageSize) {
		int tempStartIndex = 0;
		int tempEndIndex = 0;

		List<PostDto> postsArr = new ArrayList<>();

		try (Cursor<Entry<Object, Object>> cursor = configuration.redisTemplate().opsForHash()
				.scan(AppConstants.CACHE_KEY, ScanOptions.scanOptions().match("*").build())) {

			while (cursor.hasNext()) {
				if (tempStartIndex >= pageNumber && tempEndIndex < pageSize) {
					Entry<Object, Object> entry = cursor.next();
					PostDto postsDto = (PostDto) entry.getValue();
					postsArr.add(postsDto);
					tempStartIndex++;
					tempEndIndex++;
					continue;
				}

				if (tempEndIndex >= pageSize) {
					break;
				}

				tempStartIndex++;
				cursor.next();
			}

		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception while fetching data from redis cache : " + e);
			}
		}
		return postsArr;
	}

}

