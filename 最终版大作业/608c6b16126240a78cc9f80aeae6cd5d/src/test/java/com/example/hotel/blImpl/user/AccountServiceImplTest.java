package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplTest extends TestCase {
    @Autowired
    AccountService accountService;

    @Test
    public void loginTest() {
        UserForm userForm = new UserForm();
        userForm.setEmail("185@qq.com");
        userForm.setPassword("123456");
        System.out.println(accountService.login(userForm));
    }

    @Test
    public void registerTest() {
        UserVO userVO = new UserVO();
        userVO.setCredit(100);
        userVO.setEmail("121@qq.com");
        userVO.setPassword("123456");
        userVO.setUserName("测试号");
        userVO.setPhoneNumber("1111111111");
        System.out.println(accountService.registerAccount(userVO));
    }

    @Test
    public void updateUserInfo() {
        accountService.updateUserInfo(7,"123456","test_1号","11111111112");
        User user=accountService.getUserInfo(7);
        assertEquals("123456",user.getPassword());     //修改密码
    }

}