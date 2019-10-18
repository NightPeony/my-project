package www.hm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hm.www.intetface.Star;
import hm.www.pojo.Pojo;
import hm.www.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import www.hm.agency.ProxyStar;
import www.hm.agency.impl.CaiXuKun;
import www.hm.agency.mybatis.UserDao;

import java.io.*;
import java.lang.reflect.Proxy;
import java.util.List;

/*这些都来自spring的测试包*/
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/springConfig.xml"}) //加载配置文件
public class BeanTest {
    //@Autowired 是按type注入  resource(这个不是spring的注解) 按名称
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Star caiXuKun;

    @Autowired
    UserDao userDao;


    @Test
    public void getObject() {
        Pojo pojo = (Pojo) applicationContext.getBean("user");
        System.out.println(pojo);

    }

    @Test
    public void daili() {
        CaiXuKun caiXuKun = new CaiXuKun();
        ProxyStar proxyStar = new ProxyStar(caiXuKun);
        // 生成代理类
        Star star = (Star) Proxy.newProxyInstance(caiXuKun.getClass().getClassLoader(), caiXuKun.getClass().getInterfaces(), proxyStar);
        star.dance();
    }

    //aspject
    @Test
    public void aspjectTest() {
        System.out.println(caiXuKun.dance());
        System.out.println(applicationContext);
    }

    @Test
    public void mybatis() {
        List<User> list = userDao.getUser();
        for (User list1 : list) {
            System.out.println("=================================================================");
            System.out.println(list1.getId());
        }
    }

    @Test
    public void me() {
        //SqlSessionFactoryBuilder读取配置文件
        SqlSessionFactory sessionFactory;
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(new InputStreamReader(new FileInputStream("D:\\warehouse\\gitWork\\seat2\\MyHope\\springMvc\\src\\main\\resources\\mybatis.cfg.xml")));
            SqlSession sqlSession = sessionFactory.openSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> user = userDao.getUser();
            System.out.println(user.get(0).getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        //通过SqlSessionFactory获取SqlSession
//        SqlSession sqlSession = sessionFactory.g();
    }

    @Test
    public void init() {
        //SqlSessionFactoryBuilder读取配置文件
        SqlSessionFactory sessionFactory;
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("");

    }

    @Test
    public void jacksonTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(new Object());

    }

}
