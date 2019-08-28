package demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZkTest implements Watcher {
    ZooKeeper zk;
    private String hostPort;

    ZkTest(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    @Override
    public void process(WatchedEvent e) {
        System.out.println(e);
    }

    public static void main(String[] args) throws Exception {
        ZkTest m = new ZkTest("172.16.4.44:2181");

        m.startZK();

        Thread.sleep(60000);
    }
}
