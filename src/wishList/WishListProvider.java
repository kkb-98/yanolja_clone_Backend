package com.example.demo.src.wishList;

import com.example.demo.config.BaseException;
import com.example.demo.src.wishList.model.GetWishListRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class WishListProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WishListDao wishListDao;

    @Autowired
    public WishListProvider(WishListDao wishListDao) {
        this.wishListDao = wishListDao;
    }

    public List<GetWishListRes> getWishList(int userId)throws BaseException{
        try{
            List<GetWishListRes> getWishListRes = wishListDao.getWistList(userId);
            return getWishListRes;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(RESPONSE_ERROR);
        }
    }
}
