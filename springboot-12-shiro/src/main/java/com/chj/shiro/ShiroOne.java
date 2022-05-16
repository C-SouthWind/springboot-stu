package com.chj.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroOne {


        private static final transient Logger log = LoggerFactory.getLogger(ShiroOne.class);


        public static void main(String[] args) {

            //使用工厂读取shiro.ini
            Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
            //获得实例
            SecurityManager securityManager = factory.getInstance();
            //设置安全管理器
            SecurityUtils.setSecurityManager(securityManager);


            // 获取当前执行对象
            Subject currentUser = SecurityUtils.getSubject();

            //使用 Session 做一些事情
            Session session = currentUser.getSession();
            //设置Session值
            session.setAttribute("someKey", "aValue");
            //获取Session值
            String value = (String) session.getAttribute("someKey");
            if ("aValue".equals(value)) {
                log.info("session中设置的someKey值是：  [" + value + "]");
            }

            // 检查是否登录
            if (!currentUser.isAuthenticated()) {
                //获取token
                UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
                //记住我
                token.setRememberMe(true);
                try {
                    //登录
                    currentUser.login(token);
                } catch (UnknownAccountException uae) {//未知账户
                    log.info("没有用户名为 " + token.getPrincipal());
                } catch (IncorrectCredentialsException ice) {//凭证错误
                    log.info("帐户密码 " + token.getPrincipal() + " 不正确!");
                } catch (LockedAccountException lae) {//账户锁定
                    log.info("用户名的帐户 " + token.getPrincipal() + " 锁住了  ");
                }
                // ... catch more exceptions here (maybe custom ones specific to your application?
                catch (AuthenticationException ae) {//身份验证异常
                    //unexpected condition?  error?
                }
            }

            //获得当前用户的认证
            log.info("用户 [" + currentUser.getPrincipal() + "] 登录成功.");

            //是否拥有这个角色
            if (currentUser.hasRole("schwartz")) {
                log.info("你好  schwartz");
            } else {
                log.info("没有这个角色!");
            }

            //粗粒度权限
            if (currentUser.isPermitted("lightsaber:wield")) {
                log.info("你拥有lightsaber:wield 权限");
            } else {
                log.info("你没有lightsaber:wield权限");
            }

            //细粒度权限
            if (currentUser.isPermitted("winnebago:drive:eagle5")) {
                log.info("你拥有winnebago:drive:eagle5权限!");
            } else {
                log.info("你没有winnebago:drive:eagle5权限");
            }

            //退出
            currentUser.logout();

            System.exit(0);
        }
}
