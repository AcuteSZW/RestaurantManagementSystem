package com.zw.restaurantmanagementsystem.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 设置缓存值，支持自动序列化为JSON字符串
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public <T> void set(String key, T value) {
        try {
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }

    /**
     * 设置缓存值并指定过期时间
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    public <T> void setEx(String key, T value, long timeout, TimeUnit unit) {
        try {
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value), Duration.ofSeconds(unit.toSeconds(timeout)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }

    /**
     * 获取缓存值，并尝试反序列化为目标类型
     *
     * @param key 缓存键
     * @param clazz 目标类型
     * @return 反序列化后的对象
     */
    public <T> T get(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }

    /**
     * 获取缓存值
     *
     * @param key 缓存键
     */
    public String get(String key) {
        String strCode = stringRedisTemplate.opsForValue().get(key);
        String cleanValue = null; // 去除首尾的双引号
        if (strCode != null) {
            cleanValue = strCode.replaceAll("^\"|\"$", "");
        }
        return cleanValue;
    }

    /**
     * 获取缓存值，并尝试反序列化为JSON对象
     * @param key
     * @return
     */
    public <T> T getJson(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 检查缓存是否存在
     *
     * @param key 缓存键
     * @return 存在返回true，否则返回false
     */
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 获取指定key的剩余过期时间
     *
     * @param key 缓存键
     * @param unit 时间单位
     * @return 剩余过期时间，如果key不存在或没有设置过期时间，则返回-1
     */
    public long getExpire(String key, TimeUnit unit) {
        Long expire = stringRedisTemplate.getExpire(key, unit);
        return expire != null ? expire : -1;
    }

    /**
     * 增加指定key的过期时间
     *
     * @param key     缓存键
     * @param timeout 增加的过期时间
     * @param unit    时间单位
     * @return 如果成功增加过期时间返回true，否则返回false
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        Boolean result = stringRedisTemplate.expire(key, timeout, unit);
        return result != null && result;
    }


    /**
     * 设置逻辑过期时间，用于解决缓存击穿问题
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    public <T> void setWithLogicalExpire(String key, T value, long timeout, TimeUnit unit) {
        try {
            LogicalExpireData<T> data = new LogicalExpireData<>();
            data.setData(value);
            data.setExpireTime(System.currentTimeMillis() + unit.toMillis(timeout));
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object", e);
        }
    }

    /**
     * 获取带有逻辑过期时间的数据
     *
     * @param key 缓存键
     * @param clazz 目标类型
     * @return 反序列化后的对象
     */
    public <T> T getWithLogicalExpire(String key, Class<T> clazz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json == null) {
            return null;
        }
        try {
            LogicalExpireData<T> data = objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(LogicalExpireData.class, clazz));
            if (data.getExpireTime() > System.currentTimeMillis()) {
                return data.getData();
            } else {
                // 过期则删除缓存
                delete(key);
                return null;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize object", e);
        }
    }

    private static class LogicalExpireData<T> {
        private T data;
        private long expireTime;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }
    }
}