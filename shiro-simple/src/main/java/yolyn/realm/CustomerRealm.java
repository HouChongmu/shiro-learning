package yolyn.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @author Yolyn
 * @version 1.0
 * @date 2020/6/13 16:51
 * @project shiro-learning
 * 自定义Realm 将认证/授权数据的来源转为数据库的实现
 */
public class CustomerRealm extends AuthorizingRealm {
    /**
     * 授权
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中获取用户名
        String principal =(String)token.getPrincipal();
        System.out.println("用户名："+principal);
        if ("yolyn".equals(principal)){
            //第一个参数：返回数据库中正确的用户名，第二个参数返回数据库中正确密码，第三个参数是注册时的随机盐，第四个参数提供当前realm的名字
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(principal,"123", this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }

    public static void main(String[] args) {
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        securityManager.setRealm(new CustomerRealm());
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject=SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken("yolyn","123"));
            System.out.println("是否认证："+subject.isAuthenticated());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
