package com.liuxv.mybatis.po;

import java.util.List;

/**
 * created by liuxv on 2018/5/5
 * email:liuxver444@gmail.com
 * qq:1369058574
 */
public class UserQueryVo {


    //在这里包装所需要的查询条件
    //传入多个id
    private List<Integer> ids;


    //用户查询条件
    private UserCustom userCustom;

    public UserCustom getUserCustom() {
        return userCustom;
    }

    public void setUserCustom(UserCustom userCustom) {
        this.userCustom = userCustom;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    //可以包装其它的查询条件，订单、商品
    //....

}
