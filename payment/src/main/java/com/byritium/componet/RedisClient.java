package com.byritium.componet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient<Serializable, Value> {
    @Resource
    private RedisTemplate<Serializable, Value> redisTemplate;

    /**
     * 给一个指定的 key 值附加过期时间
     *
     */
    public boolean expire(Serializable key, long time) {
        Boolean flag = redisTemplate.expire(key, time, TimeUnit.SECONDS);
        return flag != null && flag;
    }

    /**
     * 获取key 获取过期时间
     */
    public long getTime(Serializable key) {
        Long time = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return time == null ? 0 : time;
    }

    /**
     * 判断key是否存在
     */
    public boolean hasKey(Serializable key) {
        Boolean flag = redisTemplate.hasKey(key);
        return flag != null && flag;
    }

    /**
     * 移除指定key 的过期时间
     */
    public boolean persist(Serializable key) {
        Boolean flag = redisTemplate.boundValueOps(key).persist();
        return flag != null && flag;
    }

    //- - - - - - - - - - - - - - - - - - - - -  String类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 根据key获取值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 将值放入缓存
     */
    public void set(Serializable key, Value value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将值放入缓存并设置时间
     */
    public void set(Serializable key, Value value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 批量添加 key (重复的键会覆盖)
     *
     */
    public void batchSet(Map<Serializable, Value> keyAndValue) {
        redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    /**
     * 批量添加 key-value 只有在键不存在时,才添加
     * map 中只要有一个key存在,则全部不添加
     *
     */
    public void batchSetIfAbsent(Map<Serializable, Value> keyAndValue) {
        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     *
     */
    public Long increment(Serializable key, long number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是 纯数字 ,将报错
     *
     */
    public Double increment(Serializable key, double number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    //- - - - - - - - - - - - - - - - - - - - -  set类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 将数据放入set缓存
     */
    public void sSet(Serializable key, Value... value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取变量中的值
     */
    public Set<Value> members(Serializable key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取变量中指定个数的元素
     */
    public void randomMembers(Serializable key, long count) {
        redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取变量中的元素
     */
    public Object randomMember(Serializable key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 弹出变量中的元素
     */
    public Object pop(Serializable key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 获取变量中值的长度
     */
    public long size(Serializable key) {
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0 : size;
    }

    /**
     * 根据value从一个set中查询,是否存在
     */
    public boolean sHasKey(Serializable key, Object value) {
        Boolean flag = redisTemplate.opsForSet().isMember(key, value);
        return flag != null && flag;
    }

    /**
     * 检查给定的元素是否在变量中。
     */
    public boolean isMember(Serializable key, Object obj) {
        Boolean flag = redisTemplate.opsForSet().isMember(key, obj);
        return flag != null && flag;
    }

    /**
     * 转移变量的元素值到目的变量。
     */
    public boolean move(Serializable key, Value value, Serializable destKey) {
        Boolean flag = redisTemplate.opsForSet().move(key, value, destKey);
        return flag != null && flag;
    }

    /**
     * 批量移除set缓存中元素
     */
    public void remove(Serializable key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 通过给定的key求2个set变量的差值
     */
    public Set<Value> difference(Serializable key, Serializable destKey) {
        return redisTemplate.opsForSet().difference(key, destKey);
    }


    //- - - - - - - - - - - - - - - - - - - - -  hash类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 加入缓存
     *
     * @param key 键
     * @param map 键
     */
    public void add(Serializable key, Map<Serializable, Value> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取 key 下的 所有  hashkey 和 value
     *
     * @param key 键
     */
    public Map<Object, Object> getHashEntries(Serializable key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 验证指定 key 下 有没有指定的 hashkey
     *
     */
    public boolean hashKey(Serializable key, Serializable hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取指定key的值string
     *
     * @param key  键
     * @param key2 键
     */
    public Object getMapValue(Serializable key, Serializable key2) {
        return redisTemplate.opsForHash().get(key, key2);
    }

    /**
     * 弹出元素并删除
     */
    public Value popValue(Serializable key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 删除指定 hash 的 HashKey
     */
    public Long delete(Serializable key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     */
    public Long increment(Serializable key, Object hashKey, long number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     */
    public Double increment(Serializable key, Object hashKey, Double number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 获取 key 下的 所有 hashkey 字段
     *
     */
    public Set<Object> hashKeys(Serializable key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取指定 hash 下面的 键值对 数量
     *
     */
    public Long hashSize(Serializable key) {
        return redisTemplate.opsForHash().size(key);
    }

    //- - - - - - - - - - - - - - - - - - - - -  list类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 在变量左边添加元素值
     *
     */
    public void leftPush(Serializable key, Value value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取集合指定位置的值。
     */
    public Value index(Serializable key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取指定区间的值。
     */
    public List<Value> range(Serializable key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，
     * 如果中间参数值存在的话。
     */
    public void leftPush(Serializable key, Value pivot, Value value) {
        redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 向左边批量添加参数元素。
     */
    public void leftPushAll(Serializable key, Value... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 向集合最右边添加元素。
     */
    public void leftPushAll(Serializable key, Value value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向左边批量添加参数元素。
     */
    public void rightPushAll(Serializable key, Value... values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向已存在的集合中添加元素。
     */
    public void rightPushIfPresent(Serializable key, Value value) {
        redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 向已存在的集合中添加元素。
     */
    public long listLength(Serializable key) {
        Long length = redisTemplate.opsForList().size(key);
        return length == null ? 0 : length;
    }

    /**
     * 移除集合中的左边第一个元素。
     */
    public void leftPop(Serializable key) {
        redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     */
    public void leftPop(Serializable key, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除集合中右边的元素。
     */
    public void rightPop(Serializable key) {
        redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     */
    public void rightPop(Serializable key, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().rightPop(key, timeout, unit);
    }
}
