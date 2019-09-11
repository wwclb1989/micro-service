package demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.GetConnectionTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

public class DruidTest {

    private static final Logger logger = LoggerFactory.getLogger(DruidTest.class);

    private volatile static int count;

    public static void main(String[] args) throws Exception {

        Properties prop = new Properties();
        // 加载配置文件
        prop.load(new FileInputStream("/data/jdbc.properties"));

        // 创建连接池
        DruidDataSource dataSource = getDataSource(prop);

        // sql
        String[] sqls = prop.getProperty("sqls").split(";");
        Arrays.stream(sqls).forEach(s -> logger.warn("sql:{}", s));

        // id范围
        String[] mins = prop.getProperty("min").split(";");
        String[] maxs = prop.getProperty("max").split(";");

        // 开始计数
//        startCount();

        int threadNumber = Integer.parseInt(prop.getProperty("threadNumber"));
        for (int i = 0; i < threadNumber; i++) {
            for (int j = 0; j < sqls.length; j++) {
                startRequest(dataSource, sqls[j], Integer.parseInt(mins[j]), Integer.parseInt(maxs[j]));
            }
        }

    }

    private static DruidDataSource getDataSource(Properties prop) throws SQLException {
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
        if (!StringUtils.isEmpty(prop.getProperty("filters"))) {
            dataSource.setFilters(prop.getProperty("filters"));
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

        return dataSource;
    }

    private static void startCount() {
        // 记数线程
        Runnable r = () -> {
            int i = 0;
            while (true) {
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.warn("已执行{}秒，请求总数：{}，QPS：{}", i, count, count / i);
            }

        };
        new Thread(r).start();
    }

    private static void startRequest(DruidDataSource dataSource, String sql, int min, int max) {
        // 请求数据库线程
        Runnable runnable = () -> {
            Connection conn = null;
            for (int i = min; i <= max; i++) {
                // 一个循环模拟一次请求
                try {
                    // 执行语句
                    conn = dataSource.getConnection();
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, i);
                    if (sql.contains("dataNodeId")) {
                        int dataNodeId = Integer.parseInt((i + "").substring(2, 3));
                        pst.setInt(2, dataNodeId);
                    }
//                    count++;
                    pst.execute();
                } catch (GetConnectionTimeoutException e) {
                    logger.error("线程{}连接超时！", Thread.currentThread().getName(), e);
//                    System.out.println(Thread.currentThread().getName() + "线程连接超时！");
                } catch (SQLException e) {
                    logger.error("线程{}执行sql发生异常", Thread.currentThread().getName(), e);
//                    System.out.println(Thread.currentThread().getName() + "线程执行sql发生异常！");
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        logger.error("close connection error:", e);

                    }
                }
            }
            logger.warn("线程{}运行结束！", Thread.currentThread().getName());
        };

        new Thread(runnable).start();
    }
}
