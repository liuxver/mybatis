package com.liuxv.ssm.mapper;

import com.liuxv.ssm.po.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * created by liuxv on 2018/5/5
 * email:liuxver444@gmail.com
 * qq:1369058574
 */
public class UserMapperTest {

    private ApplicationContext applicationContext;

    //在setUp这个方法得到spring容器
    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
    }



    @Test
    public void testFindUserById() throws Exception {


        UserMapper userMapper = (UserMapper)applicationContext.getBean("userMapper");

        //调用userMapper的方法

        User user = userMapper.findUserById(1);

        System.out.println(user);

    }

}