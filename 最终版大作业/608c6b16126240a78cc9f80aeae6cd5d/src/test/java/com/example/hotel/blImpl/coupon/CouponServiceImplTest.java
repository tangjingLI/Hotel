package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CouponServiceImplTest extends TestCase {
    @Autowired
    CouponService couponService;


    @Test
    public void getMatchOrderCoupon(){
        OrderVO orderVO=new OrderVO();
        orderVO.setHotelId(1);
        orderVO.setRoomNum(4);
        orderVO.setUserId(1);
        orderVO.setCheckInDate("2020-06-29");
        orderVO.setCheckOutDate("2020-06-30");
        orderVO.setPrice(300.0);
        List<Coupon> list = couponService.getMatchOrderCoupon(orderVO);
        assertEquals(new ArrayList<>(),list);
        //String[] name=new String[]{"name","user","type"};
        //assertEquals(true,name[0].equals("name")&&name[1].equals("user")&&name[2].equals("type"));
    }

    @Test
    public void getHotelAllCoupon(){
        System.out.println(couponService.getHotelAllCoupon(1));
    }

    @Test
    public void addHotelTargetMoneyCoupon() {
        HotelTargetMoneyCouponVO couponVO=new HotelTargetMoneyCouponVO();
        couponVO.setId(1);
        couponVO.setHotelId(1);
        couponVO.setTargetMoney(500);
        couponVO.setDiscountMoney(200);
        try {
            couponService.addHotelTargetMoneyCoupon(couponVO);
        }
        catch (Exception e){}
    }

    @Test
    public void addHotelTimeCoupon() {
        TimeCouponVO couponVO=new TimeCouponVO();
        couponVO.setId(1);
        couponVO.setHotelId(1);
        couponVO.setTargetMoney(500);
        couponVO.setDiscount(200);
        try {
            couponService.addHotelTimeCoupon(couponVO);
        }
        catch (Exception e){}
    }

    @Test
    public void addHotelTargetRoomNumCoupon() {
        HotelTargetRoomNumCouponVO couponVO=new HotelTargetRoomNumCouponVO();
        couponVO.setId(1);
        couponVO.setHotelId(1);
        couponVO.setDiscount(0.7);
        try {
            couponService.addHotelTargetRoomNumCoupon(couponVO);
        }
        catch (Exception e){}
    }

}