package cn.javass.spring.chapter7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class TraditionalJdbcTest {
    
    @Test
    public void test() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();//1.获取JDBC连接
            //2.声明SQL
            String sql = "select * from INFORMATION_SCHEMA.SYSTEM_TABLES";
            pstmt = conn.prepareStatement(sql);//2.预编译SQL
            ResultSet rs = pstmt.executeQuery();//3.执行SQL
            process(rs);//4.处理结果集
            closeResultSet(rs);//5.释放结果集
            closeStatement(pstmt);//6.释放Statement
            conn.commit();//7.提交事务
        } catch (Exception e) {
            //8.处理异常并回滚事务
            conn.rollback();
            throw e;
        } finally {
            //9.释放JDBC连接，防止JDBC连接不关闭造成的内存泄漏
            closeConnection(conn);
        }
        
    }
    

    private Connection getConnection() throws SQLException {
        //1.注册JDBC驱动器
        DriverManager.registerDriver(new org.hsqldb.jdbcDriver());
        //2.获取数据库连接
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:test", "sa", "");
        //3.关闭自动提交，打开事务
        conn.setAutoCommit(false);
        //4.设置事务隔离级别
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return conn;
    }
    
    private void process(ResultSet rs) throws SQLException {
        while(rs.next()) {
            String value = rs.getString("TABLE_NAME");
            System.out.println("Column TABLENAME:" + value);
        }        
    }


    private void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                //处理异常
            }
        }
    }

    private void closeStatement(Statement stmt) {
        if(stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                //处理异常
            }
        }
    }

    private void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                //处理异常
            }
        }
    }
}
