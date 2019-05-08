package huai.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author ：shizhonghuai
 * @date ：2019/5/8 21:07
 * @description： 自定义Realm
 * @modified By：
 * @version:
 */
public class IniRealmTest {

    IniRealm iniRealm = new IniRealm("classpath:user.ini");

    @Test
    public void IniRealmTest(){

        //构建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //主体提交认证
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("huai","12345");
        //用户登录
        subject.login(token);

        System.out.println("isAuthentication: "+subject.isAuthenticated());

        subject.checkRole("admin");
        //认证多角色
        subject.checkRoles("admin","user");

        subject.checkPermission("user:delete");

        subject.checkPermissions("user:add","user:delete");



    }

}
