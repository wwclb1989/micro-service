package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketTest {

    public static void main(String[] args) {


        try {
            InetAddress address = InetAddress.getByName("www.baidu.com");
            System.out.println(address);
            System.out.println(InetAddress.getByName("localhost"));
            System.out.println(InetAddress.getLocalHost());

//            Socket s = new Socket("time-A.timefreq.bldrdoc.gov", 13);
            Socket s = new Socket("localhost", 8189);
            s.setSoTimeout(10000);
            InputStream inputStream = s.getInputStream();

            Scanner in = new Scanner(inputStream);

            while (in.hasNext()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
