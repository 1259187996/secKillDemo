package com.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    /**
     * 默认过期时长为24小时，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;
    /**
     * 过期时长为1小时，单位：秒
     */
    public final static long HOUR_ONE_EXPIRE = 60 * 60 * 1L;
    /**
     * 过期时长为6小时，单位：秒
     */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;
    
    /**
     * setNx  lua脚本
     */
    private final static String setNxStr = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";


    /**
     * @param key
     * @methodName: set
     * @author: bill.ning
     * @description: 添加自增key 默认24小时
     * @date: 2019/4/15
     * @return: void
     */
    public long increKeyByOne(String key) {
        return increKeyByOne(key, DEFAULT_EXPIRE);
    }

    public Long increKeyByOne(String key, long expire) {
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return (increment == null ? 0 : increment);
    }


    /**
     * @Author: wx
     * @Desc: 添加递减key 默认24小时
     * @Date: 3:15 PM 2019/9/24
     * @param: key
     * @return: long
     **/
    public long decrKeyByOne(String key) {
        return decrKeyByOne(key, DEFAULT_EXPIRE);
    }

    public Long decrKeyByOne(String key, long expire) {
        Long increment = redisTemplate.opsForValue().increment(key, -1);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return (increment == null || increment < 0 ? 0 : increment);
    }

    /**
     * @param key
     * @methodName: set
     * @author: bill.ning
     * @description: 获得key过期时间
     * @date: 2019/4/15
     * @return: void
     */
    public long keyExpireTime(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * @param key
     * @param value
     * @param expire
     * @methodName: set
     * @author: wx
     * @description: 设置缓存 String结构，带过期时间
     * @date: 2019/4/15
     * @return: void
     */
    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * @param key
     * @param value
     * @methodName: set
     * @author: wx
     * @description: 设置缓存 String结构 默认过期时间24小时
     * @date: 2019/4/15
     * @return: void
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * @param key
     * @param expire
     * @methodName: get
     * @author: wx
     * @description: 获取缓存 String结构 并重新设置过期时间，expire=-1代表不重新设置过期时间
     * @date: 2019/4/15
     * @return: java.lang.Object
     */
    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    /**
     * @param key
     * @methodName: get
     * @author: wx
     * @description: 获取缓存 String结构
     * @date: 2019/4/15
     * @return: java.lang.Object
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * @param key
     * @methodName: get
     * @author: wx
     * @description: 获取缓存 String结构
     * @date: 2019/4/15
     * @return: java.lang.Object
     */
    public Object get(String key, Integer database) {
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        Integer old = factory.getDatabase();
        factory.setDatabase(database);
        factory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(factory);
        Object value = redisTemplate.opsForValue().get(key);
        factory.setDatabase(old);
        factory.afterPropertiesSet();
        return value;
    }

    /**
     * @param key
     * @methodName: delete
     * @author: wx
     * @description: 删除缓存
     * @date: 2019/4/15
     * @return: void
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * @param key      键
     * @param database 数据库
     * @author: yzz
     * @description: 删除缓存
     * @date: 2019/4/15
     * @return: void
     */
    public Boolean delete(String key, Integer database) {
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        int old = Objects.requireNonNull(factory).getDatabase();
        factory.setDatabase(database);
        factory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(factory);
        boolean flag = redisTemplate.delete(key);
        factory.setDatabase(old);
        factory.afterPropertiesSet();
        return flag;
    }

    /**
     * @Author: wx
     * @Desc: 模糊删除
     * @Date: 1:52 PM 2019/5/16
     * @param: pref
     * @return: void
     **/
    public void deleteByPrex(String pref) {
        Set<String> keys = redisTemplate.keys(pref + "*");
        redisTemplate.delete(keys);
    }

    /**
     * @Author: wx
     * @Desc: 批量模糊删除
     * @Date: 1:52 PM 2019/5/16
     * @param: prefs
     * @return: void
     **/
//    public void batchDeleteByPrex(Set<String> prefs) {
//        if (CollectionUtils.isNotEmpty(prefs)) {
//            prefs.forEach(pref -> {
//                Set<String> keys = redisTemplate.keys(pref);
//                redisTemplate.delete(keys);
//            });
//        }
//
//    }

    /**
     * @param keys
     * @methodName: delete
     * @author: wx
     * @description: 删除多个缓存
     * @date: 2019/4/15
     * @return: void
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * @param key
     * @param field
     * @methodName: hGet
     * @author: wx
     * @description: 获取缓存 map结构
     * @date: 2019/4/15
     * @return: java.lang.Object
     */
    public Object getMap(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<String, Object> getMapAll(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    /**
     * @param key
     * @param map
     * @methodName: hMSet
     * @author: wx
     * @description: 设置缓存 Map结构，默认过期时间24小时
     * @date: 2019/4/15
     * @return: void
     */
    public void addMap(String key, Map<String, Object> map) {
        addMap(key, map, DEFAULT_EXPIRE);
    }

    /**
     * @param key
     * @param map
     * @param expire
     * @methodName: hMSet
     * @author: wx
     * @description: 设置缓存 Map结构 带过期时间
     * @date: 2019/4/15
     * @return: void
     */
    public void addMap(String key, Map<String, Object> map, long expire) {
        redisTemplate.opsForHash().putAll(key, map);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void addMap(String key, String field, Object value) {
        addMap(key, field, value, DEFAULT_EXPIRE);
    }

    public void addMap(String key, String field, Object value, long expire) {
        redisTemplate.opsForHash().put(key, field, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * @Author: wx
     * @Desc: 设置缓存 set结构
     * @Date: 10:38 AM 2019/6/28
     * @param: key
     * @param: values
     * @return: void
     **/
    public void addSet(String key, Object... values) {
        addSet(key, NOT_EXPIRE, values);

    }

    /**
     * @Author: wx
     * @Desc: 设置缓存 set结构 带过期时间参数
     * @Date: 10:40 AM 2019/6/28
     * @param: key
     * @param: expire
     * @param: values
     * @return: void
     **/
    public void addSet(String key, long expire, Object... values) {
        redisTemplate.opsForSet().add(key, values);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * @Author: wx
     * @Desc: 获取缓存 set结构
     * @Date: 10:44 AM 2019/6/28
     * @param: key
     * @return: org.springframework.data.redis.core.BoundSetOperations
     **/
    public BoundSetOperations getSet(String key) {
        return redisTemplate.boundSetOps(key);
    }

    /**
     * @Author: wx
     * @Desc: 设置缓存 ZSet结构，默认过期时间24小时
     * @Date: 5:50 PM 2019/6/27
     * @param: key
     * @param: set
     * @return: void
     **/
    public void addZSet(String key, Set<ZSetOperations.TypedTuple<Object>> set) {
        addZSet(key, set, DEFAULT_EXPIRE);
    }

    /**
     * @Author: wx
     * @Desc: 设置缓存 Hset结构 带过期时间
     * @Date: 5:46 PM 2019/6/27
     * @param: key
     * @param: set
     * @param: expire
     * @return: void
     **/
    public void addZSet(String key, Set<ZSetOperations.TypedTuple<Object>> set, long expire) {
        redisTemplate.opsForZSet().add(key, set);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * @Author: wx
     * @Desc: 设置缓存 Hset元素
     * @Date: 1:44 PM 2019/10/8
     * @param: key
     * @param: elementKey
     * @param: score
     * @return: void
     **/
    public void addZSetElement(String key, String elementKey, double score) {
        redisTemplate.opsForZSet().add(key, elementKey,score);
    }


    /**
     * @Author: wx
     * @Desc: 新增分数
     * @Date: 2:00 PM 2019/10/8
     * @param: key
     * @param: elementKey
     * @return: java.lang.Long
     **/
    public Long addZSetScore(String key, String elementKey) {
        return updateZetScore(key,elementKey,1);
    }


    /**
     * @Author: wx
     * @Desc: 减少分数
     * @Date: 2:00 PM 2019/10/8
     * @param: key
     * @param: elementKey
     * @return: java.lang.Long
     **/
    public Long reduceZSetScore(String key, String elementKey) {
        Long score = updateZetScore(key,elementKey,-1);
        if(score <= 0){
            return 0L;
        }
        return score;
    }

    /**
     * @Author: wx
     * @Desc: 更新分数
     * @Date: 2:03 PM 2019/10/8
     * @param: key
     * @param: elementKey
     * @return: java.lang.Long
     **/
    public Long updateZetScore(String key, String elementKey,double score) {
        Double incrementScore = redisTemplate.opsForZSet().incrementScore(key, elementKey, score);
        return incrementScore == null ? 0L : incrementScore.longValue();
    }

    /**
     * @Author: wx
     * @Desc: 获取缓存Zset结构
     * @Date: 5:54 PM 2019/6/27
     * @param: key
     * @return: org.springframework.data.redis.core.BoundZSetOperations
     **/
    public BoundZSetOperations getZSet(String key) {
        return redisTemplate.boundZSetOps(key);
    }

    /**
     * @Author: wx
     * @Desc: 获取Hset长度
     * @Date: 5:56 PM 2019/6/27
     * @param: key
     * @return: java.lang.Long
     **/
    public Long getZSetSize(String key) {
        BoundZSetOperations hSet = getZSet(key);
        if (hSet == null) {
            return 0L;
        }
        return hSet.size();
    }

    /**
     * @Author: wx
     * @Desc: 获取Hset分数
     * @Date: 2:11 PM 2019/10/8
     * @param: key
     * @param: id
     * @return: java.lang.Long
     **/
    public Long getZSetScore(String key,String id){
        Double score = redisTemplate.opsForZSet().score(key, id);
        return score == null ? 0L : score.longValue();
    }

    /**
     * @Author: wx
     * @Desc: 获取Zset范围数据
     * @Date: 2:35 PM 2019/10/8
     * @param: key
     * @param: start
     * @param: end
     * @return: java.util.Set<java.lang.String>
     **/
//    public Set<String> getZSetrange(String key, long start, long end){
//        Set<Object> rangeSet = redisTemplate.opsForZSet().reverseRange(key, start, end);
//        if (CollectionUtil.isEmpty(rangeSet)){
//            return Sets.newHashSetWithExpectedSize(0);
//        }
//        Set<String> rangeSetStr = Sets.newHashSetWithExpectedSize(rangeSet.size());
//        rangeSet.forEach(elementKey -> rangeSetStr.add(String.valueOf(elementKey)));
//        return rangeSetStr;
//    }

    /**
     * @Author: wx
     * @Desc: 删除Zset元素数据
     * @Date: 6:40 PM 2019/10/8
     * @param: key
     * @param: values
     * @return: java.util.Set<java.lang.String>
     **/
    public Boolean removeZSetElement(String key, String... values){
        long removeCount = redisTemplate.opsForZSet().remove(key,values);
        return removeCount == values.length;
    }

    /**
     * @param key
     * @param expire
     * @methodName: expire
     * @author: wx
     * @description: 缓存设置过期时间
     * @date: 2019/4/15
     * @return: void
     */
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param fields
     * @methodName: hDel
     * @author: wx
     * @description: 删除缓存 map结构
     * @date: 2019/4/15
     * @return: void
     */
    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * @param key
     * @param value
     * @methodName: leftPush
     * @author: wx
     * @description: 新增缓存 队列结构 从头入队，默认超时时间24小时
     * @date: 2019/4/15
     * @return: void
     */
    public void leftPush(String key, Object value) {
        leftPush(key, value, DEFAULT_EXPIRE);
    }

    /**
     * @param key
     * @param value
     * @param expire
     * @methodName: leftPush
     * @author: wx
     * @description: 新增缓存 队列结构 从头入队，设置超时时间
     * @date: 2019/4/15
     * @return: void
     */
    public void leftPush(String key, Object value, long expire) {
        redisTemplate.opsForList().leftPush(key, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * @param key
     * @methodName: rightPop
     * @author: wx
     * @description: 获取缓存 队列结构，从尾部出队
     * @date: 2019/4/15
     * @return: java.lang.Object
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }


    /**
     * 分页获取redis数据
     *
     * @param key     key值
     * @param subKey
     * @param by      排序字段
     * @param isDesc  是否倒序
     * @param isAlpha 是否按照字母顺序
     * @param off     起始位置
     * @param num     加载数量
     * @param <T>     泛型
     * @return
     * @throws Exception
     */
    public <T> List<T> sortPageList(String key, String subKey, String by, boolean isDesc, boolean isAlpha, int off, int num) throws Exception {
        SortQueryBuilder<String> builder = SortQueryBuilder.sort(key);
        builder.by(subKey + "*->" + by);
        builder.get("#");
        builder.alphabetical(isAlpha);
        if (isDesc) {
            builder.order(SortParameters.Order.DESC);
        }
        builder.limit(off, num);
        List cks = redisTemplate.sort(builder.build());
        List<T> result = new ArrayList<T>();
        for (Object ck : cks) {
            //得到项目对象 by(subKey+ck);
            result.add((T) ck);
        }
        return result;
    }


    /**
     * @Author: wx
     * @Desc: 如果键不存在则新增,存在则不改变已经有的值
     * @Date: 5:46 PM 2019/9/3
     * @param: key
     * @param: value
     * @param: expire
     * @return: boolean true 不存在 false存在
     **/
    public boolean setNx(String key, Object value, long expire) {
        RedisScript<String> redisScript = new DefaultRedisScript<>(setNxStr, String.class);
        String result = redisTemplate.execute(redisScript, Collections.singletonList(key), value, expire);
        return Objects.equals("1L", result);
    }

    /**
     * @Author: wx
     * @Desc: 如果键不存在则新增,存在则不改变已经有的值
     * @Date: 5:46 PM 2019/9/3
     * @param: key
     * @param: value
     * @param: expire
     * @return: boolean true 不存在 false存在
     **/
    public boolean setNx(String key, Object value) {
        return setNx(key, value, HOUR_ONE_EXPIRE);
    }
}