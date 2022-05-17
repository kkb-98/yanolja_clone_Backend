package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.src.coupon.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class CouponProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CouponDao couponDao;

    @Autowired
    public CouponProvider(CouponDao couponDao) {
        this.couponDao = couponDao;
    }




    public GetMyCouponInfoRes getMyCouponInfo(int userId,int couponId)throws BaseException{
        try{
            GetMyCouponInfoRes getMyCouponInfoRes = couponDao.getMyCouponInfo(userId,couponId);
            return getMyCouponInfoRes;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_COUPONINFO);
        }
    }
}
