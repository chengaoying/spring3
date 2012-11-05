package cn.javass.spring.chapter7;

import java.sql.Types;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlQuery;

public class UserModelSqlQuery extends SqlQuery<UserModel> {
    
    public UserModelSqlQuery(JdbcTemplate jdbcTemplate) {
        //1.设置数据源或JdbcTemplate
        //super.setDataSource(jdbcTemplate.getDataSource());
        super.setJdbcTemplate(jdbcTemplate);
        //2.注入sql语句
        super.setSql("select * from test where name=?");
        //3.对PreparedStatement参数描述，如命名参数、占位符参数，用于描述参数类型
        super.declareParameter(new SqlParameter(Types.VARCHAR));
        //可选的编译步骤，当执行查询方法时自动编译，对于编译的SqlQuery不能再对参数进行修改
        compile();
    }
    @Override
    protected RowMapper<UserModel> newRowMapper(Object[] parameters, Map context) {
        return new UserRowMapper();
    }
}
