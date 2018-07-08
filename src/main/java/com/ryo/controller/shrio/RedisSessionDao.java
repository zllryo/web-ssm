package com.ryo.controller.shrio;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisSessionDao extends EnterpriseCacheSessionDAO  {

    private String keyPrefix = "shiro_redis_session:";

    @Autowired
    @Qualifier("jedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建session，保存到redis集群中
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        System.out.println("sessionId: " + sessionId);

        BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps(keyPrefix + sessionId.toString());
        sessionValueOperations.set(session);
        sessionValueOperations.expire(30, TimeUnit.MINUTES);
        redisTemplate.getConnectionFactory().getConnection().close();
        return sessionId;
    }

    /**
     * 获取session
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);

        if(session == null){
            BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps(keyPrefix + sessionId.toString());
            session = (Session) sessionValueOperations.get();
        }
        redisTemplate.getConnectionFactory().getConnection().close();
        return session;
    }

    /**
     * 更新session
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);

        BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps(keyPrefix + session.getId().toString());
        sessionValueOperations.set(session);
        sessionValueOperations.expire(30, TimeUnit.MINUTES);
        redisTemplate.getConnectionFactory().getConnection().close();
    }

    /**
     * 删除失效session
     */
    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete(keyPrefix + session.getId().toString());
        super.doDelete(session);
        redisTemplate.getConnectionFactory().getConnection().close();
    }


}

