/**
 * 
 */
package com.blgroup.jkl.redis.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blgroup.jkl.redis.RedisClientTemplate;
import com.blgroup.jkl.redis.RedisDataSource;

import redis.clients.jedis.Jedis;

/**
 * @author issuser
 * 
 */
@Component
public class RedisClientTemplateImpl implements RedisClientTemplate {

    /**
     * 缓存超时时间
     */
    private static int expire;

    static {
        ResourceBundle rb = ResourceBundle.getBundle("application");
        expire = Integer.parseInt(rb.getString("redis.expire"));
    }

    @Autowired
    private RedisDataSource redisDataSource;

    @Override
    public String set(String key, String value, int expire) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.set(key, value);
            if (0 < expire) {
                jedis.expire(key, expire);
            }
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public String get(String key) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Boolean exists(String key) {
        Boolean result = false;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.exists(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public boolean exists(byte[] key) {
        Boolean result = false;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.exists(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public String type(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.hmset(key, hash);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.hmset(key, hash);
            jedis.expire(key, expire);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long hset(String key, String field, String value) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.hset(key, field, value);

        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long hset(byte[] key, byte[] field, byte[] value) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Boolean hexists(String key, String field) {
        Boolean result = false;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.hexists(key, field);

        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Boolean hexists(byte[] key, byte[] field) {
        Boolean result = false;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.hexists(key, field);

        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public String hget(String key, String field) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.hget(key, field);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        byte[] result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.hget(key, field);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long del(String... key) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.del(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long del(byte[]... key) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.del(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Set<String> keys(String pattern) {
        Set<String> result = new HashSet<String>();
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.keys(pattern);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Set<byte[]> keys(byte[] pattern) {
        Set<byte[]> result = new HashSet<byte[]>();
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.keys(pattern);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public byte[] rpop(byte[] key) {
        byte[] result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.rpop(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long rpush(byte[] key, byte[] value) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.rpush(key, value);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long llen(byte[] key) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.llen(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public List<byte[]> lrange(byte[] key, int start, int end) {
        List<byte[]> result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.lrange(key, start, end);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long expire(byte[] key, int seconds) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public String set(byte[] key, byte[] value, int sessionExpire) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.set(key, value);
            jedis.expire(key, sessionExpire);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public byte[] get(byte[] key) {
        byte[] result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public byte[] getSession(byte[] key, int sessionExpire) {
        byte[] result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.get(key);
            if (null != result) {
                jedis.expire(key, sessionExpire);
            }
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long rpush(String key, String value) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.rpush(key, value);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public String rpop(String key) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.rpop(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public Long llen(String key) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.llen(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    @Override
    public String flushAll() {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.flushAll();
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;

    }

    @Override
    public Long ttl(byte[] key) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (null == jedis) {
            return null;
        }

        boolean broken = false;
        try {
            result = jedis.pttl(key);
        } catch (Exception e) {
            // TODO
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

}
