package com.example.demo.src.basket;

import com.example.demo.config.BaseException;
import com.example.demo.src.basket.model.GetBasketRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class BasketProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BasketDao basketDao;

    @Autowired
    public BasketProvider(BasketDao basketDao) {
        this.basketDao = basketDao;
    }


    public List<GetBasketRes> getMyBasket(int userId)throws BaseException{
        try {
            List<GetBasketRes> getBasketRes = basketDao.getMyBasket(userId);
            return getBasketRes;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
