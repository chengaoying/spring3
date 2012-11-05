package cn.javass.spring.chapter8.dao.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cn.javass.spring.chapter7.UserModel;
import cn.javass.spring.chapter7.dao.IUserDao;

public class UserIbatisDaoImpl extends SqlMapClientDaoSupport implements IUserDao {

    @Override
    public void save(UserModel model) {
        getSqlMapClientTemplate().insert("UserSQL.insert", model);
    }

    @Override
    public int countAll() {
        return (Integer) getSqlMapClientTemplate().queryForObject("UserSQL.countAll");
    }

}
