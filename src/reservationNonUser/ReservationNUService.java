package com.example.demo.src.reservationNonUser;

import com.example.demo.config.BaseException;
import com.example.demo.src.reservationNonUser.model.PostResNUInfoReq;
import com.example.demo.src.reservationNonUser.model.PostResNUInfoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ReservationNUService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationNUDao reservationNUDao;
    private final ReservationNUProvider reservationNUProvider;

    @Autowired
    public ReservationNUService(ReservationNUDao reservationNUDao, ReservationNUProvider reservationNUProvider) {
        this.reservationNUDao = reservationNUDao;
        this.reservationNUProvider = reservationNUProvider;
    }

    //POST
    @Transactional(isolation = Isolation.READ_UNCOMMITTED,rollbackFor = Exception.class)
    public PostResNUInfoRes createReservationByNonUser(PostResNUInfoReq postResNUInfoReq) throws BaseException {

        try {
            int reserveNIndex = reservationNUDao.createReservationByNonUser(postResNUInfoReq);
            return new PostResNUInfoRes(reserveNIndex);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
