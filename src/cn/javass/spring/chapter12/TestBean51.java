package cn.javass.spring.chapter12;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import cn.javass.spring.chapter12.qualifier.JSR330Mysql;

public class TestBean51 {
    private DataSource mysqlDataSource;
    private DataSource oracleDataSource;
    
    @Inject
    public void initDataSoruce(
            @JSR330Mysql
            DataSource mysqlDataSource,
            
            @Named("oracleDataSource")
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
