package com.example.demo.src.reservationNonUser;

import com.example.demo.config.BaseException;
import com.example.demo.src.reservationNonUser.model.GetResNUMyInfoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ReservationNUProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final ReservationNUDao reservationNUDao;


    @Autowired
    public ReservationNUProvider(ReservationNUDao reservationNUDao) {
        this.reservationNUDao = reservationNUDao;
    }


    @Transactional(rollbackFor = Exception.class)
    public GetResNUMyInfoRes getNonUserResMyInfo(String bookerName,String bookerPhone,int reservaNIndex) throws BaseException {
        try{
            GetResNUMyInfoRes getResNUMyInfoRes = reservationNUDao.getNonUserResMyInfo(bookerName,bookerPhone,reservaNIndex);
            return getResNUMyInfoRes;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_NONRESMYINFO);
        }
    }

}
