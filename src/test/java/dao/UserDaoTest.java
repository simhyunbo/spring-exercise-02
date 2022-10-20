package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;


    @Test
    void addANDSelect() throws SQLException, ClassNotFoundException {
        //public UserDao awsUserDao(){} 함수 호출을 한다.
        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        //userDao.deleteAll();
//        String id = "25";
//        userDao.add(new User(id,"Nunu2","112233qqww"));
//        User user = userDao.Select(id);
//        Assertions.assertEquals("Nunu2",user.getName());
        
        //getCount()테스트
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
}