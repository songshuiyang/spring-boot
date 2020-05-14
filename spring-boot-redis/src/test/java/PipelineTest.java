import com.songsy.springboot.redis.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author songsy
 * @date 2020/5/14 11:00
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PipelineTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test () {
        // 使用 RedisCallback 把命令放在 pipeline 中
        RedisCallback<Object> redisCallback = connection -> {

            StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
            for (int i = 0; i != 10; ++i) {
                stringRedisConn.set(String.valueOf(i), String.valueOf(i));
            }

            return null;    // 这里必须要返回 null
        };
        System.out.println(stringRedisTemplate.executePipelined(redisCallback));

        // 使用 SessionCallback 把命令放在 pipeline
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                operations.opsForValue().set("name", "qinyi");
                operations.opsForValue().set("gender", "male");
                operations.opsForValue().set("age", "19");

                return null;
            }
        };
        System.out.println(stringRedisTemplate.executePipelined(sessionCallback));
    }

}
