package com.rainapi.rainapiinterface;

import com.rainapi.rainapiclientsdk.client.RainClient;
import com.rainapi.rainapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RainapiInterfaceApplicationTests {

    @Resource
    private RainClient rainClient;

    @Test
    void contextLoads() {
        String res1 = rainClient.getNameByGet("rain");

        User user = new User();
        user.setUsername("rain");
        String userNameByPost = rainClient.getUserNameByPost(user);

        System.out.println(res1);
        System.out.println(userNameByPost);
    }

}
