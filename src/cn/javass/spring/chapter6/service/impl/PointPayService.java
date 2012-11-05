package cn.javass.spring.chapter6.service.impl;

import cn.javass.spring.chapter6.service.IPayService;

public class PointPayService implements IPayService {
    
    @Override
    public boolean pay(long userId, long money) {
        //支付业务实现
        return true;
    }

    @Override
    public boolean isEnough(long userId, long money) {
        //验证用户货币是否充足业务实现
        return true;
    }

    @Override
    public long query(long userId) {
        //查询用户货币业务实现
        return 1000;
    }
}
