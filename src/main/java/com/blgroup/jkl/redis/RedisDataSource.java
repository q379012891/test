/**
 * 
 */
package com.blgroup.jkl.redis;

import redis.clients.jedis.Jedis;

/**
 * @author issuser
 * 
 */
public interface RedisDataSource {

	public abstract Jedis getRedisClient();

	public void returnResource(Jedis jedis);

	public void returnResource(Jedis jedis, boolean broken);

}
