package com.ryo.controller;

import java.util.HashMap;
import java.util.Map;

public class TestController {


    public  void test()
    {
        Map<String,String> map=new HashMap<String,String>();
        for(Object s:((HashMap) map).keySet())
        {
           System.out.printf(s+map.get(s));
        }
    }
}
