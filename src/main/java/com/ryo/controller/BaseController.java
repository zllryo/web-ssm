package com.ryo.controller;


import com.ryo.model.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.ejb.Schedule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    protected  int pageindex=1;
    protected  int pagesize=5;
    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected HttpSession session;

    /*
    *该Controller的所有方法在调用前，先执行此@ModelAttribute方法
    * */
    @ModelAttribute
    public void setallbeans(HttpServletResponse response,HttpServletRequest request)
    {
        this.response=response;
        this.request=request;
        this.session=request.getSession();
    }

    /*
    * 获取登录用户信息，信息存储于session
    * @return 登录用户信息实例
    *  */
    public User getSessionUser()
    {
        return  (User)this.session.getAttribute("user");
    }

/*    @Scheduled(fixedRate = 6000)
    public  void  Dsum()
    {
        System.out.printf("定时器执行");
    }


    @Scheduled(cron = "0 34 13 * * ?")
    public  void EverPrint()
    {
        System.out.printf("每天开始");
    }*/
}
