package huai.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import com.huai.shiro.realm.CustomRealm;
import org.junit.Test;

/**
 * @author ：shizhonghuai
 * @date ：2019/5/20 22:00
 * @description： 测试自定义Realm
 * @modified By：
 * @version:
 */
public class CustomRealmTest {

    @Test
    public void testCustomRealm() {

        CustomRealm customRealm = new CustomRealm();

        //1. 构建SecurityManage对象
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5"); //
        matcher.setHashIterations(1); //加密次数
        customRealm.setCredentialsMatcher(matcher);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //2. 主体提交认证请求
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("huai", "123456");

        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");
        subject.checkPermissions("user:add","user:delete");

    }


}
