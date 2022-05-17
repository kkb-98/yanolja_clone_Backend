package com.example.demo.src.wishList;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.wishList.model.DeleteWistListReq;
import com.example.demo.src.wishList.model.PostWishListReq;
import com.example.demo.src.wishList.model.PostWishListRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class WishListService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WishListDao wishListDao;
    private final WishListProvider wishListProvider;

    @Autowired
    public WishListService(WishListDao wishListDao, WishListProvider wishListProvider) {
        this.wishListDao = wishListDao;
        this.wishListProvider = wishListProvider;
    }



    public PostWishListRes createWish(PostWishListReq postWishListReq) throws BaseException {
        try{
            int wishId = wishListDao.createWish(postWishListReq);
            return new PostWishListRes(wishId);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }




    public void deleteWish(DeleteWistListReq deleteWistListReq) throws BaseException{
        try{
            int result = wishListDao.deleteWish(deleteWistListReq);
            System.out.println(result);
            if (result == 1){
                throw new BaseException(DELETE_FAIL_WISHLIST);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DELETE_FAIL_WISHLIST);
        }
    }
}

