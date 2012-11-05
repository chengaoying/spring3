package cn.javass.spring.chapter9.service;

import cn.javass.spring.chapter9.model.AddressModel;

public interface IAddressService {
    
    public void save(AddressModel address);
    
    public int countAll();
}
