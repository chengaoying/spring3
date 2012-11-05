package cn.javass.spring.chapter12;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TestBean32 {
    
    private DataSource DataSource;
    
    @Autowired
    @Qualifier(value = "oracleDataSource") //指定Bean标识符
    //@Qualifier(value = "mysqlDataSourceBean") //是错误的注入，不会发生回退容错，因为你指定了<qualifier>
    public void setDataSource(DataSource DataSource) {
        this.DataSource = DataSource;
    }
    
    public DataSource getDataSource() {
        return DataSource;
    }
}
