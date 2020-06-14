package com.yolyn.springboot_jsp_shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Yolyn
 * @version 1.0
 * @date 2020/6/14 20:41
 * @project shiro-learning
 */
@Controller
@RequestMapping("user")
public class UserController {
    @RequestMapping("login")
    public String login(String username,String password){
        Subject subject= SecurityUtils.getSubject();
        try {

            subject.login(new UsernamePasswordToken(username,password));
            return "redirect:/index.jsp";
        }catch (UnknownAccountException e){
            System.out.println("username is not correct");
        }catch (IncorrectCredentialsException e){
            System.out.println("password is not correct");
        }
        return "redirect:/login.jsp";
    }
    @RequestMapping("logout")
    public String logout(){
      Subject subject=  SecurityUtils.getSubject();
      subject.logout();
      return "redirect:/login.jsp";
    }

}
