    package cn.javass.spring.chapter8.dao.jpa;
    
    import org.springframework.orm.jpa.support.JpaDaoSupport;
    import org.springframework.transaction.annotation.Propagation;
    import org.springframework.transaction.annotation.Transactional;
    
    import cn.javass.spring.chapter7.UserModel;
    import cn.javass.spring.chapter7.dao.IUserDao;
    @Transactional(propagation = Propagation.REQUIRED)
    public class UserJpaDaoImpl extends JpaDaoSupport implements IUserDao {
    
        private static final String COUNT_ALL_JPAQL = "select count(*) from UserModel";
        
        @Override
        public void save(UserModel model) {
            getJpaTemplate().persist(model);
        }
    
        @Override
        public int countAll() {
            Number count =  (Number) getJpaTemplate().find(COUNT_ALL_JPAQL).get(0);
            return count.intValue();
        }
        
    }
