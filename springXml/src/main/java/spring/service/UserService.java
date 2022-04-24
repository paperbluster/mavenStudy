package spring.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.wrapper.UserMapper;


/**
 * @author wanjun
 * @create 2022-04-19 10:18
 */
@Component
public class UserService {

    private UserMapper userMapper;

    @Transactional
    public void Test(){
        userMapper.insertOne();
        //throw new NullPointerException();
    }
}
