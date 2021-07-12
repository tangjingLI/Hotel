package com.example.hotel.blImpl.hotel;


import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.vo.RoomVO;
import com.example.hotel.util.ServiceException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;


import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RoomServiceImplTest extends TestCase {

    @Autowired
    RoomService roomService;

    @Autowired
    HotelService hotelService;

    @Test
    public void retrieveHotelRoomInfo() {
        try {
            roomService.retrieveHotelRoomInfo(1);
        }
        catch (Exception e){}
        //System.out.println(roomService.retrieveHotelRoomInfo(1));
    }

    @Test
    public void insertRoomInfo() {
        HotelRoom hotelRoom=new HotelRoom();
        hotelRoom.setHotelId(1);
        hotelRoom.setRoomType(RoomType.BigBed);
        hotelRoom.setPrice(199);
        hotelRoom.setCurNum(10);
        hotelRoom.setTotal(50);
        hotelRoom.setId(2);
        roomService.insertRoomInfo(hotelRoom);

    }

    @Test
    public void updateRoomInfo() {
        int temp_curnum=roomService.getRoomCurNum(1,"Family",399.0);
        roomService.updateRoomInfo(1,"Family",399.0,2);
        assertEquals(temp_curnum-2,roomService.getRoomCurNum(1,"Family",399.0));
    }

    @Test
    public void getRoomCurNum() {

        assertEquals(20, roomService.getRoomCurNum(1,"BigBed",199.0));

    }
}