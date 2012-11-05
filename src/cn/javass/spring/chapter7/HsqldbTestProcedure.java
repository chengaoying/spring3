package cn.javass.spring.chapter7;

import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class HsqldbTestProcedure extends StoredProcedure {
    
    public HsqldbTestProcedure(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
        super.setSql("PROCEDURE_TEST");
        super.declareParameter(new SqlInOutParameter("inOutName", Types.VARCHAR));
        super.declareParameter(new SqlOutParameter("outId", Types.INTEGER));
        compile();
    }
}
