package intern_server.shibing.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @author Administrator
 */
@Component
public class RedisConfig {

    private Boolean isInitRedis = Boolean.FALSE;
    private JedisPool jedisPool = null;

    @Value("${redisProperties.REDIS_MAX_TOTAL}")
    int redisMaxTotal;
    @Value("${redisProperties.REDIS_MAX_IDLE}")
    int redisMaxIDLE;
    @Value("${redisProperties.REDIS_MAX_WAIT}")
    int redisMaxWaitMillis;
    @Value("${redisProperties.REDIS_HOST}")
    String redisHost;
    @Value("${redisProperties.REDIS_PORT}")
    int redisPort;
    @Value("${redisProperties.REDIS_TIMEOUT}")
    int redisTimeOut;
    @Value("${redisProperties.REDIS_USE_PASSWORD}")
    String redisUsePassword;
    @Value("${redisProperties.REDIS_PASSWORD}")
    String redisPassword;

    /**
     * 获得redis连接池对象
     */
    @Bean
    public JedisPool redisPoolBean() {

        if (jedisPool == null) {
            synchronized (isInitRedis) {
                if (isInitRedis.equals(Boolean.FALSE)) {

                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

                    try {
                        jedisPoolConfig.setMaxIdle(redisMaxIDLE);
                        jedisPoolConfig.setMaxWaitMillis(redisMaxWaitMillis);
                        jedisPoolConfig.setMaxTotal(redisMaxTotal);
                        if ("Y".equals(redisUsePassword)) {
                            jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, redisTimeOut, redisPassword);
                        } else {
                            jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, redisTimeOut);
                        }

                        isInitRedis = Boolean.TRUE;
                    } catch (Exception e) {
                        //打印日志输出log.error("CacheRedisClient Init Exception.", e);
                    }
                }
            }
        }
        return jedisPool;
    }

    /**
     * 重置redis连接池对象
     */
    public void reInit() {
        jedisPool = null;
        isInitRedis = Boolean.FALSE;
        redisPoolBean();
    }

}
