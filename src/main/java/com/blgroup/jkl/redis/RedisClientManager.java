package com.blgroup.jkl.redis;

import com.blgroup.jkl.servlet.CacheServerHelper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis操作基类
 * 
 * @version 1.0
 * 
 * @createDate 2012-7-24
 * 
 * @modifyDate 2012-8-14
 */
public class RedisClientManager {

    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        if (jedisPool == null) {
            jedisPool = CacheServerHelper.jedisPool;
        }
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 用完redis归还
     * 
     */
    public void returnResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }
}
