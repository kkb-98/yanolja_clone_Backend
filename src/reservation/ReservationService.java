package com.example.demo.src.reservation;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.reservation.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ReservationService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationDao reservationDao;
    private final ReservationProvider reservationProvider;

    @Autowired
    public ReservationService(ReservationDao reservationDao, ReservationProvider reservationProvider) {
        this.reservationDao = reservationDao;
        this.reservationProvider = reservationProvider;
    }

    //POST
    @Transactional(isolation = Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public PostResInfoRes createReservation(PostResInfoReq postResInfoReq) throws BaseException {
        //중복
        if(reservationProvider.checkReservation(postResInfoReq.getAccId(), postResInfoReq.getRoomId(),postResInfoReq.getUserId()) ==1){
            throw new BaseException(DUPLICATED_RESERVATION);
        }
        try{
            int reservalIndex = reservationDao.createReservation(postResInfoReq);
            return new PostResInfoRes(reservalIndex);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
