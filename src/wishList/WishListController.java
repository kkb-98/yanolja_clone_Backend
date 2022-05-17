package com.example.demo.src.wishList;

import com.example.demo.src.wishList.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/app/wishList")
public class WishListController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final WishListService wishListService;
    @Autowired
    private final WishListProvider wishListProvider;
    @Autowired
    private final JwtService jwtService;
    public WishListController(WishListService wishListService, WishListProvider wishListProvider, JwtService jwtService) {
        this.wishListService = wishListService;
        this.wishListProvider = wishListProvider;
        this.jwtService = jwtService;
    }

    /**
     * 야놀자 API 13번
     * 숙소 찜하기 API
     * [POST] /wishList
     *
     * @return BaseResponse<PostWishListReq>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostWishListRes> createWish(@RequestBody PostWishListReq postWishListReq) {

        try {
            int userIdByJwt = jwtService.getUserId();
            if (postWishListReq.getUserId() != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostWishListRes postWishListRes = wishListService.createWish(postWishListReq);
            return new BaseResponse<>(postWishListRes);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 야놀자 API 14번
     * 찜 취소(삭제) API
     * [DELETE] /wishListCancel/:userId
     *
     * @return BaseResponse<DeleteWistListReq>
     */
    @ResponseBody
    @DeleteMapping("/wishListCancel/{userId}")
    public BaseResponse<DeleteWistListReq> deleteWish(@PathVariable("userId")int userId, @RequestBody WishList wishList){

        try{
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if (userId != wishList.getUserId()){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            DeleteWistListReq deleteWistListReq = new DeleteWistListReq(wishList.getUserId(),wishList.getAccId());
            wishListService.deleteWish(deleteWistListReq);

            return new BaseResponse<>(deleteWistListReq);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 야놀자 API 15번
     * 찜 목록 조회 API
     * [GET] /wishList/:userId
     *
     * @return BaseResponse<GetWishListRes>
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetWishListRes>> getWistList(@PathVariable("userId")int userId){
        try{
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetWishListRes> getWishListRes = wishListProvider.getWishList(userId);
            return new BaseResponse<>(getWishListRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
