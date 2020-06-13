package com.yolyn.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

/**
 * @author Yolyn
 * @version 1.0
 * @date 2020/6/13 21:13
 * @project shiro-learning
 */
public class CustomerMd5Realm extends AuthorizingRealm {
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName=(String) token.getPrincipal();
        System.out.println("principal:"+token.getPrincipal()+"---"+"credentials:"+token.getCredentials().toString());
        if ("yolyn".equals(userName)){
            //加了盐的hash
            Md5Hash md5Hash=new Md5Hash(token.getCredentials(),ByteSource.Util.bytes("abc"));
            String password= md5Hash.toHex();


            return new SimpleAuthenticationInfo(userName,password, ByteSource.Util.bytes("abc"),this.getName());
        }
        return null;
    }

    public static void main(String[] args) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //使用的散列算法
        CustomerMd5Realm realm = new CustomerMd5Realm();
        //设置realm使用hash凭证匹配器,校验token的时候是调用CredentialsMatcher的实现类去校验的
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        realm.setCredentialsMatcher(credentialsMatcher);

        securityManager.setRealm(realm);
        //将安全管理器注入安全工具
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("yolyn", "123");
        try {
            subject.login(token);
            System.out.println(subject.isAuthenticated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
