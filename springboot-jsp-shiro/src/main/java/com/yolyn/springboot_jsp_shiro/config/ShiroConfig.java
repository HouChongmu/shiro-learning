package com.yolyn.springboot_jsp_shiro.config;

import com.yolyn.springboot_jsp_shiro.shiro.realms.CustomerRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yolyn
 * @version 1.0
 * @date 2020/6/14 16:24
 * @project shiro-learning
 * <p>
 * 1. 创建shiroFilter
 * 2. 创建安全管理器
 * 3. 创建自定义realm
 * <p>
 * shiroFilter拦截所有请求  用安全管理器去处理   安全管理器又需要realm处理数据源
 */
@Configuration
public class ShiroConfig {
    /**
     * 负责拦截所有请求
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> resource=new HashMap<>();
        resource.put("/user/login","anon");//设置公共资源
        resource.put("/**","authc");//authc表示请求这个资源需要认证和授权 ，/**表示所有资源都需要认证，但是记得将登录资源设置成公共资源，否则进入登陆死循环
        //默认的认证界面
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(resource);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    @Bean("myRealm")
    public Realm getRealm() {
        return new CustomerRealm();
    }

}
