package cn.javass.spring.chapter6.service.impl;

import cn.javass.spring.chapter6.service.IIntroductionService;

public class IntroductiondService implements IIntroductionService {
    
    @Override
    public void induct() {
        System.out.println("=========introduction");
    }
}
