package com.boot.sevice;

import com.boot.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by abee on 17-3-10.
 */
@Service
public class UserServcieImpl implements UserService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //利用jaskon进行序列化和反序列化
    ObjectMapper mapper = new ObjectMapper();


    /**
     * 用于创建锁
     *
     * @param userName
     * @param lockTime
     * @return
     */
    public String acquireLockWithTimeout( String userName, int lockTime) {


        //获取锁.uuid
        String Id = UUID.randomUUID().toString();

        //过期时间设置为10s
        long endTime = System.currentTimeMillis() + lockTime * 1000;

        //使用while循环等待
        while (System.currentTimeMillis() < endTime) {

            redisTemplate.opsForValue().set(userName,Id,lockTime);

            if(redisTemplate.getExpire(userName)>=0){

                return Id;

            }else {

                redisTemplate.expire(userName,lockTime, TimeUnit.SECONDS);

            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 创建用户
     *
     * @param userName
     * @return
     */
    @Override
    public long createUser(String userName, int lockTime) {

        //获取锁
        String lock = acquireLockWithTimeout(userName, lockTime);

        if (lock == null) {

            return -1;//锁创建失败
        }

        if (redisTemplate.opsForHash().get("users",userName) != null) {

            return -1;//用户名已经存在
        }
        Long id = redisTemplate.opsForValue().increment("user:id",0);

        //开启事物
        redisTemplate.multi();

        Map<String, String> values = new HashMap<>();

        values.put("userName", userName);
        values.put("id", String.valueOf(id));
        try {
            redisTemplate.opsForHash().put("users",userName,values);//mapper.writeValueAsString(values)

        } catch (Exception e) {
            e.printStackTrace();
        }
        //执行
        redisTemplate.exec();

        Map<String, String> users =(HashMap<String, String>) redisTemplate.opsForHash().get("users", userName);

        //释放锁
        releaseLock(userName, lock);

        return id;
    }


    /**
     * 释放锁
     *
     * @param userName
     * @param lock
     * @return
     */
    public Boolean releaseLock(String userName, String lock) {

        while (true) {

            redisTemplate.watch(userName);

            if (lock.equals(redisTemplate.opsForValue().get(userName))) {
                redisTemplate.multi();
                redisTemplate.delete(userName);
                List<Object> exec = redisTemplate.exec();

                if (exec == null) {
                    continue;
                }
                return true;
            }
            redisTemplate.unwatch();

            break;
        }
        return false;
    }
}
