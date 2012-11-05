package cn.javass.spring.chapter9.service.impl;

import cn.javass.spring.chapter9.dao.IUserDao;
import cn.javass.spring.chapter9.model.UserModel;
import cn.javass.spring.chapter9.service.IAddressService;
import cn.javass.spring.chapter9.service.IUserService;

public class ConfigUserServiceImpl implements IUserService {
    
    private IUserDao userDao;

    private IAddressService addressService;
    
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
    
    public void setAddressService(IAddressService addressService) {
        this.addressService = addressService;
    }
    
    @Override
    public void save(final UserModel user) {
        userDao.save(user);
        user.getAddress().setUserId(user.getId());
        addressService.save(user.getAddress());
        
    }

    @Override
    public int countAll() {
        return userDao.countAll();
    }

}
