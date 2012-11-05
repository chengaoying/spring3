package cn.javass.spring.chapter9.service.impl.supports;

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

public class SupportsAndSupportsUserServiceImplWithRuntimeException implements IUserService {
    
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
                    TransactionDefinition.PROPAGATION_SUPPORTS, 
                    TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                userDao.save(user);//无事务环境允许，将发生立即提交
                user.getAddress().setUserId(user.getId());
                addressService.save(user.getAddress());//无事务环境运行，即使抛出异常，对前边执行的无影响
                
            }
        });
    }

    @Override
    public int countAll() {
        return userDao.countAll();
    }

}
