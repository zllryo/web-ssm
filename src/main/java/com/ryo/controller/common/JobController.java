package com.ryo.controller.common;

import com.ryo.utils.DateUtils;

import java.util.Date;

public class JobController {

    public  void ExecJob()
    {
        System.out.printf(DateUtils.getCurrentDate()+"开始执行定时器1--");
    }

    public  void ExecJob2()
    {
        System.out.printf(DateUtils.getCurrentDate()+"开始执行定时器2--");
    }
}
