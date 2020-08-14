package com.joke.springcloud.mapper;

import com.joke.springcloud.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptDao {
    //添加一个部门
    public boolean addDept(Dept dept);
    //根据id查出一个部门
    public Dept queryById(Long id);
    //查询所有部门信息
    public List<Dept> queryAll();
}