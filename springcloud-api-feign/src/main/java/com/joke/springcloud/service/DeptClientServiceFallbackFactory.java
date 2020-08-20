package com.joke.springcloud.service;

import com.joke.springcloud.pojo.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory{

    @Override
    public DeptClientService create(Throwable cause) {
        return new DeptClientService() {
            @Override
            public boolean addDept(Dept dept) {
                return false;
            }

            @Override
            public Dept queryById(Long id) {
                return new Dept().setDeptno(id)
                        .setDname("id=>"+id+"这个服务暂时关闭，你看到的是降级信息")
                        .setDb_source("服务降级，没有查找到数据");
            }

            @Override
            public List<Dept> queryAll() {
                return null;
            }
        };
    }
}
