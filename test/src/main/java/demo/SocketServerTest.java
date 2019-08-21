package demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerTest {
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8189);
            Socket incoming = s.accept();       // 告诉服务器一直等待，直到有人连接到这个端口

            InputStream inputStream = incoming.getInputStream();
            OutputStream outputStream = incoming.getOutputStream();

            Scanner in = new Scanner(inputStream);
            PrintWriter out = new PrintWriter(outputStream, true);

            out.println("Hello, enter Bye to exit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
