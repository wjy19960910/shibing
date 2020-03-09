package intern_server.shibing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;


@Component
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Resource
    JedisPool redisPoolBean;

    private RedisSerializer<String> keyRedisSerializer = new StringRedisSerializer();
    private RedisSerializer<Object> valueRedisSerializer = new JdkSerializationRedisSerializer();

    /**
     * Key序列化
     */
    private byte[] keyRedisSerializer(String key) throws Exception {
        return keyRedisSerializer.serialize(key);
    }

    /**
     * Key反序列化
     */
    private String keyRedisDeserializer(byte[] key) throws Exception {
        return keyRedisSerializer.deserialize(key);
    }

    /**
     * Value序列化
     */
    private byte[] valueRedisSerializer(Object value) throws Exception {
        return valueRedisSerializer.serialize(value);
    }

    /**
     * Value反序列化
     */
    private Object valueRedisDeserializer(byte[] value) throws Exception {
        if (null != value) {
            return valueRedisSerializer.deserialize(value);
        } else {
            return null;
        }
    }


    /**
     * 设置键值
     */
    public String set(String key, Object value) {
        String result = null;
        if (value == null) {
            return null;
        }
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.set(keyRedisSerializer(key), valueRedisSerializer(value));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 删除指定key
     */
    public Long del(String key) {
        Long result = null;
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.del(keyRedisSerializer(key));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 更改指定key
     */
    public String rename(String oldKey, String newKey) {
        String result = null;
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.rename(keyRedisSerializer(oldKey), keyRedisSerializer(newKey));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 设置生存时间
     */
    public String setex(String key, int seconds, Object value) {
        String result = null;
        if (value == null) {
            return null;
        }
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.setex(keyRedisSerializer(key), seconds, valueRedisSerializer(value));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 获取单个值
     */
    public Object get(String key) {
        Object result = null;
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        try {
            result = valueRedisDeserializer(jedis.get(keyRedisSerializer(key)));
        } catch (Exception e) {
            if (!(e instanceof JedisDataException || e instanceof JedisConnectionException)) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 判断指定key是否存在
     */
    public Boolean exists(String key) {
        Boolean result = false;
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.exists(keyRedisSerializer(key));
        } catch (Exception e) {
            if (!(e instanceof JedisDataException || e instanceof JedisConnectionException)) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 使键值固定时间间隔失效
     */
    public Long expire(String key, int seconds) {
        Long result = null;
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.expire(keyRedisSerializer(key), seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 在某个时间点失效
     */
    public Long expireAt(String key, long unixTime) {
        Long result = null;
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.expireAt(keyRedisSerializer(key), unixTime);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 根据key前缀批量删除
     */
    public Long delByPrefix(String pre_str) {
        Long result = 0L;
        Jedis jedis = redisPoolBean.getResource();
        if (jedis == null) {
            return result;
        }
        String keys = "";
        try {
            Set<String> set = jedis.keys(pre_str + "*");
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                result = result + del(keyStr);
                keys += keyStr + "; ";
            }
            logger.info("delete by prefix keys is " + keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return result;
    }

}
