package com.huai.shiro.realm;

import com.mysql.jdbc.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ：shizhonghuai
 * @date ：2019/5/20 21:42
 * @description： 自定义Realm
 * @modified By：
 * @version:
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String, String> userMap = new HashMap<>(16);

    {
        userMap.put("huai", "123456");

        //设置Realm的name
        super.setName("customRealm");
    }


    /**
     * create by: shizhonghuai
     * description: 用户认证
     * create time: 2019/5/20 22:18
     *
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从主体传过来的认证信息中获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();

        //通过用户名从数据库或者缓存中获取角色数据
        Set<String> roles = getRolesByUserName(userName);

        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * create by: shizhonghuai
     * description: 通过用户名从数据库或缓存中获取用户权限数据
     * create time: 2019/5/20 22:23
     *
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> permissions = new HashSet<>(16);
        permissions.add("user:add");
        permissions.add("user:delete");
        return permissions;
    }

    /**
     * create by: shizhonghuai
     * description: 通过用户名获取角色数据
     * create time: 2019/5/20 22:20
     *
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles = new HashSet<>(16);
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    /**
     * create by: shizhonghuai
     * description: 用户登录
     * create time: 2019/5/20 22:18
     *
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1. 从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();

        //2. 通过用户名到数据库中获取凭证
        String passWord = getPassWordByUserName(userName);
        if (StringUtils.isNullOrEmpty(passWord)) {
            //密码为空表该用户不存在
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("huai", passWord, "customRealm");

        return simpleAuthenticationInfo;
    }

    /**
     * create by: shizhonghuai
     * description: 用于模拟数据库
     * create time: 2019/5/20 21:56
     *
     * @return
     */
    private String getPassWordByUserName(String userName) {

        return userMap.get(userName);
    }


}
