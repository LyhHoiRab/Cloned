package socket;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest{

    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(9988);
        Socket socket = null;

        socket = server.accept();

        System.out.println(socket);
        System.out.println("链接成功");

        while(true){
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //读取客户端发送来的消息
            String message = br.readLine();
            if(StringUtils.isNotBlank(message)){
                System.out.println("客户端：" + message);
            }
        }
    }
}
