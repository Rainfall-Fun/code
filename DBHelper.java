import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * 此工具方法需要导入 MySQL JDBC 包（仅测试5.1.39）
 * 数据库连接用方法，使用getConnection()建立链接，使用excuteQuery()和excute()执行数据库语句。
 * sql语句中的参数用 ? 代替，被问号的代替的值装在一个 Object[] 数组中作为 params 传入方法
 * 有返回值的查询函数返回值类型为ResultSet
 * PS：用完记得close()
 * 
 * 修改于2018/11/22
 * By:Rainfall Fun
 */
public class DBHelper {
    Connection conn = null;
    PreparedStatement pstmt = null;

    DBHelper(String url, String username, String password) throws SQLException, ClassNotFoundException {
        try {
            conn = getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接数据库发生异常");
        }

    }

    /**
     * 链接数据库
     *
     * @param url      数据库地址
     * @param username 用户名
     * @param passwd   密码
     * @return 返回链接
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection(String url, String username, String password) throws ClassNotFoundException, SQLException {
        // 加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 获得链接
        conn = DriverManager.getConnection(url, username, password);
        System.out.println("已连接数据库");
        return conn;
    }

    /**
     * 有返回值的excuteQuery方法，查询并返回结果
     *
     * @param url      数据库地址
     * @param username 用户名
     * @param passwd   密码
     * @param sql      SQL语句
     * @param params   参数
     * @return ResultSet类型的查询结果
     */
    public ResultSet excuteQuery(String url, String username, String password, String sql, Object[] params) {
        try {
            // 得到链接
            if (conn == null) {
                conn = getConnection(url, username, password);
                System.out.println("已获得数据库连接");
            }
            // 准备语句对象（拼接sql语句）
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            System.out.println(pstmt);
            // 执行查询
            ResultSet rs = pstmt.executeQuery();
            // 处理结果
            System.out.println("已执行查询");
            return rs;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("异常");
        }
        return null;
    }

    /**
     * 重写excuteQuery方法
     * 省去了构造函数连接数据库这个步骤，可以直接访问构造函数连接后的数据库
     *
     * @param sql    sql语句
     * @param params sql语句的参数
     * @return ResultSet类型的查询结果
     */
    public ResultSet excuteQuery(String sql, Object[] params) {
        try {
            // 得到链接
            if (conn == null) {
                System.out.println("未建立数据库连接");
            }
            // 准备语句对象（拼接sql语句）
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            System.out.println(pstmt);
            // 执行查询
            ResultSet rs = pstmt.executeQuery();
            // 处理结果
            System.out.println("已执行查询");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("异常");
        }
        return null;
    }


    /**
     * 无返回值的excute方法
     *
     * @param url      数据库地址
     * @param username 用户名
     * @param passwd   密码
     * @param sql      SQL语句
     * @param params   参数
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void excute(String url, String username, String password, String sql, Object[] params) throws ClassNotFoundException, SQLException {
        try {
            if (conn == null) {
                conn = getConnection(url, username, password);
                System.out.println("已获得数据库连接");
            }
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            System.out.println(pstmt);
            pstmt.execute();
            System.out.println("已执行查询");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("异常");
        }

    }
    /**
     * 重写excute方法
     * 省去了构造函数连接数据库这个步骤，可以直接访问构造函数连接后的数据库
     *
     * @param sql    sql语句
     * @param params sql语句的参数
     */
    public void excute(String sql, Object[] params) throws SQLException {
        if (conn == null) {
            System.out.println("未建立数据库连接");
        }
        try {

            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            System.out.println(pstmt);
            pstmt.execute();
            System.out.println("已执行查询");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("异常");
        }

    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
