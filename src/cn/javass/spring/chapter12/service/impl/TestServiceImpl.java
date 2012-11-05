package cn.javass.spring.chapter12.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.javass.spring.chapter12.dao.hibernate.TestHibernateDaoImpl;

@Service("testService")
public class TestServiceImpl {
    
    @Autowired
    @Qualifier("testHibernateDao")
    private TestHibernateDaoImpl dao;
    
    public TestHibernateDaoImpl getDao() {
        return dao;
    }
}
