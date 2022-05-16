package com.chj.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean3:
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(getDefaultWebSecurityManager());
        //添加shiro的内置过滤器
        /*
         anon；无需认证就能访问
         authc：必须认证了才能访问
         user；必须拥有 记住我 功能才能使用
         perms；拥有对某个资源的权限才能访问
         role；拥有某个角色权限才能访问
        */
        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
        filterMap.put("/user/*","authc");


        bean.setFilterChainDefinitionMap(filterMap);
        //没有权限 设置登录请求
        bean.setLoginUrl("/toLogin");

        return bean;
    }

    //DefaulWebSecurityManager2:
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(new UserRealm());

        return securityManager;
    }

    //创建realm对象  需要自定义类 1:
    @Bean("userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

}
