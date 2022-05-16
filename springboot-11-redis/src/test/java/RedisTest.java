import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


@SpringBootTest(classes = RedisTest.class)
@EnableAutoConfiguration
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void test() throws JsonProcessingException {
        //redisTemplate  操作不同的数据类型，api和我们的指令是一样的
        //opsForValue  操作字符串 类似String
        //opsForList  操作List 类似List
        //opsForSet
        //opsForHash
        //opsForZSet
        //opsForGeo

        //除了基本的操作，我们常用的方法都是可以直接通过redisTemplate操作

        //获取redis的连接对象
        /*RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushAll();
        connection.flushDb();*/

        redisTemplate.opsForValue().set("chj","aa");
        System.out.println(redisTemplate.opsForValue().get("chj"));



    }
}
