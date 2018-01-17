/**
 * 
 */
package com.blgroup.jkl.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blgroup.jkl.redis.RedisDataSource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author issuser
 * 
 */
@Component
public class RedisDataSourceImpl implements RedisDataSource {

    @Autowired
    private JedisPool jedisPool;

    /*
     * (non-Javadoc)
     * 
     * @see com.iss.redis.RedisDataSource#getRedisClient()
     */
    @Override
    public Jedis getRedisClient() {
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.iss.redis.RedisDataSource#returnResource(redis.clients.jedis.ShardedJedis
     * )
     */
    @Override
    public void returnResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.iss.redis.RedisDataSource#returnResource(redis.clients.jedis.ShardedJedis
     * , boolean)
     */
    @Override
    public void returnResource(Jedis jedis, boolean broken) {
        if (broken) {
            jedisPool.returnBrokenResource(jedis);
        } else {
            jedisPool.returnResource(jedis);
        }
    }

}
