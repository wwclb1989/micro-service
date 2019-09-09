package demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.pool.GetConnectionTimeoutException;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class DruidTest {

    private volatile static int count;

    public static void main(String[] args) throws Exception {

        Properties prop = new Properties();
        // 加载配置文件
        prop.load(new FileInputStream("/data/jdbc.properties"));

        // 创建连接池
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName(prop.getProperty("driverName"));  // 驱动
        dataSource.setUrl(prop.getProperty("url"));                     // url
        dataSource.setUsername(prop.getProperty("username"));           // username
        dataSource.setPassword(prop.getProperty("password"));           // password
        System.out.println("url:" + prop.getProperty("url"));
        System.out.println("username:" + prop.getProperty("username") + ", password:" + prop.getProperty("password"));
        // 其它参数
        if (!StringUtils.isEmpty(prop.getProperty("initialSize"))) {
            dataSource.setInitialSize(Integer.parseInt(prop.getProperty("initialSize")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("maxActive"))) {
            dataSource.setMaxActive(Integer.parseInt(prop.getProperty("maxActive")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("minIdle"))) {
            dataSource.setMinIdle(Integer.parseInt(prop.getProperty("minIdle")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("maxWait"))) {
            dataSource.setMaxWait(Long.parseLong(prop.getProperty("maxWait")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("timeBetweenEvictionRunsMillis"))) {
            dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(prop.getProperty("timeBetweenEvictionRunsMillis")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("minEvictableIdleTimeMillis"))) {
            dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(prop.getProperty("minEvictableIdleTimeMillis")));
        }
        dataSource.setValidationQuery(prop.getProperty("validationQuery"));
        if (!StringUtils.isEmpty(prop.getProperty("testOnBorrow"))) {
            dataSource.setTestOnBorrow(Boolean.parseBoolean(prop.getProperty("testOnBorrow")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("testOnReturn"))) {
            dataSource.setTestOnReturn(Boolean.parseBoolean(prop.getProperty("testOnReturn")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("removeAbandoned"))) {
            dataSource.setRemoveAbandoned(Boolean.parseBoolean(prop.getProperty("removeAbandoned")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("removeAbandonedTimeout"))) {
            dataSource.setRemoveAbandonedTimeout(Integer.parseInt(prop.getProperty("removeAbandonedTimeout")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("logAbandoned"))) {
            dataSource.setLogAbandoned(Boolean.parseBoolean(prop.getProperty("logAbandoned")));
        }
        if (!StringUtils.isEmpty(prop.getProperty("TestWhileIdle"))) {
            dataSource.setTestWhileIdle(Boolean.parseBoolean(prop.getProperty("TestWhileIdle")));
        }

        String sql = prop.getProperty("sql");
        System.out.println("sql:" + sql);

        // id范围
        int min = Integer.parseInt(prop.getProperty("min"));
        int max = Integer.parseInt(prop.getProperty("max"));

        Runnable r = () -> {
            int i = 0;
            while (true) {
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("已执行" + i + "秒，请求总数：" + count + "，QPS：" + (count / i));
            }

        };
        new Thread(r).start();

        Runnable runnable = () -> {
//            System.out.println("线程" + Thread.currentThread().getName() + "启动！");
            Connection conn = null;
            for (int i = min; i <= max; i++) {
                try {
                    // 执行语句
                    conn = dataSource.getConnection();
                    PreparedStatement pst = conn.prepareStatement(sql);
                    count++;
                    pst.setInt(1, i);
                    pst.execute();
                    conn.close();
                } catch (GetConnectionTimeoutException e) {
                    System.out.println(Thread.currentThread().getName() + "线程连接超时！");
                } catch (SQLException e) {
                    System.out.println(Thread.currentThread().getName() + "线程执行sql发生异常！");
                }
            }
            System.out.println("线程" + Thread.currentThread().getName() + "运行结束！");
        };

        int threadNumber = Integer.parseInt(prop.getProperty("threadNumber"));
        for (int i = 0; i < threadNumber; i++) {
            new Thread(runnable).start();
        }

    }

}
