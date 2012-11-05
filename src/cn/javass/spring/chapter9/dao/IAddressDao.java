package cn.javass.spring.chapter9.dao;

import cn.javass.spring.chapter9.model.AddressModel;


public interface IAddressDao {
    
    public void save(AddressModel address);

    public int countAll();
}
