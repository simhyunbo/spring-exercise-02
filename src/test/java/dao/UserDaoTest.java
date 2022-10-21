package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;

    //각 @Test 메소드가 실행 될때마다 실행되는 코드가 있습니다.
    //그 부분을 Junit에서 공통화 시킬 수 있게 제공하는 기능이 BeforeEach입니다
    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao",UserDao.class);
    }

    @Test
    void addANDSelect() throws SQLException, ClassNotFoundException {
        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        userDao.deleteAll();


        User user1 = new User("1","sim","1234");
        User user2 = new User("2","kim","5678");
        User user3 = new User("3","park","qwer");

        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        userDao.add(user2);
        assertEquals(2,userDao.getCount());
        userDao.add(user3);
        assertEquals(3,userDao.getCount());



    }
    @Test
    void finById(){
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.Select("30");
        });
    }
}