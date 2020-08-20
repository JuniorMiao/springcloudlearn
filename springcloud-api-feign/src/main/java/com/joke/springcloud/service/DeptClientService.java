package com.joke.springcloud.service;

import com.joke.springcloud.pojo.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "SPRINGCLOUD-PROVIDER-DEPT",fallbackFactory = DeptClientServiceFallbackFactory.class)
@RequestMapping("/dept/")
@Component
public interface DeptClientService {

    @PostMapping("add")
    public boolean addDept(@RequestBody Dept dept);

    @RequestMapping("get/{id}")
    public Dept queryById(@PathVariable("id") Long id);

    @RequestMapping("list")
    public List<Dept> queryAll();

}
