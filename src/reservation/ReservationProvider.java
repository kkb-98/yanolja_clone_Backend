package com.example.demo.src.reservation;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.reservation.model.*;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ReservationProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationProvider(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public int checkReservation(int accId, int roomId, int userId) throws BaseException{
        try{
            return reservationDao.checkReservation(accId, roomId, userId);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public List<GetResMyInfoRes> getResMyInfo(int userId,String checkOutDate) throws BaseException{
        try{
            List<GetResMyInfoRes> getResMyInfoRes = reservationDao.getResMyInfo(userId, checkOutDate);
            return getResMyInfoRes;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_RESMYINFO);
        }
    }
}
