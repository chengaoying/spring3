package cn.javass.spring.chapter9.service.impl.nested;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import cn.javass.spring.chapter9.dao.IUserDao;
import cn.javass.spring.chapter9.model.UserModel;
import cn.javass.spring.chapter9.service.IAddressService;
import cn.javass.spring.chapter9.service.IUserService;
import cn.javass.spring.chapter9.util.TransactionTemplateUtils;

public class RequiredAndNestedUserServiceImplWithRuntimeException implements IUserService {
    
    private IUserDao userDao;

    private IAddressService addressService;
    
    private PlatformTransactionManager txManager;
    
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
    
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
    
    public void setAddressService(IAddressService addressService) {
        this.addressService = addressService;
    }
    
    @Override
    public void save(final UserModel user) {
        TransactionTemplate transactionTemplate = 
            TransactionTemplateUtils.getTransactionTemplate(
                    txManager, 
                    TransactionDefinition.PROPAGATION_REQUIRED, 
                    TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                userDao.save(user);
                user.getAddress().setUserId(user.getId());
                try {
                    addressService.save(user.getAddress());//将创建嵌套事务，即创建JDBC保存点
                } catch (RuntimeException e) {
                    //捕获了异常，将不会导致外部事务回滚
                }
                
            }
        });
    }

    @Override
    public int countAll() {
        return userDao.countAll();
    }

}
