package com.joke.springcloud.controller;

import com.joke.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;

//dept服务消费者
@RestController
@RequestMapping("/consumer/dept/")
public class DeptConsumerController {

    @Autowired
    private RestTemplate restTemplate;
//    private static final String REST_URL_PREFIX = "http://localhost:8001";


    //通过ribbon去实现负载均衡，这里服务应该是一个变量，通过服务名来访问
    private static final String REST_URL_PREFIX = "http://SPRINGCLOUD-PROVIDER-DEPT";


    @PostMapping("add")
    public boolean addDept(@RequestBody Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX+"/dept/add", dept, Boolean.class);
    }

    @RequestMapping("get/{id}")
    public Dept queryById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/"+id,Dept.class);
    }

    @RequestMapping("list")
    public List<Dept> queryAll() {
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/list", List.class);
    }

}
