package com.example.demo.src.reservationNonUser;

import com.example.demo.src.reservationNonUser.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/reservations-by-nonusers")
public class ReservationNUController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReservationNUService reservationNUService;

    @Autowired
    private final ReservationNUProvider reservationNUProvider;

    public ReservationNUController(ReservationNUService reservationNUService, ReservationNUProvider reservationNUProvider) {
        this.reservationNUService = reservationNUService;
        this.reservationNUProvider = reservationNUProvider;
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostResNUInfoRes> createReservationByNonUser(@RequestBody PostResNUInfoReq postResNUInfoReq){
        if (postResNUInfoReq.getBookerName() == null) {
            return new BaseResponse<>(POST_RESERVATION_EMPTY_BOOKERNAME);
        }
        if (postResNUInfoReq.getBookerPhone() == null) {
            return new BaseResponse<>(POST_RESERVATION_EMPTY_VISITORPHONE);
        }
        if (postResNUInfoReq.getVisitorName() == null) {
            return new BaseResponse<>(POST_RESERVATION_EMPTY_VISITORNAME);
        }
        if (postResNUInfoReq.getVisitorPhone() == null) {
            return new BaseResponse<>(POST_RESERVATION_EMPTY_VISITORPHONE);
        }
        try{
            PostResNUInfoRes postResNUInfoRes = reservationNUService.createReservationByNonUser(postResNUInfoReq);
            return new BaseResponse<>(postResNUInfoRes);
        }catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/non-users-myresinfo")
    public BaseResponse<GetResNUMyInfoRes> getNonUserResMyInfo(@RequestParam("bookerName")String bookerName,
                                                               @RequestParam("bookerPhone")String bookerPhone,
                                                               @RequestParam("reserveNIndex")int reservaNIndex){
        try{
            GetResNUMyInfoRes getResNUMyInfoRes = reservationNUProvider.getNonUserResMyInfo(bookerName,bookerPhone,reservaNIndex);
            return new BaseResponse<>(getResNUMyInfoRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }




}
