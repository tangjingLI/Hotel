package com.example.hotel.blImpl.hotel;


import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import  com.example.hotel.data.hotel.HotelMapper;

import com.example.hotel.util.ServiceException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class HotelServiceImplTest extends TestCase {


    @Autowired
    HotelService hotelService;

    @Autowired
    HotelMapper hotelMapper;

    @Test
    public void addHotel() throws ServiceException {
        HotelVO hotelVO=new HotelVO();
        hotelVO.setName("测试酒店");
        hotelVO.setDescription("测试");
        hotelVO.setAddress("北京");
        hotelVO.setPhoneNum("123456789");
        hotelVO.setHotelStar("Five");
        hotelVO.setBizRegion("XiDan");
        hotelVO.setRate(200.0);
        hotelVO.setManagerId(6);
        hotelVO.setId(4);

        hotelService.addHotel(hotelVO);
        assertEquals(hotelVO.getName(),hotelMapper.selectById(4).getName());

    }

    @Test
    public void updateRoomInfo() {
        int curnum = hotelService.getRoomCurNum(1,"DoubleBed",299);
        hotelService.updateRoomInfo(1,"DoubleBed",299,1);
        //System.out.println(hotelService.getRoomCurNum(1,"DoubleBed",299,1));
        assertEquals(curnum-1,hotelService.getRoomCurNum(1,"DoubleBed",299));
    }

    @Test
    public void retrieveHotels() {
        System.out.println(hotelService.retrieveHotels());
    }

    @Test
    public void deleteHotel() throws ServiceException{
        HotelVO hotelVO=new HotelVO();
        hotelVO.setName("测试酒店2号");
        hotelVO.setDescription("测试");
        hotelVO.setAddress("北京");
        hotelVO.setPhoneNum("123456789");
        hotelVO.setHotelStar("Five");
        hotelVO.setBizRegion("XiDan");
        hotelVO.setRate(100.0);
        hotelVO.setManagerId(6);
        hotelVO.setId(4);
        hotelService.deleteByHotelId(4);
        assertEquals(null,hotelMapper.selectById(4));

    }

    @Test
    public void getRoomCurNum() {

        assertEquals(20, hotelService.getRoomCurNum(1,"BigBed",199));
        //System.out.println(hotelService.getRoomCurNum(1,"BigBed",199));
    }

    @Test
    public void retrieveHotelDetails() throws ServiceException{
        try {
            hotelService.retrieveHotelDetails(1);
        }
        catch (Exception e){}
        //System.out.println(hotelService.retrieveHotelDetails(1));
    }
}