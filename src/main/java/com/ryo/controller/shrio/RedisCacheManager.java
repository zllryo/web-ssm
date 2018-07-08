package com.ryo.controller.shrio;

import com.ryo.model.User;
import com.ryo.utils.ByteSourceUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

public class RedisCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new RedisCache<K,V>();
    }


    public class RedisCache<K,V> implements Cache<K,V> {

        public String getKeyPrefix() {
            return keyPrefix;
        }

        public void setKeyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
        }

        private String keyPrefix = "shiro_redis_session:";
        /**
         * 获得byte[]型的key
         * @param key
         * @return
         */
        private byte[] getByteKey(Object key){
            if(key instanceof String){
                String preKey = this.keyPrefix + key;
                return preKey.getBytes();
            }else{
                return ByteSourceUtils.serialize((Serializable) key);
            }
        }


        @Override
        public Object get(Object key) throws CacheException {

            byte[] bytes = getByteKey(key);
            byte[] value = JedisClientSingle.getJedis().get(bytes);
            if(value == null){
                return null;
            }
            JedisClientSingle.close(JedisClientSingle.getJedis());
            return ByteSourceUtils.deserialize(value);
        }

        /**
         * 将shiro的缓存保存到redis中
         */
        @Override
        public Object put(Object key, Object value) throws CacheException {

            Jedis jedis = JedisClientSingle.getJedis();

            jedis.set(getByteKey(key), ByteSourceUtils.serialize((Serializable)value));
            byte[] bytes = jedis.get(getByteKey(key));
            Object object = ByteSourceUtils.deserialize(bytes);
            JedisClientSingle.close(JedisClientSingle.getJedis());
            return object;

        }

        @Override
        public Object remove(Object key) throws CacheException {
            Jedis jedis = JedisClientSingle.getJedis();

            byte[] bytes = jedis.get(getByteKey(key));

            jedis.del(getByteKey(key));
            JedisClientSingle.close(JedisClientSingle.getJedis());
            return ByteSourceUtils.deserialize(bytes);
        }

        /**
         * 清空所有缓存
         */
        @Override
        public void clear() throws CacheException {
            JedisClientSingle.getJedis().flushDB();
            JedisClientSingle.close(JedisClientSingle.getJedis());
        }

        /**
         * 缓存的个数
         */
        @Override
        public int size() {
            Long size = JedisClientSingle.getJedis().dbSize();
            JedisClientSingle.close(JedisClientSingle.getJedis());
            return size.intValue();
        }

        /**
         * 获取所有的key
         */
        @Override
        public Set keys() {
            Set<byte[]> keys = JedisClientSingle.getJedis().keys(new String("*").getBytes());
            Set<Object> set = new HashSet<Object>();
            for (byte[] bs : keys) {
                set.add(ByteSourceUtils.deserialize(bs));
            }
            JedisClientSingle.close(JedisClientSingle.getJedis());
            return set;
        }


        /**
         * 获取所有的value
         */
        @Override
        public Collection values() {
            Set keys = this.keys();

            List<Object> values = new ArrayList<Object>();
            for (Object key : keys) {
                byte[] bytes = JedisClientSingle.getJedis().get(getByteKey(key));
                values.add(ByteSourceUtils.deserialize(bytes));
            }
            JedisClientSingle.close(JedisClientSingle.getJedis());
            return values;
        }
    }


}

