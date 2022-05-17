package com.example.demo.src.reservation;

import com.example.demo.src.accOwner.model.AccOwner;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.reservation.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/reservations")
public class ReservationController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReservationService reservationService;
    @Autowired
    private final ReservationProvider reservationProvider;
    @Autowired
    private final JwtService jwtService;

    public ReservationController(ReservationService reservationService, ReservationProvider reservationProvider, JwtService jwtService) {
        this.reservationService = reservationService;
        this.reservationProvider = reservationProvider;
        this.jwtService = jwtService;
    }

    /**
     * 야놀자 API 11번
     * 숙소 예약하기 API
     * [POST] /reservations
     *
     * @return BaseResponse<PostResInfoRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostResInfoRes> createReservation(@RequestBody PostResInfoReq postResInfoReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        try {
            int userIdByJwt = jwtService.getUserId();
            if (postResInfoReq.getUserId() != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if (postResInfoReq.getBookerName() == null) { //형식적 validation
                return new BaseResponse<>(POST_RESERVATION_EMPTY_BOOKERNAME);
            }
            if (postResInfoReq.getVisitorName() == null) { //형식적 validation
                return new BaseResponse<>(POST_RESERVATION_EMPTY_VISITORNAME);

                //이메일 정규표현
//        if(!isRegexEmail(postUserReq.getEmail())){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);

            }
            if (postResInfoReq.getVisitorPhone() == null) {
                return new BaseResponse<>(POST_RESERVATION_EMPTY_VISITORPHONE);
            }
        }catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            PostResInfoRes postResInfoRes = reservationService.createReservation(postResInfoReq);
            return new BaseResponse<>(postResInfoRes);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 12번
     * 나의 예약 내역 조회  API
     * [GET] /app/reservations/myResInfo/usersId? userId = & checkOutDate? checkOutDate=
     *
     * @return BaseResponse<GetResMyInfoRes>
     * Query String
     */
    @ResponseBody
    @GetMapping("/myResInfo")
    public BaseResponse<List<GetResMyInfoRes>> getResMyInfo(@RequestParam("userId") int userId,
                                                            @RequestParam("checkOutDate") String checkOutDate) {
        try {
            // Get Users
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetResMyInfoRes> getResMyInfoRes = reservationProvider.getResMyInfo(userId, checkOutDate);
            return new BaseResponse<>(getResMyInfoRes);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}