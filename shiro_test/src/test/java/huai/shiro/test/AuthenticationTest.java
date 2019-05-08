package huai.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ：shizhonghuai
 * @date ：2019/5/8 20:08
 * @description： TODO
 * @modified By：
 * @version:
 */

public class AuthenticationTest {

    /**
     * create by: shizhonghuai
     * description: shiro初体验
     * create time: 2019/5/8 20:09
     *
     * @return
     */

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void AccountUser() {
        simpleAccountRealm.addAccount("huai", "12345","admin","user");
    }

    @Test
    public void testShrio() {

        //构建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //主体提交认证请求
        Subject subject = SecurityUtils.getSubject();

        //用户登录
        UsernamePasswordToken token = new UsernamePasswordToken("huai", "12345");
        subject.login(token);
        System.out.println("isAuthentication: "+ subject.isAuthenticated());

        //用户登出
        /*subject.logout();
        System.out.println("isAuthentication: "+ subject.isAuthenticated());*/

        //用户认证
        // checkRole(s) 方法没有返回值，若当前登录人员无该角色，则抛出异常提示
        subject.checkRole("admin");

        subject.checkRoles("admin","user");


    }

}


