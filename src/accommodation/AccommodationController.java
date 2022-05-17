package com.example.demo.src.accommodation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.accommodation.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/accommodations")
public class AccommodationController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AccommodationProvider accommodationProvider;
    @Autowired
    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationProvider accommodationProvider, AccommodationService accommodationService) {
        this.accommodationProvider = accommodationProvider;
        this.accommodationService = accommodationService;
    }

    /**
     * 야놀자 API 2번
     * 지역별 숙소 조회 API
     * [GET] /accommodations/:location
     * @return BaseResponse<GetAccommodationRes>
     * */
    @ResponseBody
    @GetMapping("/{location}")
    public BaseResponse<GetAccommodationByLocationRes> getAccByLocation(@PathVariable("location") String location){
        try{
            GetAccommodationByLocationRes getAccommodationByLocationRes = accommodationProvider.getAccByLocation(location); //provider 생성해야함
            return new BaseResponse<>(getAccommodationByLocationRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 6번
     * 이름으로 숙소 조회 API
     * [GET] /accommodations/accNames/:accName
     * @return BaseResponse<GetAccommodationRes>
     * */
    @ResponseBody
    @GetMapping("/accNames/{accName}")
    public BaseResponse<GetAccommodationByLocationRes> getAccByAccName(@PathVariable("accName") String accName){
        try{
            GetAccommodationByLocationRes getAccommodationByLocationRes = accommodationProvider.getAccByAccName(accName); //provider 생성해야함
            return new BaseResponse<>(getAccommodationByLocationRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 7번
     * 등급으로 숙소리스트 조회 API  ///accommodations/tags/:tag
     * [GET] /accommodations? tag=
     * @return BaseResponse<List<AccommodationList>>
     * */
    @ResponseBody
    @GetMapping("") //[GET] kkb01.shop/app/accommodations
    public BaseResponse<List<AccommodationList>> getAccByTag(@RequestParam(required = false) String tag){
        try{
            List<AccommodationList> accommodation = accommodationProvider.getAccByTag(tag);
            return new BaseResponse<>(accommodation);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 8번
     * 할인하는 숙소 리스트만 조회 API  ///accommodations/discountRates/:discountRate
     * [GET] /accommodations? discountRate=
     * @return BaseResponse<List<AccommodationList>>
     * */
    @ResponseBody
    @GetMapping("/discountRates") //[GET] kkb01.shop/app/accommodations
    public BaseResponse<List<AccommodationList>> getAccByDiscountRate(){
        try{
            List<AccommodationList> accommodation = accommodationProvider.getAccByDiscountRate();
            return new BaseResponse<>(accommodation);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 9번
     * 예약이 많은 숙소 리스트만 조회 API  ///accommodations/discountRates/:discountRate
     * [GET] /accommodations? discountRate=
     * @return BaseResponse<List<AccommodationList>>
     * */
    @ResponseBody
    @GetMapping("/reserveCounts")
    public BaseResponse<List<AccommodationTop>> getAccByTop(){
        try{
            List<AccommodationTop> accommodationTops = accommodationProvider.getAccByTop();
            return new BaseResponse<>(accommodationTops);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 10번
     * 숙소 상세정보 조회 API
     * [GET] /accommodations/info/:accId
     * @return BaseResponse<List<Accommodation>>
     * */
    @ResponseBody
    @GetMapping("/info/{accId}")
    public BaseResponse<List<Accommodation>> getAccInfo(@PathVariable("accId") int accId){
        try{
            List<Accommodation> accommodation = accommodationProvider.getAccInfo(accId);
            return new BaseResponse<>(accommodation);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}