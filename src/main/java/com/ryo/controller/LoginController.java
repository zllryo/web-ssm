package com.ryo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ryo.aop.SystemLog;
import com.ryo.controller.shrio.CustomRealm;
import com.ryo.model.Role;
import com.ryo.model.User;
import com.ryo.service.RoleService;
import com.ryo.service.UserService;
import com.ryo.utils.RedisCacheUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
    private  CustomRealm customRealm;

    @Autowired
    private RedisCacheUtil<Object> redisCache;

    @RequestMapping("login")
    public String login() {
        return "index";
    }

    @RequestMapping("Checklogin")
    @ResponseBody
    @SystemLog(description ="登陆",operationName = "admin")
    public Map<String,Object> Checklogin(User user,HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        User model= userService.selectByNameandWord2(user.getUsername(),user.getPassword());
     /*   Map<String, String> map2 = new HashMap<String, String>();
        map2.put("username",user.getUsername());
        map2.put("password",user.getPassword());
        User model= userService.selectByNameandWord(map2);*/
        String  rememberMe= request.getParameter("rememberMe");
        Subject subject = SecurityUtils.getSubject();//获取当前用户对象
        //生成令牌(传入用户输入的账号和密码)
        UsernamePasswordToken token=new UsernamePasswordToken(model.getUsername(),model.getPassword());
        if(rememberMe=="1"||rememberMe.equals("1"))
        {
            token.setRememberMe(true);
        }
        //这里会加载自定义的realm
        subject.login(token);//把令牌放到login里面进行查询,如果查询账号和密码时候匹配,如果匹配就把user对象获取出来,失败就抛异常

        if(model!=null)
        {
            User model2=  userService.selectAllByPrimaryKey(model.getId());
            session.setAttribute("user",model2);
            PageHelper.startPage(pageindex,pagesize);
            List<User> userList=userService.selectlist();
            PageInfo<User> pageInfo=new PageInfo<User>(userList);
            session.setAttribute("userlist",userList);
            session.setAttribute("pageInfo",pageInfo);
            //redisCache.setCacheObject("count","10");
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
        //从shiro的session中取出activeUser
        Subject subject= SecurityUtils.getSubject();
        //取出身份信息
        String userCode=(String)subject.getPrincipal();
        //通过model传到页面
        session.setAttribute("userCode",userCode);
        return "result";
    }

    @RequestMapping("loginout")
    public  String loginout()
    {
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        return  "index";
    }

    @RequestMapping("noauthorization")
    public String noauthorization() {
        return "noauthorization";
    }
}
