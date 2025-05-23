package com.zw.restaurantmanagementsystem;

import com.zw.restaurantmanagementsystem.util.RedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    private final String TEST_KEY = "test_key";
    private final String TEST_VALUE = "test_value";

    @BeforeEach
    void setUp() {
        // 在每个测试之前清理缓存
        if (redisUtil.hasKey(TEST_KEY)) {
            redisUtil.delete(TEST_KEY);
        }
    }

    @Test
    void testSetAndGet() {
        // 设置值
        redisUtil.set(TEST_KEY, TEST_VALUE);

        // 获取值并验证
        String value = redisUtil.get(TEST_KEY, String.class);
        assertNotNull(value, "The value should not be null");
        assertEquals(TEST_VALUE, value, "The retrieved value should match the set value");
    }

    @Test
    void testSetExpiryAndGet() {
        // 设置带有过期时间的值
        redisUtil.setEx(TEST_KEY, TEST_VALUE, 5, TimeUnit.MINUTES);

        // 立即获取值并验证
        String value = redisUtil.get(TEST_KEY, String.class);
        assertNotNull(value, "The value should exist immediately after setting");

        // 等待超过过期时间后再次尝试获取值
        try {
            Thread.sleep(6000); // 等待6秒以确保过期
        } catch (InterruptedException e) {
            fail("Thread was interrupted", e);
        }

        value = redisUtil.get(TEST_KEY, String.class);
        assertNull(value, "The value should have expired and been removed");
    }

    @Test
    void testDelete() {
        // 先设置值
        redisUtil.set(TEST_KEY, TEST_VALUE);

        // 删除值
        redisUtil.delete(TEST_KEY);

        // 验证值已被删除
        assertFalse(redisUtil.hasKey(TEST_KEY), "The key should no longer exist after deletion");
    }

    @Test
    void testHasKey() {
        // 初始状态下不应存在键
        assertFalse(redisUtil.hasKey(TEST_KEY), "The key should not exist initially");

        // 设置值后应存在键
        redisUtil.set(TEST_KEY, TEST_VALUE);
        assertTrue(redisUtil.hasKey(TEST_KEY), "The key should exist after setting a value");

        // 删除值后键应不存在
        redisUtil.delete(TEST_KEY);
        assertFalse(redisUtil.hasKey(TEST_KEY), "The key should not exist after deletion");
    }

    @Test
    void testSetWithLogicalExpireAndGetWithLogicalExpire() {
        // 设置带有逻辑过期时间的值
        redisUtil.setWithLogicalExpire(TEST_KEY, TEST_VALUE, 5, TimeUnit.SECONDS);

        // 立即获取值并验证
        String value = redisUtil.getWithLogicalExpire(TEST_KEY, String.class);
        assertNotNull(value, "The value should exist immediately after setting with logical expiration");
        assertEquals(TEST_VALUE, value, "The retrieved value should match the set value");

        // 等待超过逻辑过期时间后再次尝试获取值
        try {
            Thread.sleep(6000); // 等待6秒以确保逻辑过期
        } catch (InterruptedException e) {
            fail("Thread was interrupted", e);
        }

        value = redisUtil.getWithLogicalExpire(TEST_KEY, String.class);
        assertNull(value, "The value should have logically expired and been removed");
    }
}