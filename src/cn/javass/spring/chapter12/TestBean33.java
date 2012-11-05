package cn.javass.spring.chapter12;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.javass.spring.chapter12.qualifier.Mysql;
import cn.javass.spring.chapter12.qualifier.Oracle;

public class TestBean33 {
    
    private DataSource mysqlDataSource;
    private DataSource oracleDataSource;
    
    @Autowired
    public void initDataSource(@Mysql DataSource mysqlDataSource, @Oracle DataSource oracleDataSource) {
        this.mysqlDataSource = mysqlDataSource;
        this.oracleDataSource = oracleDataSource;
    }
    
    public DataSource getMysqlDataSource() {
        return mysqlDataSource;
    }
    
    public DataSource getOracleDataSource() {
        return oracleDataSource;
    }
    
}
