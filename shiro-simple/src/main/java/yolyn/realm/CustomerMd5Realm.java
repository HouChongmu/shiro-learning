package yolyn.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
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

    /**
     * 权限认证
     *
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String principal = (String) principals.getPrimaryPrincipal();
        System.out.println("身份信息：" + principal);
        //根据身份信息 用户名 获取当前用户的角色信息和权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //将数据库中查询角色信息赋值给权限对象
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user");
        //将数据库查询权限信息赋值给授权的用户
        simpleAuthorizationInfo.addStringPermission("user:*:01");
        return simpleAuthorizationInfo;
    }

    /**
     * 数据校验
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        System.out.println("principal:" + token.getPrincipal() + "---" + "credentials:" + token.getCredentials().toString());
        if ("yolyn".equals(userName)) {
            //加了盐的hash
            Md5Hash md5Hash = new Md5Hash(token.getCredentials(), ByteSource.Util.bytes("abc"));
            String password = md5Hash.toHex();
            return new SimpleAuthenticationInfo(userName, password, ByteSource.Util.bytes("abc"), this.getName());
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
            System.out.println("=========================");
            System.out.println("yolyn是否是admin角色："+subject.hasRole("admin"));

            //基于权限字符串的访问控制，资源标识符：操作：资源类型
            System.out.println("01用户是否有update权限："+subject.isPermitted("user:update:02"));
            //分别有哪些权限
            subject.isPermitted();
            //同时具有哪些权限
            subject.isPermittedAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
