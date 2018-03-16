package utils;

import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wah.cloned.commons.security.consts.CacheParamName;
import org.wah.cloned.commons.security.context.ApplicationContextUtils;
import org.wah.doraemon.utils.RedisUtils;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class CacheUtilsTest{

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Test
    public void cache(){
        WeChatMessage message_1 = new WeChatMessage();
        message_1.setWechatId("1");

        WeChatMessage message_2 = new WeChatMessage();
        message_2.setWechatId("2");

        WeChatMessage message_3 = new WeChatMessage();
        message_3.setWechatId("3");

        List<WeChatMessage> list = new ArrayList<WeChatMessage>();
        list.add(message_1);
        list.add(message_2);
        list.add(message_3);

        RedisUtils.rpush(shardedJedisPool.getResource(), CacheParamName.WECHAT_MESSAGE_LIST, list);
    }

    @Test
    public void cacheByContext(){
        ShardedJedisPool pool = (ShardedJedisPool) ApplicationContextUtils.getById("shardedJedisPool");

        WeChatMessage message_1 = new WeChatMessage();
        message_1.setWechatId("1");

        WeChatMessage message_2 = new WeChatMessage();
        message_2.setWechatId("2");

        WeChatMessage message_3 = new WeChatMessage();
        message_3.setWechatId("3");

        List<WeChatMessage> list = new ArrayList<WeChatMessage>();
        list.add(message_1);
        list.add(message_2);
        list.add(message_3);

        RedisUtils.rpush(pool.getResource(), CacheParamName.WECHAT_MESSAGE_LIST + ":1", list);
    }
}
