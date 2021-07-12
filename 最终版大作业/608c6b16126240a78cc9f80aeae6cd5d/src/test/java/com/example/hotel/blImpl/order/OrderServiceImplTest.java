package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceImplTest {
    @Autowired
    OrderService orderService;

    @Test
    public void addOrder(){
        OrderVO orderVO=new OrderVO();
        orderVO.setRoomType("Family");
        orderVO.setClientName("测试号");
        orderVO.setCreateDate("2020-05-03");
        orderVO.setPhoneNumber("12453");
        orderVO.setPeopleNum(2);
        orderVO.setHotelId(1);
        orderVO.setHaveChild(true);
        orderVO.setUserId(4);
        orderVO.setPrice(399.0);
        orderVO.setRoomNum(1);
        orderVO.setCheckInDate("2020-06-01");
        orderVO.setCheckOutDate("2020-06-03");

        orderService.addOrder(orderVO);
    }

    @Test
    public void getAllOrdersTest(){
        try {
            orderService.getAllOrders();
        }
        catch (Exception e){}
        //System.out.println(orderService.getAllOrders());
    }

    @Test
    public void  getUserOrdersTest(){
        try {
            orderService.getUserOrders(4);
        }
        catch (Exception e){}
        //System.out.println(orderService.getUserOrders(20));
    }

    @Test
    public void  getHotelOrdersTest(){
        try {
            orderService.getHotelOrders(1);
        }
        catch (Exception e){}
        //System.out.println(orderService.getHotelOrders(1));
    }

    @Test
    public void  deleteByOrderId(){
        OrderVO orderVO=new OrderVO();
        orderVO.setRoomType("DoubleBed");
        orderVO.setClientName("测试号");
        orderVO.setCreateDate("2020-06-03");
        orderVO.setPhoneNumber("123");
        orderVO.setPeopleNum(2);
        orderVO.setHotelId(1);
        orderVO.setHaveChild(true);
        orderVO.setUserId(5);
        orderVO.setPrice(299.0);
        orderVO.setRoomNum(3);
        orderVO.setCheckInDate("2020-06-09");
        orderVO.setCheckOutDate("2020-06-10");

        int before = orderService.getAllOrders().size();
        orderService.addOrder(orderVO);
        orderService.deleteByOrderId(orderService.getAllOrders().get(0).getId());
        int after = orderService.getAllOrders().size();
        assertEquals(before, after);
    }
}

