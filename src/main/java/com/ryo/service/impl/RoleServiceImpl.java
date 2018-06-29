package com.ryo.service.impl;

import com.ryo.dao.RoleMapper;
import com.ryo.model.Role;
import org.springframework.stereotype.Service;
import com.ryo.service.RoleService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements RoleService{
    @Resource
    RoleMapper roleMapper;
    public int deleteByPrimaryKey(Integer id)
    {
        return  roleMapper.deleteByPrimaryKey(id);
    }

    public int insert(Role record)
    {
        return  roleMapper.insert(record);
    }

    public int insertSelective(Role record)
    {
        return  roleMapper.insertSelective(record);
    }

    public Role selectByPrimaryKey(Integer id)
    {
       return  roleMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Role record)
    {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Role record)
    {
        return  roleMapper.updateByPrimaryKey(record);
    }
}
