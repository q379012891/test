/**
 * 
 */
package com.blgroup.jkl.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author issuser
 * 
 */
public interface RedisClientTemplate {

    public String flushAll();

    public String set(String key, String value, int expire);

    public String set(byte[] key, byte[] value, int sessionExpire);

    public String get(String key);

    public byte[] get(byte[] key);

    public byte[] getSession(byte[] key, int sessionExpire);

    public Long del(String... key);

    public Boolean exists(String key);

    public Set<String> keys(String pattern);

    public String type(String key);

    /*
     * ****************************expire***********************************
     */
    public Long expire(byte[] key, int seconds);

    /*
     * ****************************HashMap***********************************
     */
    public String hmset(String key, Map<String, String> hash);

    public Long hset(String key, String field, String value);

    public Boolean hexists(String key, String field);

    public String hget(String key, String field);

    String hmset(byte[] key, Map<byte[], byte[]> hash);

    Boolean hexists(byte[] key, byte[] field);

    public Long hset(byte[] convert, byte[] convert2, byte[] rValue);

    public byte[] hget(byte[] convert, byte[] convert2);

    public boolean exists(byte[] convert);

    Set<byte[]> keys(byte[] pattern);

    Long del(byte[]... key);

    /*
     * ****************************List***********************************
     */
    public byte[] rpop(byte[] key);

    public String rpop(String key);

    public Long rpush(byte[] key, byte[] value);

    public Long llen(byte[] key);

    public Long llen(String key);

    public List<byte[]> lrange(byte[] key, int start, int end);

    public Long rpush(String key, String value);

    public Long ttl(byte[] key);
}
