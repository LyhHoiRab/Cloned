package hook;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Arrays;

public class CMDUtilsTest{

    @Test
    public void cmd() throws Exception{
        String cmd = "C:\\Users\\xiuxiu\\Desktop\\ngrok\\ngrok http 8080";

        Runtime.getRuntime().exec(cmd);
    }

    @Test
    public void test(){
        System.out.println("304e020100044730450201000204311237c702033d14b9020469fd03b702045ae2c871042063656163356330633531303763376130306637376264393332626530356634390204010800010201000400".length());
    }

    @Test
    public void decrypt() throws Exception{
        String content = "304e020100044730450201000204311237c702033d14b9020469fd03b702045ae2c871042063656163356330633531303763376130306637376264393332626530356634390204010800010201000400";
        String encodingAESKey = "b3403a4de4e649f0ab221c5a367d0d19";

        byte[] aesKey = Base64.decodeBase64(encodingAESKey + "=");
        byte[] original;

        //设置解密模式
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
        IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
        cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
        //解密
        original = cipher.doFinal(Base64.decodeBase64(content));



        //去补位
//        int pad = (int) original[original.length -1];
//        if(pad < 1 || pad > 32){
//            pad = 0;
//        }
//        byte[] bytes = Arrays.copyOfRange(original, 0, original.length - pad);
//        //分离16位随机字符串,网络字节序和AppId
//        byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
//        int sourceNumber = 0;
//        for(int i = 0; i < 4; i++){
//            sourceNumber <<= 8;
//            sourceNumber |= networkOrder[i] & 0xff;
//        }
//        //解密后明文
//        content = new String(Arrays.copyOfRange(bytes, 20, 20 + sourceNumber), Charset.forName("utf-8"));

        System.out.println(new String(original, Charset.forName("utf-8")));
    }
}
