package com.liuxv.ssm.mapper;

import com.liuxv.ssm.po.User;

/**
 * created by liuxv on 2018/5/5
 * email:liuxver444@gmail.com
 * qq:1369058574
 */
public interface UserMapper {
    //根据id查询用户信息
    User findUserById(int id) throws Exception;

}
