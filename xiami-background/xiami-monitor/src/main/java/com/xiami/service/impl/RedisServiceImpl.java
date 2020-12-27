package com.xiami.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xiami.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;

    /**
     * 获取内存信息
     *
     * @return
     */
    @Override
    public Map<String, Object> getInfo() {
        Properties info = (Properties) redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback) redisConnection -> redisConnection.dbSize());

        Map<String, Object> result = new HashMap<>(4);
        result.put("info", info);
        result.put("dbSize", dbSize);
        result.put("time", DateUtil.format(new Date(), DatePattern.NORM_TIME_PATTERN));

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(4);
            String property = commandStats.getProperty(key);
            data.put("name", StrUtil.removePrefix(key, "cmdstat_"));
            data.put("value", StrUtil.subBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return result;
    }
}
