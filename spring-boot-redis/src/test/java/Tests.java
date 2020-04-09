import com.songsy.springboot.redis.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class Tests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1 () {

        for (int i = 1; i < 20; i ++) {
            stringRedisTemplate.opsForList().rightPush("list_order", String.valueOf(i));
        }

        for (int i = 1; i < 20; i ++) {
            log.info(stringRedisTemplate.opsForList().leftPop("list_order"));
        }

    }

}