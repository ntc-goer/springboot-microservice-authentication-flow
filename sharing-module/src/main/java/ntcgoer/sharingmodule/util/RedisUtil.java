package ntcgoer.sharingmodule.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisUtil(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void rightPushToList(String key, Object value,int authExpireTime ) {
        this.redisTemplate.opsForList().rightPush(key, value);
        this.redisTemplate.expire(key, authExpireTime, TimeUnit.HOURS);
    }

    public void setKeyValue(String key, String value, int authExpireTime) {
        this.redisTemplate.opsForValue().set(key, value);
        this.redisTemplate.expire(key, authExpireTime, TimeUnit.HOURS);
    }
    public void addHashMap(String key, HashMap<Object,Object> map, int authExpireTime) {
        this.redisTemplate.opsForHash().putAll(key, map);
        this.redisTemplate.expire(key, authExpireTime, TimeUnit.HOURS);
    }

    public List<Object> getList(String key) {
        // Retrieve the entire list by key
        return this.redisTemplate.opsForList().range(key, 0, -1);
    }

    public Boolean delete(String key){
        return this.redisTemplate.delete(key);
    }
}
