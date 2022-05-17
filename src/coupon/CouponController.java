package com.example.demo.src.coupon;
import com.example.demo.src.coupon.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/app/coupon")
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class CouponController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CouponProvider couponProvider;
    @Autowired
    private final JwtService jwtService;
    public CouponController(CouponProvider couponProvider, JwtService jwtService) {
        this.couponProvider = couponProvider;
        this.jwtService = jwtService;
    }

    /**
     * 야놀자 API 20번
     * 찜 목록 조회 API
     * [GET] /coupon/:userId
     *
     * @return BaseResponse<GetMyCouponInfoRes>
     */
    @ResponseBody
    @GetMapping("/couponInfo")
    public BaseResponse<GetMyCouponInfoRes> getMyCouponInfo(@RequestParam("userId") int userId,
                                                            @RequestParam("couponId") int couponId){
        try{
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetMyCouponInfoRes getMyCouponInfoRes = couponProvider.getMyCouponInfo(userId,couponId);
            return new BaseResponse<>(getMyCouponInfoRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
