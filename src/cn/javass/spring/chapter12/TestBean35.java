package cn.javass.spring.chapter12;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.javass.spring.chapter12.qualifier.CustomQualifier;

public class TestBean35 {
    
    private DataSource DataSource;
    
    @Autowired
    public TestBean35(@CustomQualifier("oracleDataSource") DataSource dataSource) {
        this.DataSource = dataSource;
    }
    
    public DataSource getDataSource() {
        return DataSource;
    }
}
