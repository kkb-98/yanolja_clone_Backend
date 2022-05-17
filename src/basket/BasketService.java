package com.example.demo.src.basket;

import com.example.demo.config.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.src.basket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BasketService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BasketDao basketDao;
    private final BasketProvider basketProvider;

    @Autowired
    public BasketService(BasketDao basketDao, BasketProvider basketProvider) {
        this.basketDao = basketDao;
        this.basketProvider = basketProvider;
    }





    public PostMyBasketRes createBasket(PostMyBasketReq postMyBasketReq)throws BaseException{
        try{
            int basketId = basketDao.createBasket(postMyBasketReq);
            return new PostMyBasketRes(basketId);
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }




    public void deleteBasket(DeleteBasketReq deleteBasketReq) throws BaseException{
        try{
            int result = basketDao.deleteBasket(deleteBasketReq);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_BASKET);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
