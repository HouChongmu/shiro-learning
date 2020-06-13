package com.yolyn;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @author Yolyn
 * @version 1.0
 * @date 2020/6/13 15:37
 * @project shiro-learning
 */
public class TestAuthenticator {
    public static void main(String[] args) {
        //1. 创建安全管理器
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        //2. 给安全管理器设置realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));
        //3. 给securitUtils全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //4. 拿到subject
        Subject subject=SecurityUtils.getSubject();
        // 5. 创建令牌
        UsernamePasswordToken token=new UsernamePasswordToken("yolyn","123");
        try {
            System.out.println("认证状态："+subject.isAuthenticated());
            subject.login(token);
            System.out.println("认证状态："+subject.isAuthenticated());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
