package com.liuxv.ssm.dao;

import com.liuxv.ssm.po.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * created by liuxv on 2018/5/5
 * email:liuxver444@gmail.com
 * qq:1369058574
 */
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{


    @Override
    public User findUserById(int id) throws Exception {
        //继承SqlSessionDaoSupport，通过this.getSqlSession()得到sqlSessoin
        SqlSession sqlSession = this.getSqlSession();
        User user = sqlSession.selectOne("test.findUserById",id);

        return user;
    }

}