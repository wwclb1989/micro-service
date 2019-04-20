package demo;

import java.sql.*;

public class Test12 {

    public static final String URL = "jdbc:mysql://localhost:3306/manager?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";


    public static void main(String[] args) {

        try {
            //1.加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 获得数据库连接
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //3.操作数据库，实现增删改查
            PreparedStatement pst = conn.prepareStatement("show tables");

            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                // 表名
                String tableName = resultSet.getString(1);
//                System.out.println(resultSet.getString(1));

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("show full columns from " + tableName);
                while (rs.next()) {
                    System.out.print(rs.getString("Field") + "\t");
                    System.out.println(rs.getString("Comment"));
                }
            }

//            stmt.execute("show full columns from sysuser")

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
