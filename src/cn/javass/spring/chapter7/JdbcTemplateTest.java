package cn.javass.spring.chapter7;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import cn.javass.spring.chapter7.dao.IUserDao;

public class JdbcTemplateTest {
    
    private static JdbcTemplate jdbcTemplate;
    
    @BeforeClass
    public static void setUpClass() {
        String url = "jdbc:hsqldb:mem:test";
        String username = "sa";
        String password = "";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @AfterClass
    public static void tearDownClass() {
        jdbcTemplate = null;
    }
    
    @Test 
    public void test() {
        //1.声明SQL
        String sql = "select * from INFORMATION_SCHEMA.SYSTEM_TABLES";
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                //2.处理结果集
                String value = rs.getString("TABLE_NAME");
                System.out.println("Column TABLENAME:" + value);
            }
            
        });
    }
    
    @Before
    public void setUp() {
        //id自增主键从0开始
        String createTableSql = "create memory table test" +
        "(id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
        "name varchar(100))";
        jdbcTemplate.update(createTableSql);
        
        String createHsqldbFunctionSql = 
            "CREATE FUNCTION FUNCTION_TEST(str VARCHAR(100)) " +
            "returns INT begin atomic return length(str);end";
        jdbcTemplate.update(createHsqldbFunctionSql);
        String createHsqldbProcedureSql = 
            "CREATE PROCEDURE PROCEDURE_TEST" +
            "(INOUT inOutName VARCHAR(100), OUT outId INT) " +
            "MODIFIES SQL DATA " + 
            "BEGIN ATOMIC " +
            "  insert into test(name) values (inOutName); " +
            "  SET outId = IDENTITY(); " +
            "  SET inOutName = 'Hello,' + inOutName; " + 
            "END";
        jdbcTemplate.execute(createHsqldbProcedureSql);
    }
    @After
    public void tearDown() {
        jdbcTemplate.execute("DROP FUNCTION FUNCTION_TEST");
        jdbcTemplate.execute("DROP PROCEDURE PROCEDURE_TEST");
        String dropTableSql = "drop table test";
        jdbcTemplate.execute(dropTableSql);
    }
    
    @Test
    public void testCURD() {
        insert();
        delete();
        update();
        select();
    }
    
    private void insert() {
        jdbcTemplate.update("insert into test(name) values('name1')");
        jdbcTemplate.update("insert into test(name) values('name2')");
        Assert.assertEquals(2, jdbcTemplate.queryForInt("select count(*) from test"));
    }

    private void delete() {
        jdbcTemplate.update("delete from test where name=?", new Object[]{"name2"});
        Assert.assertEquals(1, jdbcTemplate.queryForInt("select count(*) from test"));
    }
    
    private void update() {
        jdbcTemplate.update("update test set name='name3' where name=?", new Object[]{"name1"});
        Assert.assertEquals(1, jdbcTemplate.queryForInt("select count(*) from test where name='name3'"));
        
    }
    
    private void select() {
        jdbcTemplate.query("select * from test", new RowCallbackHandler(){
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                System.out.print("====id:" + rs.getInt("id"));
                System.out.println(",name:" + rs.getString("name"));
            }
        });
    }
    
    
    @Test
    public void testPreparedStatement1() {
        int count = jdbcTemplate.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                return conn.prepareStatement("select count(*) from test");
            }
        }, new PreparedStatementCallback<Integer>() {
            @Override
            public Integer doInPreparedStatement(PreparedStatement pstmt)
                throws SQLException, DataAccessException {
                pstmt.execute();
                ResultSet rs = pstmt.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
        });
        
        Assert.assertEquals(0, count);
    }

    @Test
    public void testPreparedStatement2() {
        String insertSql = "insert into test(name) values (?)";
        int count = jdbcTemplate.update(insertSql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, "name4");
            }
        });
        Assert.assertEquals(1, count);
        
        String deleteSql = "delete from test where name=?";
        count = jdbcTemplate.update(deleteSql, new Object[] {"name4"});
        Assert.assertEquals(1, count);
    }

    @Test
    public void testResultSet1() {
        jdbcTemplate.update("insert into test(name) values('name5')");
        String listSql = "select * from test";
        List result = jdbcTemplate.query(listSql, new RowMapper<Map>() {
            @Override
            public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map row = new HashMap();
                row.put(rs.getInt("id"), rs.getString("name"));
                return row;
            }
        });
        Assert.assertEquals(1, result.size());
        jdbcTemplate.update("delete from test where name='name5'");
        
    }
   

    @Test
    public void testResultSet2() {
        jdbcTemplate.update("insert into test(name) values('name5')");
        String listSql = "select * from test";
        final List result = new ArrayList();
        jdbcTemplate.query(listSql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Map row = new HashMap();
                row.put(rs.getInt("id"), rs.getString("name"));
                result.add(row);
            }
            
        });
        Assert.assertEquals(1, result.size());
        jdbcTemplate.update("delete from test where name='name5'");
    }

    @Test
    public void testResultSet3() {
        jdbcTemplate.update("insert into test(name) values('name5')");
        String listSql = "select * from test";
        List result = jdbcTemplate.query(listSql, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet rs) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while(rs.next()) {
                    Map row = new HashMap();
                    row.put(rs.getInt("id"), rs.getString("name"));
                    result.add(row);
                }
                return result;
            }
        });
        Assert.assertEquals(1, result.size());
        jdbcTemplate.update("delete from test where name='name5'");
    }

    @Test
    public void testResultSet4() {
        
        jdbcTemplate.execute("alter table test add column last_name varchar(100)");
        jdbcTemplate.update("insert into test(name) values('name5')");
        
        int count = jdbcTemplate.queryForInt("select count(*) from test");
        Assert.assertEquals(1, count);
        Map result1 = jdbcTemplate.queryForMap("select * from test where name='name5'");
        Assert.assertEquals("name5", result1.get("name"));
        count = jdbcTemplate.queryForObject("select count(*) from test", Integer.class);
        Assert.assertEquals(1, count);
        List<Map<String, Object>> result2 = jdbcTemplate.queryForList("select * from test");
        Assert.assertEquals(1, result2.size());
        List<String> result3 = jdbcTemplate.queryForList("select name from test where name=?", new Object[]{"name5"}, String.class);
        Assert.assertEquals(1, result3.size());
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from test");
        List result4 = new ArrayList();
        while(rs.next()) {
            result4.add(rs.getInt("id"));
        }
        Assert.assertEquals(1, result4.size());
        
        jdbcTemplate.update("delete from test where name='name5'");
    }
    
    
    @Test
    public void testCallableStatementCreator1() {
        final String callFunctionSql = "{call FUNCTION_TEST(?)}";
        List<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter(Types.VARCHAR));
        params.add(new SqlReturnResultSet("result", new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException,
                DataAccessException {
                
                while(rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }));
        
        Map<String, Object> outValues = jdbcTemplate.call(new CallableStatementCreator() {
            
            @Override
            public CallableStatement createCallableStatement(Connection conn) throws SQLException {
                CallableStatement cstmt = conn.prepareCall(callFunctionSql);
                cstmt.setString(1, "test");
                return cstmt;
            }
        }, params);
        Assert.assertEquals(4, outValues.get("result"));
    }

    @Test
    public void testCallableStatementCreator2() {
        //1.首先登录mysql控制台创建test数据库
        String url = "jdbc:mysql://localhost:3306/test";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, "root", "");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        JdbcTemplate mysqlJdbcTemplate = new JdbcTemplate(dataSource);
        
        //2.创建自定义函数
        String createFunctionSql = 
            "CREATE FUNCTION FUNCTION_TEST(str VARCHAR(100)) " +
            "returns INT return LENGTH(str)";
        String dropFunctionSql = "DROP FUNCTION IF EXISTS FUNCTION_TEST";
        mysqlJdbcTemplate.update(dropFunctionSql);
        mysqlJdbcTemplate.update(createFunctionSql);
        //3.准备sql
        final String callFunctionSql = "{?= call FUNCTION_TEST(?)}";
        //4.定义参数
        List<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlOutParameter("result", Types.INTEGER));
        params.add(new SqlParameter("str", Types.VARCHAR));
        
        Map<String, Object> outValues = mysqlJdbcTemplate.call(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection conn)
                throws SQLException {
                CallableStatement cstmt = conn.prepareCall(callFunctionSql);
                cstmt.registerOutParameter(1, Types.INTEGER);
                cstmt.setString(2, "test");
                return cstmt;
            }
        }, params);
        
        Assert.assertEquals(4, outValues.get("result"));
    }
    
    @Test
    public void testCallableStatementCreator3() {
        final String callProcedureSql = "{call PROCEDURE_TEST(?, ?)}";
        List<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlInOutParameter("inOutName", Types.VARCHAR));
        params.add(new SqlOutParameter("outId", Types.INTEGER));
        
        Map<String, Object> outValues = jdbcTemplate.call(new CallableStatementCreator() {
            
            @Override
            public CallableStatement createCallableStatement(Connection conn) throws SQLException {
                CallableStatement cstmt = conn.prepareCall(callProcedureSql);
                cstmt.registerOutParameter(1, Types.VARCHAR);
                cstmt.registerOutParameter(2, Types.INTEGER);
                cstmt.setString(1, "test");
                return cstmt;
            }
        }, params);
        Assert.assertEquals("Hello,test", outValues.get("inOutName"));
        Assert.assertEquals(0, outValues.get("outId"));
    }
    
    
    
    @Test
    public void testNamedParameterJdbcTemplate1() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
        //namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        String insertSql = "insert into test(name) values(:name)";
        String selectSql = "select * from test where name=:name";
        String deleteSql = "delete from test where name=:name";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "name5");
        namedParameterJdbcTemplate.update(insertSql, paramMap);
        
        final List<Integer> result = new ArrayList<Integer>();
        namedParameterJdbcTemplate.query(selectSql, paramMap, new RowCallbackHandler() {
            
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                result.add(rs.getInt("id"));
            }
        });
        
        Assert.assertEquals(1, result.size());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        namedParameterJdbcTemplate.update(deleteSql, paramSource);
        
        
        class Model {
            private int id;
            private String myName;
           
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }
            public void setMyName(String myName) {
                this.myName = myName;
            }
            public String getMyName() {
                return myName;
            }
        }
        Model model = new Model();
        model.setId(1);
        model.setMyName("name5");
        insertSql = "insert into test(name) values(:myName)";
        paramSource = new BeanPropertySqlParameterSource(model);
        namedParameterJdbcTemplate.update(insertSql, paramSource);
    }

    
    @Test
    public void testNamedParameterJdbcTemplate2() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
       
        UserModel model = new UserModel();
        model.setMyName("name5");
        String insertSql = "insert into test(name) values(:myName)";
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(model);
        namedParameterJdbcTemplate.update(insertSql, paramSource);
    }
    
    @Test
    public void testSimpleJdbcTemplate() {
        //还支持DataSource和NamedParameterJdbcTemplate作为构造器参数
        SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(jdbcTemplate);
        String insertSql = "insert into test(id, name) values(?, ?)";
        simpleJdbcTemplate.update(insertSql, 10, "name5");
        String selectSql = "select * from test where id=? and name=?";
        List<Map<String, Object>> result = simpleJdbcTemplate.queryForList(selectSql, 10, "name5");
        Assert.assertEquals(1, result.size());
        RowMapper<UserModel> mapper = new UserRowMapper();        
        List<UserModel> result2 = simpleJdbcTemplate.query(selectSql, mapper, 10, "name5");
        Assert.assertEquals(1, result2.size());
        
    }
    
    @Test
    public void testSqlQuery() {
        SqlQuery query = new UserModelSqlQuery(jdbcTemplate);
        List<UserModel> result = query.execute("name5");
        Assert.assertEquals(0, result.size());
    }
    

    @Test
    public void testMappingSqlQuery() {
        jdbcTemplate.update("insert into test(name) values('name5')");
        SqlQuery<UserModel> query = new UserModelMappingSqlQuery(jdbcTemplate);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "name5");
        UserModel result = query.findObjectByNamedParam(paramMap);
        Assert.assertNotNull(result);
    }

    
    @Test
    public void testGenericSqlQuery() throws IllegalAccessException, InstantiationException {
        jdbcTemplate.update("insert into test(name) values('name5')");
        SqlQuery<UserModel> query = new UserModelGenericSqlQuery(jdbcTemplate);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "name5");
        UserModel result = query.findObjectByNamedParam(paramMap);
        Assert.assertNotNull(result);
    }

    
    @Test
    public void testSqlFunction() {
        jdbcTemplate.update("insert into test(name) values('name5')");
        String countSql = "select count(*) from test";
        SqlFunction<Integer> sqlFunction1 = new SqlFunction<Integer>(jdbcTemplate.getDataSource(), countSql);
        Assert.assertEquals(1, sqlFunction1.run());

        String selectSql = "select name from test where name=?";
        SqlFunction<String> sqlFunction2 = new SqlFunction<String>(jdbcTemplate.getDataSource(), selectSql);
        sqlFunction2.declareParameter(new SqlParameter(Types.VARCHAR));
        String name = (String) sqlFunction2.runGeneric(new Object[] {"name5"});
        Assert.assertEquals("name5", name);
    }
    
    
    @Test
    public void testSqlUpdate() {
        
        SqlUpdate update1 = new InsertUserModel(jdbcTemplate);
        update1.update("name5");
        
        String updateSql = "update test set name=? where name=?";
        SqlUpdate update = new SqlUpdate(jdbcTemplate.getDataSource(), updateSql, new int[]{Types.VARCHAR, Types.VARCHAR});
        update.update("name6", "name5");

        String deleteSql = "delete from test where name=:name";
        SqlUpdate delete = new SqlUpdate(jdbcTemplate.getDataSource(), deleteSql, new int[]{Types.VARCHAR});
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "name5");
        delete.updateByNamedParam(paramMap);
    }
    
    
    @Test
    public void testStoredProcedure1() {
        StoredProcedure lengthFunction = new HsqldbLengthFunction(jdbcTemplate);
        Map<String,Object> outValues = lengthFunction.execute("test");
        Assert.assertEquals(4, outValues.get("result"));
    }    
    
    @Test
    public void testStoredProcedure2() {
        JdbcTemplate mysqlJdbcTemplate = new JdbcTemplate(getMysqlDataSource());
      
        String createFunctionSql = 
            "CREATE FUNCTION FUNCTION_TEST(str VARCHAR(100)) " +
            "returns INT return LENGTH(str)";
        String dropFunctionSql = "DROP FUNCTION IF EXISTS FUNCTION_TEST";
        mysqlJdbcTemplate.update(dropFunctionSql);
        mysqlJdbcTemplate.update(createFunctionSql);
        StoredProcedure lengthFunction = new MysqlLengthFunction(mysqlJdbcTemplate);
        Map<String,Object> outValues = lengthFunction.execute("test");
        Assert.assertEquals(4, outValues.get("result"));
    }
    
    @Test
    public void testStoredProcedure3() {
        StoredProcedure procedure = new HsqldbTestProcedure(jdbcTemplate);
        Map<String,Object> outValues = procedure.execute("test");
        //id 默认从0开始
        Assert.assertEquals(0, outValues.get("outId"));
        Assert.assertEquals("Hello,test", outValues.get("inOutName"));
        
    }
    
    
    @Test
    public void testSimpleJdbcInsert() {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("test");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("name", "name5");
        insert.compile();
        //1.普通插入
        insert.execute(args);
        Assert.assertEquals(1, jdbcTemplate.queryForInt("select count(*) from test"));
        //2.插入时获取主键值
        insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("test");
        insert.setGeneratedKeyName("id");
        Number id = insert.executeAndReturnKey(args);
        Assert.assertEquals(1, id);
        //3.批处理
        insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("test");
        insert.setGeneratedKeyName("id");
        int[] updateCount = insert.executeBatch(new Map[] {args, args, args});
        Assert.assertEquals(1, updateCount[0]);
        Assert.assertEquals(5, jdbcTemplate.queryForInt("select count(*) from test"));
    }
    
    
    @Test
    public void testSimpleJdbcCall1() {
        //此处用mysql,因为hsqldb调用自定义函数和存储过程一样
        SimpleJdbcCall call = new SimpleJdbcCall(getMysqlDataSource());
        call.withFunctionName("FUNCTION_TEST");
        call.declareParameters(new SqlOutParameter("result", Types.INTEGER));
        call.declareParameters(new SqlParameter("str", Types.VARCHAR));
        Map<String, Object> outVlaues = call.execute("test");
        Assert.assertEquals(4, outVlaues.get("result"));
        outVlaues = call.execute("test1");
        Assert.assertEquals(5, outVlaues.get("result"));
    }
    
    @Test
    public void testSimpleJdbcCall2() {
        //调用hsqldb自定义函数得使用如下方式
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
        call.withProcedureName("FUNCTION_TEST");
        call.declareParameters(new SqlReturnResultSet("result", new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                while(rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }));
        call.declareParameters(new SqlParameter("str", Types.VARCHAR));
        Map<String, Object> outVlaues = call.execute("test");
        Assert.assertEquals(4, outVlaues.get("result"));
    }

    @Test
    public void testSimpleJdbcCall3() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
        call.withProcedureName("PROCEDURE_TEST");
        call.declareParameters(new SqlInOutParameter("inOutName", Types.VARCHAR));
        call.declareParameters(new SqlOutParameter("outId", Types.INTEGER));
        SqlParameterSource params = new MapSqlParameterSource().addValue("inOutName", "test");
        Map<String, Object> outVlaues = call.execute(params);
        Assert.assertEquals("Hello,test", outVlaues.get("inOutName"));
        Assert.assertEquals(0, outVlaues.get("outId"));
    }
    
    
    @Test
    public void testFetchKey1() throws SQLException {
        final String insertSql = "insert into test(name) values('name5')";
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                return conn.prepareStatement(insertSql, new String[]{"ID"});
            }
        }, generatedKeyHolder);
        Assert.assertEquals(0, generatedKeyHolder.getKey());
    }
    
    @Test
    public void testFetchKey2() {
        final String insertSql = "insert into test(name) values('name5')";
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlUpdate update = new SqlUpdate();
        update.setJdbcTemplate(jdbcTemplate);
        update.setReturnGeneratedKeys(true);
        //update.setGeneratedKeysColumnNames(new String[]{"ID"});
        update.setSql(insertSql);
        update.update(null, generatedKeyHolder);
        Assert.assertEquals(0, generatedKeyHolder.getKey());
    }
    
    
    @Test
    public void testBatchUpdate1() {
        String insertSql = "insert into test(name) values('name5')";
        String[] batchSql = new String[] {insertSql, insertSql};
        jdbcTemplate.batchUpdate(batchSql);
        Assert.assertEquals(2, jdbcTemplate.queryForInt("select count(*) from test"));
    }
    
    @Test
    public void testBatchUpdate2() {
        String insertSql = "insert into test(name) values(?)";
        final String[] batchValues = new String[] {"name5", "name6"};
        jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, batchValues[i]);
            }
            @Override
            public int getBatchSize() {
                return batchValues.length;
            }
        });
        Assert.assertEquals(2, jdbcTemplate.queryForInt("select count(*) from test"));
    }
    
    @Test
    public void testBatchUpdate3() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        String insertSql = "insert into test(name) values(:myName)";
        UserModel model = new UserModel();
        model.setMyName("name5");
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(new Object[] {model, model});
        namedParameterJdbcTemplate.batchUpdate(insertSql, params);
        Assert.assertEquals(2, jdbcTemplate.queryForInt("select count(*) from test"));
    }
    
    @Test
    public void testBatchUpdate4() {
        SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(jdbcTemplate);
        String insertSql = "insert into test(name) values(?)";
        List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[]{"name5"});
        params.add(new Object[]{"name5"});
        simpleJdbcTemplate.batchUpdate(insertSql, params);
        Assert.assertEquals(2, jdbcTemplate.queryForInt("select count(*) from test"));
    }

    @Test
    public void testBatchUpdate5() {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("test");
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("name", "name5");
        insert.executeBatch(new Map[] {valueMap, valueMap});
        Assert.assertEquals(2, jdbcTemplate.queryForInt("select count(*) from test"));
    }
    
    
    @Test
    public void testBestPractice() {
        String[] configLocations = new String[] {
                "classpath:chapter7/applicationContext-resources.xml",
                "classpath:chapter7/applicationContext-jdbc.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
        IUserDao userDao = ctx.getBean(IUserDao.class);
        UserModel model = new UserModel();
        model.setMyName("test");
        userDao.save(model);
        Assert.assertEquals(1, userDao.countAll());
    }
    
    
    
    public DataSource getMysqlDataSource() {
        //1.首先启动mysql（本书使用5.4.3版本），其次登录mysql创建test数据库
        String url = "jdbc:mysql://localhost:3306/test";
        //在进行测试前，请下载并添加mysql-connector-java-5.1.10.jar到classpath
        DriverManagerDataSource dataSource = 
            new DriverManagerDataSource(url, "root", "");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
}
