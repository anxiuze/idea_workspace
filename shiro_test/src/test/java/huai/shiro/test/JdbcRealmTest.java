package huai.shiro.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * @author ：shizhonghuai
 * @date ：2019/5/8 21:47
 * @description： TODO
 * @modified By：
 * @version:
 */
public class JdbcRealmTest {

    //数据源
    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro_test");
        dataSource.setUsername("root");
        dataSource.setPassword("");
    }

    @Test
    public void JdbcRealmTest() {

        //设置数据源
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //认证用户权限，  默认值为false
        jdbcRealm.setPermissionsLookupEnabled(true);

        String userSql = "select password from test_users where username = ?";
        jdbcRealm.setAuthenticationQuery(userSql);

        String userRoleSql = "select test_roles from test_users_roles where test_username = ?";
        jdbcRealm.setUserRolesQuery(userRoleSql);

        String permissionSql = "select test_permissions from test_permissions where test_roles = ?";
        jdbcRealm.setPermissionsQuery(permissionSql);

        //构建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //主体提交认证
        Subject subject = SecurityUtils.getSubject();
        //设置Token
//        UsernamePasswordToken token = new UsernamePasswordToken("huai","123456");
        UsernamePasswordToken token = new UsernamePasswordToken("hong", "654321");
        subject.login(token);

        System.out.println("isAuthentication: " + subject.isAuthenticated());

        subject.checkRole("user");

        subject.checkPermission("read");

        /*subject.checkRole("admin");

        subject.checkRoles("admin","user");

        subject.checkPermission("delete");*/


    }

}
