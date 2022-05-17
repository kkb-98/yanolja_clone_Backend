package com.example.demo.src.accommodation;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.accommodation.model.*;
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

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public  class AccommodationProvider  {

    private final AccommodationDao accommodationDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AccommodationProvider(AccommodationDao accommodationDao) {
        this.accommodationDao = accommodationDao;
    }

    /**
     * 야놀자 API 2번
     * 지역별 숙소 조회 Provider
     * */
    public GetAccommodationByLocationRes getAccByLocation(String location) throws BaseException{
        try{
            GetAccommodationByLocationRes getAccommodationByLocationRes = accommodationDao.getAccByLocation(location);
            return getAccommodationByLocationRes;
        } catch (Exception exception){
            throw new BaseException(FAILED_TO_SEARCH_ACCBYLOCATION);
        }
    }

    /**
     * 야놀자 API 6번
     * 이름으로 숙소 조회 Provider
     * */
    public GetAccommodationByLocationRes getAccByAccName(String accName) throws BaseException{
        try{
            GetAccommodationByLocationRes getAccommodationByLocationRes = accommodationDao.getAccByAccName(accName);
            return getAccommodationByLocationRes;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_ACCBYACCNAME);
        }
    }

    /**
     * 야놀자 API 7번
     * 등급으로 숙소 조회 Provider
     * */
    public List<AccommodationList> getAccByTag(String tag) throws BaseException{
        try{
            List<AccommodationList> accommodation = accommodationDao.getAccByTag(tag);
            return accommodation;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_ACCBYRANK);
        }
    }

    /**
     * 야놀자 API 8번
     * 할인하는 숙소 리스트만 조회 Provider
     * */
    public List<AccommodationList> getAccByDiscountRate() throws BaseException{
        try{
            List<AccommodationList> accommodation = accommodationDao.getAccByDiscountRate();
            return accommodation;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_ACCBYDISCOUNT);
        }
    }

    //getAccByTop
    /**
     * 야놀자 API 9번
     * 예약이 많은 숙소 리스트만 조회 Provider
     * */
    public List<AccommodationTop> getAccByTop() throws BaseException{
        try{
            List<AccommodationTop> accommodationTops = accommodationDao.getAccByTop();
            return accommodationTops;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_ACCBYTOP);
        }
    }


//    getAccInfo
    /**
     * 야놀자 API 10번
     *숙소 상세정보 조회 Provider
     * */
    public List<Accommodation> getAccInfo(int accId) throws BaseException{
        try{
            List<Accommodation> accommodation = accommodationDao.getAccInfo(accId);
            return accommodation;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_ACCBYINFO);
        }
    }
}