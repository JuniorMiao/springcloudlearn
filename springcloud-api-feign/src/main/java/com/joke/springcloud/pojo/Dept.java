package com.joke.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data//get 和 set
@NoArgsConstructor//无参构造
@Accessors(chain = true)//开启链式写法   例：new Dept().setid(1).setname("test")....
public class Dept implements Serializable {

    private Long deptno;//主键
    private String dname;

    //存在于哪个数据库的字段 ~ 微服务，一个服务对应一个数据库，同一个信息可能存在于不同的数据库
    private String db_source;

    //dname的有参构造
    public Dept(String dname) {
        this.dname = dname;
    }
}
