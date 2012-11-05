package cn.javass.spring.chapter12;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TestBean31 {
    
    private DataSource DataSource;

    @Autowired
    //根据<qualifier>标签指定Bean标识符
    public void initDataSource(@Qualifier("mysqlDataSource") DataSource DataSource) {
        this.DataSource = DataSource;
    }
    
    public DataSource getDataSource() {
        return DataSource;
    }
    
}
