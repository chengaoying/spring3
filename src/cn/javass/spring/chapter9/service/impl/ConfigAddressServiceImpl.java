package cn.javass.spring.chapter9.service.impl;

import cn.javass.spring.chapter9.dao.IAddressDao;
import cn.javass.spring.chapter9.model.AddressModel;
import cn.javass.spring.chapter9.service.IAddressService;

public class ConfigAddressServiceImpl implements IAddressService {
    
    private IAddressDao addressDao;
    

    public void setAddressDao(IAddressDao addressDao) {
        this.addressDao = addressDao;
    }
    
    @Override
    public void save(final AddressModel address) {
        addressDao.save(address);
    }

    @Override
    public int countAll() {
        return addressDao.countAll();
    }


}
