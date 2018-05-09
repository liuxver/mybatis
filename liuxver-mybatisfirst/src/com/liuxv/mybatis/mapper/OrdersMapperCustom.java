package com.liuxv.mybatis.mapper;

import com.liuxv.mybatis.po.Orders;
import com.liuxv.mybatis.po.OrdersCustom;
import com.liuxv.mybatis.po.User;

import java.util.List;

/**
 * created by liuxv on 2018/5/5
 * email:liuxver444@gmail.com
 * qq:1369058574
 */
public interface OrdersMapperCustom {
    //查询订单关联查询用户信息
    public List<OrdersCustom> findOrdersUser()throws Exception;


    //查询订单关联查询用户使用resultMap
    public List<Orders> findOrdersUserResultMap()throws Exception;

    //查询订单(关联用户)及订单明细
    public List<Orders>  findOrdersAndOrderDetailResultMap()throws Exception;


    //查询用户购买商品信息
    public List<User>  findUserAndItemsResultMap()throws Exception;



    //查询订单关联查询用户，用户信息是延迟加载
    public List<Orders> findOrdersUserLazyLoading()throws Exception;

}
