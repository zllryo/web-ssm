package com.ryo.service;


import com.ryo.model.Role;
import com.ryo.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertall(User user, Role role);

    int insertSelective(User record);

    List<User> selectlist();

    List<User> selectlistBy(User user);

    User selectByPrimaryKey(Integer id);

    User selectByName(String name);

    User selectByNameandWord(Map<String,String> map);

    User selectByNameandWord2(String username,String password);

    User selectAllByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
