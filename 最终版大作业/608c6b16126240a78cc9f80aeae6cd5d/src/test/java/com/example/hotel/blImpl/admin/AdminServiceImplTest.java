package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceImplTest extends TestCase {
    @Autowired
    AdminService adminService;
    @Autowired
    AccountService accountService;

    @Test
    public void getAllClientTest(){
        try {
            adminService.getAllClients();
        }
        catch (Exception e){}
        //System.out.println(adminService.getAllClients());
    }

    @Test
    public void getAllManagersTest(){
        try {
            adminService.getAllManagers();
        }
        catch (Exception e){}
        //System.out.println(adminService.getAllManagers());
    }

    @Test
    public void addManager() {
        UserForm userForm=new UserForm();
        userForm.setEmail("122@qq.com");
        userForm.setPassword("123456");
        try {
            adminService.addManager(userForm);
        }
        catch (Exception e){}
        System.out.println(adminService.getAllManagers());
    }

}