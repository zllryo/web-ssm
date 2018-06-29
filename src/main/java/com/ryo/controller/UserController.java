package com.ryo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryo.model.User;
import com.ryo.service.UserService;
import com.ryo.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends  BaseController{

    @Resource
    public UserService userService;

    @Autowired
    private RedisCacheUtil<User> redisCache;

    @RequestMapping("listpage")
    public String GetListPage(Integer pageNo,String username,String address)
    {
        User user=new User();
        user.setUsername(username);
        user.setAddress(address);
        PageHelper.startPage(pageNo,pagesize);
        List<User> userList=userService.selectlistBy(user);
        PageInfo<User> pageInfo=new PageInfo<User>(userList);
        session.setAttribute("userlist",userList);
        session.setAttribute("pageInfo",pageInfo);

        String redisvalue=redisCache.getCacheObject("count");
        session.setAttribute("count",redisvalue);
        return "result";
    }

    @RequestMapping("deleteBy")
    @ResponseBody
    public Map<String,Object> deleteBy(Integer id)
    {
        Map<String,Object> map=new HashMap<String, Object>();
        try
        {
            userService.deleteByPrimaryKey(id);
             map.put("msg","success");
        }
        catch (Exception ex)
        {
            map.put("msg","error");
        }
        return map;
    }

    @RequestMapping("lookBy")
    public String lookBy(Integer id)
    {
        User user=userService.selectAllByPrimaryKey(id);
         session.setAttribute("user",user);
        redisCache.setCacheObject("count","6");


         return  "userlook";
    }

    @RequestMapping("editBy")
    public String editBy(Integer id)
    {
        User user=userService.selectAllByPrimaryKey(id);
        session.setAttribute("user",user);
        return  "useredit";
    }

    @RequestMapping("addBy")
    public String addBy()
    {
        return  "useradd";
    }

    @RequestMapping("usersave")
    @ResponseBody
    public Map<String,Object> usersave(User user)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            if(user.getId()!=null)
            {
                userService.updateByPrimaryKey(user);
            }
            else{
                userService.insert(user);
            }

            map.put("msg","success");
        }
        catch (Exception ex)
        {
            map.put("msg","error");
        }

        return map;
    }
}
