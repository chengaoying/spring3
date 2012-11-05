package cn.javass.spring.chapter9.dao;

import cn.javass.spring.chapter9.model.UserModel;

public interface IUserDao {
    
    public void save(UserModel user);
    
    public int countAll();
    
}
