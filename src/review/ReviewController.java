package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/reviews")
public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;

    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService) {
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
    }

    /**
     * 야놀자 API 16번
     * 내가 쓴 리뷰들 조회 API
     * [GET] /reviews/:userId
     *
     * @return BaseResponse<GetMyReviewRes>
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetMyReviewRes>> getMyReview(@PathVariable("userId")int userId){
        try{
            List<GetMyReviewRes> getMyReviewRes = reviewProvider.getMyReview(userId);
            return new BaseResponse<>(getMyReviewRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
