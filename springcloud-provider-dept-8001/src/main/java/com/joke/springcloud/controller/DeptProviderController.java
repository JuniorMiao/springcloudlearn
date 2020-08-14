package com.joke.springcloud.controller;

import com.joke.springcloud.pojo.Dept;
import com.joke.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//提供Restful服务
@RestController
@RequestMapping("/dept/")
public class DeptProviderController {
    @Autowired
    private DeptService deptService;

    @PostMapping("add")
    public boolean addDept(@RequestBody Dept dept) {
        return deptService.addDept(dept);
    }

    @RequestMapping("get/{id}")
    public Dept queryById(@PathVariable("id") Long id) {
        System.out.println("测试热部署");
        return deptService.queryById(id);
    }

    @RequestMapping("list")
    public List<Dept> queryAll() {
        return deptService.queryAll();
    }
}