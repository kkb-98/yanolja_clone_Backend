package com.example.demo.src.basket;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.basket.model.PostMyBasketReq;
import com.example.demo.src.basket.model.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/basket")
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class BasketController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BasketService basketService;
    @Autowired
    private final BasketProvider basketProvider;

    public BasketController(BasketService basketService, BasketProvider basketProvider) {
        this.basketService = basketService;
        this.basketProvider = basketProvider;
    }

    /**
     * 야놀자 API 17번
     * 장바구니 담기 API
     * [POST] /basket
     *
     * @return BaseResponse<PostMyBasketReq>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostMyBasketRes> createBasket(@RequestBody PostMyBasketReq postMyBasketReq){
        try{
            PostMyBasketRes postMyBasketRes = basketService.createBasket(postMyBasketReq);
            return new BaseResponse<>(postMyBasketRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 18번
     * 장바구니 취소(삭제) API
     * [DELETE] /basketCancel/:userId
     *
     * @return BaseResponse<DeleteWistListReq>
     */
    @ResponseBody
    @DeleteMapping("/basketCancel/{userId}")
    public BaseResponse<DeleteBasketReq> deleteBasket(@PathVariable("userId") int userId, @RequestBody Basket basket){
        try{
            if (userId != basket.getUserId()){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            DeleteBasketReq deleteBasketReq = new DeleteBasketReq(basket.getUserId(), basket.getRoomId());
            basketService.deleteBasket(deleteBasketReq);
            return new BaseResponse<>(deleteBasketReq);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 19번
     * 내 장바구니 조회 API
     * [DELETE] /basket/:userId
     *
     * @return BaseResponse<GetBasketRes>
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetBasketRes>> getMyBasket(@PathVariable("userId")int userId){
        try{
            List<GetBasketRes> getBasketRes = basketProvider.getMyBasket(userId);
            return new BaseResponse<>(getBasketRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
