package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种限时优惠策略
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if(coupon.getCouponType()!=4) return false;
        LocalDate checkIn= LocalDate.parse(orderVO.getCheckInDate());
        LocalDate checkOut=LocalDate.parse(orderVO.getCheckOutDate());
        LocalDate startDate=coupon.getStartTime().toLocalDate();
        LocalDate endDate=coupon.getEndTime().toLocalDate();
        if(checkIn.isAfter(startDate)&&endDate.isAfter(checkOut)) return true;
        return false;
    }
}
