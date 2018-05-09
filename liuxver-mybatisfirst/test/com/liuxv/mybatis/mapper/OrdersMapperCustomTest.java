package com.liuxv.mybatis.mapper;

import com.liuxv.mybatis.po.Orders;
import com.liuxv.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * created by liuxv on 2018/5/5
 * email:liuxver444@gmail.com
 * qq:1369058574
 */
public class OrdersMapperCustomTest {


    private SqlSessionFactory sqlSessionFactory;

    //注解Before是在执行本类所有测试方法之前先调用这个方法
    @Before
    public void setup() throws Exception{
        //创建SqlSessionFactory
        String resource="SqlMapConfig.xml";

        //将配置文件加载成流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂，传入mybatis配置文件的信息
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
    }



    @Test
    public void testFindOrdersUserResultMap() throws Exception {

       // SqlSessionFactory sqlSessionFactory = null;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);

        // 调用maper的方法
        List<Orders> list = ordersMapperCustom.findOrdersUserResultMap();

        System.out.println(list);

        sqlSession.close();
    }




    @Test
    public void testFindOrdersAndOrderDetailResultMap() throws Exception {

        // SqlSessionFactory sqlSessionFactory = null;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);

        // 调用maper的方法
        List<Orders> list = ordersMapperCustom.findOrdersAndOrderDetailResultMap();

        System.out.println(list);

        sqlSession.close();
    }




    @Test
    public void testfindUserAndItemsResultMap() throws Exception {

        // SqlSessionFactory sqlSessionFactory = null;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 创建代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);

        // 调用mapper的方法
        List<User> list = ordersMapperCustom.findUserAndItemsResultMap();

        System.out.println(list);

        sqlSession.close();
    }




    // 查询订单关联查询用户，用户信息使用延迟加载
    @Test
    public void testFindOrdersUserLazyLoading() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();// 创建代理对象
        OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
        // 查询订单信息（单表）
        List<Orders> list = ordersMapperCustom.findOrdersUserLazyLoading();

        // 遍历上边的订单列表
        for (Orders orders : list) {
            // 执行getUser()去查询用户信息，这里实现按需加载
            User user = orders.getUser();
           System.out.println(user);


            //这里会有一个小bug  因为是调用下面的函数 得到的结果是空
            //不能在延迟加载没有全部完成之前进行加载
          //orders.print();
        }
        //System.out.println( list.size()  );
        //for(Orders orders:list){
          //  System.out.println( orders.getUser() );
       // }
    }




    // 一级缓存测试
    @Test
    public void testCache1() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();// 创建代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 下边查询使用一个SqlSession
        // 第一次发起请求，查询id为1的用户
        User user1 = userMapper.findUserById(1);
        System.out.println(user1);

        // 如果sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

        // 更新user1的信息
       // user1.setUsername("测试用户22");
         //userMapper.updateUser(user1);
        //执行commit操作去清空缓存
         //sqlSession.commit();

        // 第二次发起请求，查询id为1的用户
        User user2 = userMapper.findUserById(1);
        System.out.println(user2);

        sqlSession.close();

    }




    // 二级缓存测试
    /*
    useCache配置：
在statement中设置useCache=false可以禁用当前select语句的二级缓存，
即每次查询都会发出sql去查询，默认情况是true，即该sql使用二级缓存。

<select id="findOrderListResultMap" resultMap="ordersUserMap" useCache="false">

总结：针对每次查询都需要最新的数据sql，要设置成useCache=false，禁用二级缓存。

刷新缓存（就是清空缓存）：
刷新缓存就是清空缓存。在mapper的同一个namespace中，
如果有其它insert、update、delete操作数据后需要刷新缓存，如果不执行刷新缓存会出现脏读。

设置statement配置中的flushCache="true"属性，默认情况下为true即刷新缓存，
如果改成false则不会刷新。使用缓存时如果手动修改数据库表中的查询数据会出现脏读。如下：

<insert id="insertUser" parameterType="cn.itcast.mybatis.po.User" flushCache="true">

总结：一般下执行完commit操作都需要刷新缓存，flushCache=true表示刷新缓存，这样可以避免数据库脏读。

应用场景和局限性
应用场景：
对于访问多的查询请求且用户对查询结果实时性要求不高，此时可采用mybatis二级缓存技术降低数据库访问量，
提高访问速度，业务场景比如：耗时较高的统计分析sql、电话账单查询sql等。

实现方法如下：通过设置刷新间隔时间，由mybatis每隔一段时间自动清空缓存，
根据数据变化频率设置缓存刷新间隔flushInterval，比如设置为30分钟、60分钟、24小时等，根据需求而定。

局限性
mybatis二级缓存对细粒度的数据级别的缓存实现不好，比如如下需求：对商品信息进行缓存，
由于商品信息查询访问量大，但是要求用户每次都能查询最新的商品信息，此时如果使用mybatis的二
级缓存就无法实现当一个商品变化时只刷新该商品的缓存信息而不刷新其它商品的信息，
因为mybaits的二级缓存区域以mapper为单位划分，当一个商品信息变化会将所有商品信息的缓存数据全部清空。
解决此类问题需要在业务层根据需求对数据有针对性缓存。
     */
    @Test
    public void testCache2() throws Exception {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        // 创建代理对象
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
        // 第一次发起请求，查询id为1的用户
        User user1 = userMapper1.findUserById(1);
        System.out.println(user1);

        //这里执行关闭操作，将sqlsession中的数据写到二级缓存区域
        sqlSession1.close();


		//使用sqlSession3执行commit()操作
		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
		User user  = userMapper3.findUserById(1);
		user.setUsername("张明明");
		userMapper3.updateUser(user);
		//执行提交，清空UserMapper下边的二级缓存
		sqlSession3.commit();
		sqlSession3.close();



        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        // 第二次发起请求，查询id为1的用户
        User user2 = userMapper2.findUserById(1);
        System.out.println(user2);

        sqlSession2.close();
    }



}
