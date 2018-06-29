package com.ryo.dao;

import com.ryo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectlist();

    List<User> selectlistBy(User user);

    User selectByPrimaryKey(Integer id);

    User selectByName(String name);

    User selectByNameandWord(Map<String,String> map);

    User selectByNameandWord2(User record);

    User selectAllByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}