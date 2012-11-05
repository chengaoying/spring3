package cn.javass.spring.chapter9.service.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.javass.spring.chapter9.dao.IUserDao;
import cn.javass.spring.chapter9.model.UserModel;
import cn.javass.spring.chapter9.service.IAddressService;
import cn.javass.spring.chapter9.service.IUserService;

public class AnnotationUserServiceImpl implements IUserService {
    
    private IUserDao userDao;

    private IAddressService addressService;
    
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
    
    public void setAddressService(IAddressService addressService) {
        this.addressService = addressService;
    }
    
    @Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    @Override
    public void save(final UserModel user) {
        userDao.save(user);
        user.getAddress().setUserId(user.getId());
        addressService.save(user.getAddress());
        
    }

    @Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, readOnly=true)
    @Override
    public int countAll() {
        return userDao.countAll();
    }

}
