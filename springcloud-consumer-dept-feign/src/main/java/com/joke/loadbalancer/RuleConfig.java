package com.joke.loadbalancer;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfig {


    @Bean
    public IRule myRuleCongfig() {
        return new MyRandomRule();
    }
}
