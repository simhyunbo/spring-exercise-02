package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao() { //날개 5개 선풍기
        ConnectionMakerImpl connectionMaker = new ConnectionMakerImpl();
        //context 재사용 하는 부분이 많은 코드 - UserDao
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}
