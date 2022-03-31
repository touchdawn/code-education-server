package com.itheima.redis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.itheima.domain.RedisTestDomain;
import com.itheima.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisTest {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;
    @Test
    void contextLoads(){

//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        connection.flushAll();

        redisTemplate.opsForValue().set("myKey","redisTest");
        Object myKey = redisTemplate.opsForValue().get("myKey");
        System.out.println(myKey);
    }


    @Test
    public void test() throws JsonProcessingException {
        //
        RedisTestDomain testDomain = new RedisTestDomain("我", 3);
//        String jsonUser = new ObjectMapper().writeValueAsString(testDomain);
        redisTemplate.opsForValue().set("user",testDomain);
        Object user = redisTemplate.opsForValue().get("user");
        System.out.println(user);
    }

    @Test
    public void  utilTest(){
        redisUtil.set("name","myname");
        System.out.println(redisUtil.get("name"));
    }
}
