package utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wah.cloned.im.tencent.consts.IMMessageType;
import org.wah.cloned.im.tencent.dao.IMAppletDao;
import org.wah.cloned.im.tencent.dao.IMUserDao;
import org.wah.cloned.im.tencent.entity.IMMessage;
import org.wah.cloned.im.tencent.entity.IMMessageBody;
import org.wah.cloned.im.tencent.entity.IMUser;
import org.wah.cloned.im.tencent.security.response.IMOperationResponse;
import org.wah.cloned.im.tencent.utils.IMUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
@ActiveProfiles(value = "test")
public class IMUtilsTest{

    @Autowired
    private IMAppletDao imAppletDao;

    @Autowired
    private IMUserDao imUserDao;

    @Test
    public void sendMsg(){
        String appletId = "0ac54d16014f497eaf67d2750e10365e";
        String fromAccount = "836fa75feb314751a3684fc75a293abe";
        String toAccount = "4be7e788746c4cfdb1017e8cf05af01d";

        //from account
        IMUser from = imUserDao.getById(fromAccount);
        //to account
        IMUser to = imUserDao.getById(toAccount);
        //admin
        IMUser admin = imUserDao.getAdminByAppletId(appletId);

        //消息组
        List<IMMessage> messages = new ArrayList<IMMessage>();

        IMMessageBody body = new IMMessageBody();
        body.setFromAccount(from.getName());
        body.setToAccount(to.getName());
        body.setMessages(messages);

        //消息
        Map<String, String> content1 = new HashMap<String, String>();
        content1.put("Text", "测试");
        IMMessage message1 = new IMMessage();
        message1.setType(IMMessageType.TEXT);
        message1.setContent(content1);
        messages.add(message1);

        Map<String, String> content2 = new HashMap<String, String >();
        content2.put("Text", "test2");
        IMMessage message2 = new IMMessage();
        message2.setType(IMMessageType.TEXT);
        message2.setContent(content2);
        messages.add(message2);

        IMUtils.sendMsg(admin.getSig(), admin.getAppId(), admin.getName(), body);
    }

    @Test
    public void operation(){
        String appletId = "0ac54d16014f497eaf67d2750e10365e";

        //admin
        IMUser admin = imUserDao.getAdminByAppletId(appletId);
        IMOperationResponse response = IMUtils.appInfo(admin.getSig(), admin.getAppId(), admin.getName());

        for(Map<String, String> info : response.getResult()){
            for(String key : info.keySet()){
                System.out.println(key + " : " + info.get(key));
            }
        }
    }
}
