package com.ryo.controller.shrio;

import com.ryo.model.User;
import com.ryo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {
    @Resource
     private UserService userService;

    @Override
    public  void  setName(String  name)
    {
        super.setName(name);
    }

    //用于认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        System.out.printf("shrio开始认证");
        UsernamePasswordToken usertoken=(UsernamePasswordToken) token;//获取令牌(里面存放new UsernamePasswordToken放入的账号和密码)

        //得到账号和密码
        String username = usertoken.getUsername();
        String password = new String((char[])usertoken.getCredentials());

        User user = userService.selectByNameandWord2(username,password);//去sql查询用户名是否存在,如果存在返回对象(账号和密码都有的对象)

        if(user!=null)//如果用户名存在
        {
            //先清除缓存
            //JedisClientSingle.getJedis().del(username);
            //参数1.用户认证的对象(subject.getPrincipal();返回的对象),
            //参数2.从数据库根据用户名查询到的用户的密码
            //参数3.把当前自定义的realm对象传给SimpleAuthenticationInfo,在配置文件需要注入
            //**密码加盐**交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
            AuthenticationInfo Info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),ByteSource.Util.bytes("3.14159"), this.getName());
            return Info;

        }else
        {
            return null;
        }
    }

    //用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) 	{
        System.out.printf("shrio开始授权");
        String name=(String)principals.getPrimaryPrincipal();
        User user=userService.selectByName(name);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        List<String> roles=new ArrayList<String>();
        List<String> permissions=new ArrayList<String>();
        permissions.add("look");
        roles.add(user.getRole().getRolename());
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 清空当前用户权限信息
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

    //清除缓存
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

    /**
     * 明文密码进行加密
     * 盐值salt
     * 密码credentials
     */
    public String  StringMD5Yan(Object salt,Object credentials) {
        int hashIterations = 10000;//加密的次数
        String hashAlgorithmName = "MD5";//加密方式
        ByteSource bsalt = ByteSource.Util.bytes(salt);//以账号作为盐值
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,
                bsalt, hashIterations);
        return  (String)simpleHash;

    }

}
