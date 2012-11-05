package cn.javass.spring.chapter7;

import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericSqlQuery;

public class UserModelGenericSqlQuery extends GenericSqlQuery {
    
    public UserModelGenericSqlQuery(JdbcTemplate jdbcTemplate) throws IllegalAccessException, InstantiationException {
      //1.设置数据源或JdbcTemplate
        super.setDataSource(jdbcTemplate.getDataSource());
        //2.注入sql语句
        super.setSql("select * from test where name=:name");
        //3.对PreparedStatement参数描述，如命名参数、占位符参数，用于描述参数类型
        super.declareParameter(new SqlParameter("name", Types.VARCHAR));
        //可选的编译步骤，当执行查询方法时自动编译，对于编译的SqlQuery不能再对参数进行修改
        super.setRowMapperClass(UserRowMapper.class);
        compile();
    }
}
