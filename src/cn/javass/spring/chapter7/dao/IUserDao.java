package cn.javass.spring.chapter7.dao;

import cn.javass.spring.chapter7.UserModel;


public interface IUserDao {
    
    public void save(UserModel model);

    public int countAll();
}
