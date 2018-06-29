package com.ryo.service.impl;

import com.ryo.dao.RoleMapper;
import com.ryo.dao.UserMapper;
import com.ryo.model.Role;
import com.ryo.model.User;
import com.ryo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Integer id){
       return  userMapper.deleteByPrimaryKey(id);
    }


    @Override
    public int insert(User record){
        return  userMapper.insert(record);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertall(User user, Role role)
    {
        roleMapper.insert(role);
        user.setRoleid(role.getId());
        return  userMapper.insert(user);
    }

    @Override
    public int insertSelective(User record){
        return userMapper.insertSelective(record);
    }

    @Override
    public List<User> selectlist()
    {
        return  userMapper.selectlist();
    }

    @Override
    public List<User> selectlistBy(User user)
    {
        return  userMapper.selectlistBy(user);
    }

    @Override
    public User selectByPrimaryKey(Integer id){
        return  userMapper.selectByPrimaryKey(id);
    }

    @Override
    public  User selectByName(String name)
    {
        return  userMapper.selectByName(name);
    }

    @Override
    public User selectByNameandWord(Map<String,String> map){
        return  userMapper.selectByNameandWord(map);
    }

    @Override
    public User selectByNameandWord2(String username,String password){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        return  userMapper.selectByNameandWord2(user);
    }

    @Override
    public   User selectAllByPrimaryKey(Integer id)
    {
        return userMapper.selectAllByPrimaryKey(id);
    }
    @Override
    public int updateByPrimaryKeySelective(User record){
        return  userMapper.updateByPrimaryKeySelective(record);
    }
    @Override
    public int updateByPrimaryKey(User record){
        return  userMapper.updateByPrimaryKey(record);
    }
}
