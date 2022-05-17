package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.*;
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
public class ReviewProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;

    @Autowired

    public ReviewProvider(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public List<GetMyReviewRes> getMyReview(int userId) throws BaseException{
        try{
            List<GetMyReviewRes> getMyReviewRes = reviewDao.getMyReview(userId);
            return getMyReviewRes;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_MYREVIEW);
        }
    }
}
