package com.joke.springcloud.controller;

import com.joke.springcloud.pojo.Dept;
import com.joke.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//提供Restful服务
@RestController
@RequestMapping("/dept/")
public class DeptProviderController {

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private DeptService deptService;

    @PostMapping("add")
    public boolean addDept(@RequestBody Dept dept) {
        return deptService.addDept(dept);
    }

    @RequestMapping("get/{id}")
    @HystrixCommand(fallbackMethod = "hystrixGet") //如果失败 去调用Hystrix的备选方案
    public Dept queryById(@PathVariable("id") Long id) throws RuntimeException {
        System.out.println("测试热部署");
        Dept dept = deptService.queryById(id);
        if (dept == null) {
            throw new RuntimeException("id=> "+ id+"不存在该用户,或者该信息无法找到");
        }
        return dept;
    }

    //如果出现异常 采取Hystrix的备选方案
    public Dept hystrixGet(@PathVariable("id") Long id){
        return new Dept()
                .setDeptno(id)
                .setDname("id=> "+id+"没有找到相关信息,null by Hystrix")
                .setDb_source("not found database in mysql");
    }

    @RequestMapping("list")
    public List<Dept> queryAll() {
        return deptService.queryAll();
    }

    //注册进来的微服务，获取一些信息。没有实际作用
    @RequestMapping("/discovery")
    public Object discovery(){
        //获取微服务列表的清单
        List<String> services = client.getServices();
        System.out.println("discovery=>services "+services);

        //得到一个具体的微服务信息,通过具体的微服务id，7001中的applicationName
        List<ServiceInstance> instances = client.getInstances("SPRINGCLOUD-PROVIDER-DEPT");
        for (ServiceInstance instance : instances) {
            System.out.println(
                    instance.getHost()+"\t\t\t"+
                            instance.getPort()+"\t\t\t"+
                            instance.getUri()+"\t\t\t"+
                            instance.getServiceId());
        }
        return this.client;
    }

}