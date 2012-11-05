package cn.javass.spring.chapter12;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.javass.spring.chapter12.qualifier.DataBase;
import cn.javass.spring.chapter12.qualifier.DataSourceType;

public class TestBean34 {
    
    private DataSource mysqlDataSource;
    private DataSource oracleDataSource;
    
    @Autowired
    public void initDataSource(
            @DataSourceType(ip="localhost", database=DataBase.MYSQL) 
            DataSource mysqlDataSource,
            
            @DataSourceType(ip="localhost", database=DataBase.ORACLE) 
            DataSource oracleDataSource) {
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
