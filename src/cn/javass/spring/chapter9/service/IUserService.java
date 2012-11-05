package cn.javass.spring.chapter9.service;

import cn.javass.spring.chapter9.model.UserModel;

public interface IUserService {
    
    public void save(UserModel user);
    
    public int countAll();
}
