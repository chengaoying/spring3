package cn.javass.spring.chapter8;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import cn.javass.spring.chapter7.UserModel;
import cn.javass.spring.chapter7.dao.IUserDao;
import cn.javass.spring.chapter8.dao.IUserDao2;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.client.SqlMapSession;


public class IbatisTest {
    
    private static SqlMapClient sqlMapClient;
    
    @BeforeClass
    public static void setUpClass() {
        String[] configLocations = new String[] {
                "classpath:chapter7/applicationContext-resources.xml",
                "classpath:chapter8/applicationContext-ibatis.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
        sqlMapClient = ctx.getBean(SqlMapClient.class);
    }

    @Before
    public void setUp() throws SQLException {
        sqlMapClient.update("UserSQL.createTable");
    }
    @After
    public void after() throws SQLException {
        sqlMapClient.update("UserSQL.dropTable");
    }
    

    @Test
    public void testFirst() throws SQLException {
        UserModel model = new UserModel();
        model.setMyName("test");
        SqlMapSession session = null;
        
        try {
            session = sqlMapClient.openSession();
            beginTransaction(session);
            session.insert("UserSQL.insert", model);
            commitTransaction(session);
        } catch (SQLException e) {
            rollbackTransacrion(session);
            throw e;
        }
        finally {
          closeSession(session);
        }
    }

    private void closeSession(SqlMapSession session) {
        session.close();
    }

    private void rollbackTransacrion(SqlMapSession session) throws SQLException {
        if(session != null) {
            session.endTransaction();
        }         
    }

    private void commitTransaction(SqlMapSession session) throws SQLException {
        session.commitTransaction();
    }

    private void beginTransaction(SqlMapSession session) throws SQLException {
        session.startTransaction();
    }
    
    @Test
    public void testSqlMapClientTemplate() {
        SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
        final UserModel model = new UserModel();
        model.setMyName("myName");
        sqlMapClientTemplate.insert("UserSQL.insert", model);
        //hsqldb自增时，第一个是0
        Assert.assertEquals(0, model.getId());
        //通过回调允许更复杂操作
        sqlMapClientTemplate.execute(new SqlMapClientCallback<Void>() {
            @Override
            public Void doInSqlMapClient(SqlMapExecutor session) throws SQLException {
                session.insert("UserSQL.insert", model);
                return null;
            }
        });
        //hsqldb自增时，第二个自然就是1
        Assert.assertEquals(1, model.getId());
    }
    

    @Test
    public void testBestPractice() {
        String[] configLocations = new String[] {
                "classpath:chapter7/applicationContext-resources.xml",
                "classpath:chapter8/applicationContext-ibatis.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
        IUserDao userDao = ctx.getBean(IUserDao.class);
        UserModel model = new UserModel();
        model.setMyName("test");
        userDao.save(model);
        Assert.assertEquals(1, userDao.countAll());
    }
    
    @Test
    public void testMybatisBestPractice() {
        String[] configLocations = new String[] {
                "classpath:chapter7/applicationContext-resources.xml",
                "classpath:chapter8/applicationContext-mybatis.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
        IUserDao userDao = ctx.getBean(IUserDao.class);
        UserModel model = new UserModel();
        model.setMyName("test");
        userDao.save(model);
        Assert.assertEquals(1, userDao.countAll());
    }

    @Test
    public void testMybatis2() {
        String[] configLocations = new String[] {
                "classpath:chapter7/applicationContext-resources.xml",
                "classpath:chapter8/applicationContext-mybatis.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
        IUserDao2 userDao = ctx.getBean(IUserDao2.class);
        UserModel model = new UserModel();
        model.setMyName("test");
        userDao.save(model);
        Assert.assertEquals(1, userDao.countAll());
        
    }
    
}
