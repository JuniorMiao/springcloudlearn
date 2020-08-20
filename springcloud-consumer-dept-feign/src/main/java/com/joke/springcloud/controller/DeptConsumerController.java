package com.joke.springcloud.controller;

import com.joke.springcloud.pojo.Dept;
import com.joke.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//dept服务消费者
@RestController
@RequestMapping("/consumer/dept/")
public class DeptConsumerController {

    // Feign面向接口编程
    @Autowired
    private DeptClientService deptClientService;


    @PostMapping("add")
    public boolean addDept(@RequestBody Dept dept) {
        return deptClientService.addDept(dept);
    }

    @RequestMapping("get/{id}")
    public Dept queryById(@PathVariable("id") Long id) {
        return deptClientService.queryById(id);
    }

    @RequestMapping("list")
    public List<Dept> queryAll() {
        return deptClientService.queryAll();
    }

}
