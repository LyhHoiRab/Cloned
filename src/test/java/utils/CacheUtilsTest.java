package utils;

import io.github.biezhi.wechat.api.model.WeChatMessage;
import org.wah.cloned.commons.utils.CacheUtils;

import java.util.Arrays;

public class CacheUtilsTest{

    public static void main(String[] args){
        Thread add_1 = new Thread(new Add("1"));
        Thread add_2 = new Thread(new Add("2"));
        Thread next_1 = new Thread(new Next("1"));
        Thread next_2 = new Thread(new Next("2"));

        add_1.start();
        add_2.start();
        next_1.start();
//        next_2.start();
    }

    public static class Add implements Runnable{

        public String index;

        public Add(String index){
            this.index = index;
        }

        @Override
        public void run(){
//            System.out.println("Add_" + index + ":");

            for(int i = 0 ; i < 10; i++){
                WeChatMessage message = new WeChatMessage();
                message.setWechatId(index + "_" + i);
                CacheUtils.addMessages(Arrays.asList(message));
                System.out.println("Add_" + index + "======================>" + message.getWechatId());
            }
        }
    }

    public static class Next implements Runnable{

        public String index;

        public Next(String index){
            this.index = index;
        }

        @Override
        public void run(){
            int i = 0;
            boolean flag = true;

            while(flag){
//                System.out.println("Next_" + index + ":");

                if(CacheUtils.hasMessage()){
                    WeChatMessage message = CacheUtils.nextMessage();
                    System.out.println("Next_" + index + "======================>" + message.getWechatId());

                    i += 1;

                    if(i == 20){
                        flag = false;
                    }
                }
            }
        }
    }
}
