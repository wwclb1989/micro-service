package demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DruidTest {

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
        System.out.println("username:" + prop.getProperty("username") + ",password:" + prop.getProperty("password"));
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

//        int requestNumber = Integer.parseInt(prop.getProperty("requestNumber"));
        int min = Integer.parseInt(prop.getProperty("min"));
        int max = Integer.parseInt(prop.getProperty("max"));


        Runnable runnable = () -> {
            try {
                Connection conn = dataSource.getConnection();

                // 执行语句
                for (int i = min; i <= max; i++) {
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, i);
                    System.out.println("线程" + Thread.currentThread().getName() + "请求sql:" + pst);
                    pst.execute();
                    ResultSet resultSet = pst.executeQuery();
                    while(resultSet.next()){
                        System.out.println(resultSet.getString("name"));
                        System.out.println(resultSet.getString("phone"));
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        int threadNumber = Integer.parseInt(prop.getProperty("threadNumber"));
        for (int i = 0; i < threadNumber; i++) {
            new Thread(runnable).start();
        }

    }
}
