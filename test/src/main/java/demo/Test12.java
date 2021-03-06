package demo;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;

public class Test12 {

    public static final String HOST = "172.20.19.5";   // 主机
    public static final String PORT = "3306";           // mysql端口号
    public static final String[] DB_NAME = {
            "baseconfigservice","configservice", "couponservice", "driving","fileexport", "financesystem", "insurancecashierdesk", "insurancemember", "insuranceorder", "memberservice",
            "orderservice", "paygateway", "quotationmanager", "schedulesystem", "thirdplatform", "transactionmessage", "workflow"
    };    // 数据库名称，严格区分大小写
    public static final String USERNAME = "pubuser";    // 用户
    public static final String PASSWORD = "Z883eD21eo21q0E7HAkftKXOZ";     // 密码

    public static final String DIRECTORYPATH = "F://table/";      // 输出路径

    static {
        File file = new File(DIRECTORYPATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void main(String[] args) {

        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            for (String dbname : DB_NAME) {
                String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + dbname + "?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
                //2. 获得数据库连接
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                //3.操作数据库，实现增删改查,show tables
//                PreparedStatement pst = conn.prepareStatement("show tables");
                PreparedStatement pst = conn.prepareStatement("select table_name,table_comment from information_schema.tables where table_schema = '" + dbname + "'");

                String filePath = DIRECTORYPATH + dbname + ".xls";   // 文件路径，一个数据库一个文件
                FileOutputStream out = new FileOutputStream(filePath);  // 文件输出流

                // 创建工作簿
                HSSFWorkbook workbook = new HSSFWorkbook();     // 创建workbook
                HSSFSheet sheet = workbook.createSheet();       // 创建sheet
                CellStyle style1 = createStyle1(workbook);
                CellStyle style2 = createStyle2(workbook);
                CellStyle style3 = createStyle3(workbook);
                createTableHead(sheet, style1);

                int rowNum = 1;

                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    // 表名
                    String tableName = resultSet.getString(1);
                    String tableComment = resultSet.getString(2);
//                    String tableComment = null;
                    if (tableComment == null || tableComment.equals("")) {
                        tableComment = "无";
                    }
                    Statement st = conn.createStatement();
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery("show full columns from " + tableName);
                    } catch (SQLException e) {
                        System.out.println(dbname + "->" + tableName);
                        continue;
                    }
                    int colNum = 0; // 字段的个数

                    while (rs.next()) {
                        HSSFRow row = sheet.createRow(rowNum++);
                        HSSFCell cell0 = row.createCell(0);
                        if (colNum == 0) {
                            cell0.setCellValue(tableName + "\n(" + tableComment + ")");
                        }
                        cell0.setCellStyle(style2);
                        createRow(row, rs, style3);
                        colNum++;
                    }

                    // 合并单元格
                    CellRangeAddress cra = new CellRangeAddress(rowNum - colNum, rowNum - 1, 0, 0); // 起始行, 终止行, 起始列, 终止列
                    sheet.addMergedRegion(cra);

                    HSSFRow sep = sheet.createRow(rowNum++);
                    sep.createCell(0).setCellStyle(style1);
                    CellRangeAddress cra2 = new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 6); // 起始行, 终止行, 起始列, 终止列
                    sheet.addMergedRegion(cra2);
                }
                workbook.write(out);//保存Excel文件
                out.close();//关闭文件流
                resultSet.close();
                pst.close();
                conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 以style背景创建表头
    public static void createTableHead(HSSFSheet sheet, CellStyle style) {

        sheet.setColumnWidth(0, 25 * 256);
        sheet.setColumnWidth(1, 30 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 10 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 40 * 256);

        // 创建表头
        HSSFRow row0 = sheet.createRow(0);

        HSSFCell cell00 = row0.createCell(0);
        cell00.setCellValue("表名");
        cell00.setCellStyle(style);
        HSSFCell cell01 = row0.createCell(1);
        cell01.setCellValue("Field");
        cell01.setCellStyle(style);
        HSSFCell cell02 = row0.createCell(2);
        cell02.setCellValue("Type");
        cell02.setCellStyle(style);
        HSSFCell cell03 = row0.createCell(3);
        cell03.setCellValue("Null");
        cell03.setCellStyle(style);
        HSSFCell cell04 = row0.createCell(4);
        cell04.setCellValue("Key");
        cell04.setCellStyle(style);
        HSSFCell cell05 = row0.createCell(5);
        cell05.setCellValue("default");
        cell05.setCellStyle(style);
        HSSFCell cell06 = row0.createCell(6);
        cell06.setCellValue("comment");
        cell06.setCellStyle(style);
    }

    public static CellStyle createStyle1(HSSFWorkbook workbook) {
        CellStyle style1 = workbook.createCellStyle();
        style1.setFillForegroundColor(IndexedColors.LIME.getIndex());
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
        style1.setAlignment(HorizontalAlignment.CENTER);//水平
        return style1;
    }

    public static CellStyle createStyle2(HSSFWorkbook workbook) {
        CellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
        style2.setAlignment(HorizontalAlignment.CENTER);//水平
        style2.setWrapText(true);
        return style2;
    }

    public static CellStyle createStyle3(HSSFWorkbook workbook) {
        CellStyle style3 = workbook.createCellStyle();
        style3.setBorderBottom(BorderStyle.THIN);
        style3.setBorderLeft(BorderStyle.THIN);
        style3.setBorderRight(BorderStyle.THIN);
        style3.setBorderTop(BorderStyle.THIN);
        style3.setWrapText(true);
        return style3;
    }

    public static void createRow(HSSFRow row, ResultSet rs, CellStyle style3) throws SQLException {
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(rs.getString("Field"));
        cell1.setCellStyle(style3);
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue(rs.getString("Type"));
        cell2.setCellStyle(style3);
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue(rs.getString("Null"));
        cell3.setCellStyle(style3);
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue(rs.getString("Key"));
        cell4.setCellStyle(style3);
        HSSFCell cell5 = row.createCell(5);
        cell5.setCellValue(rs.getString("Default"));
        cell5.setCellStyle(style3);
        HSSFCell cell6 = row.createCell(6);
        cell6.setCellValue(rs.getString("Comment"));
        cell6.setCellStyle(style3);
    }

}
