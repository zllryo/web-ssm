package com.ryo.dao;

import com.ryo.model.Depart;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Depart record);

    int insertSelective(Depart record);

    Depart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Depart record);

    int updateByPrimaryKey(Depart record);
}