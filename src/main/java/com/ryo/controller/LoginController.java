package com.ryo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryo.aop.SystemLog;
import com.ryo.model.Role;
import com.ryo.model.User;
import com.ryo.service.RoleService;
import com.ryo.service.UserService;
import com.ryo.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController extends BaseController{

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @Autowired
    private RedisCacheUtil<Object> redisCache;

    @RequestMapping("login")
    public String login() {
        return "index";
    }

    @RequestMapping("Checklogin")
    @ResponseBody
    @SystemLog(description ="登陆",operationName = "admin")
    public Map<String,Object> Checklogin(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        User model= userService.selectByNameandWord2(user.getUsername(),user.getPassword());
     /*   Map<String, String> map2 = new HashMap<String, String>();
        map2.put("username",user.getUsername());
        map2.put("password",user.getPassword());
        User model= userService.selectByNameandWord(map2);*/

        if(model!=null)
        {
            User model2=  userService.selectAllByPrimaryKey(model.getId());
            session.setAttribute("user",model2);
            PageHelper.startPage(pageindex,pagesize);
            List<User> userList=userService.selectlist();
            PageInfo<User> pageInfo=new PageInfo<User>(userList);
            session.setAttribute("userlist",userList);
            session.setAttribute("pageInfo",pageInfo);
            redisCache.setCacheObject("count","10");
            map.put("msg","success");
        }
        else{
            map.put("msg","error");
        }
        return  map;
    }

    @RequestMapping("register")
    public String register() {
        return "register";
    }

    @RequestMapping("saveregister")
    @ResponseBody

    public Map<String,Object> saveregister(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        User model=userService.selectByName(user.getUsername());
        if (model==null)
        {
            Role role=new Role();
            role.setRolename("角色1");
            role.setRoledesc("xxx");
           /* user.setUsername("角色1角色1角色1角色1角色1角色1");*/
            userService.insertall(user,role);

            map.put("msg","success");
        }
        else{
            map.put("msg","error");
        }
        return map;
    }

    @RequestMapping("error")
    public String error() {
        return "error";
    }

    @RequestMapping("result")
    public String result(HttpServletRequest request) {
        return "result";
    }

    @RequestMapping("loginout")
    public  String loginout()
    {
        session.invalidate();
        return  "index";
    }
}