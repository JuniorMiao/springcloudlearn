package com.joke.loadbalancer;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MyRandomRule extends AbstractLoadBalancerRule {
    //当前自定义负载均衡：
    //每个服务访问5次。换下一个服务
    //total=0，默认=0，如果=5，指向下一个服务节点
    //index=0，默认0，如果total=5，则inedx+1

    private int totla=0;//被调用的次数
    private int currentIndex=0;//当前是谁在提供服务
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        } else {
            Server server = null;

            while (server == null) {
                if (Thread.interrupted()) {
                    return null;
                }

                List<Server> upList = lb.getReachableServers();//获得活着的服务
                List<Server> allList = lb.getAllServers();//获得全部的服务
                int serverCount = allList.size();
                if (serverCount == 0) {
                    return null;
                }

                //int index = this.chooseRandomInt(serverCount);//生成区间随机数
                //server = (Server) upList.get(index);//从活着的服务中，随机获取一个

                //================自定义负载均衡算法==================
                if(totla<5){
                    server = upList.get(currentIndex);
                    totla++;
                }else{
                    totla=0;
                    currentIndex++;
                    if (currentIndex>=upList.size()){//当前节点大于活着的数量
                        currentIndex = 0;
                    }
                    server=upList.get(currentIndex);//从活着的服务中，获取指定的服务来进行操作
                }
                //====================================================
                if (server == null) {
                    Thread.yield();
                } else {
                    if (server.isAlive()) {
                        return server;
                    }

                    server = null;
                    Thread.yield();
                }
            }

            return server;
        }
    }

    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}
