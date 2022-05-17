package com.example.demo.src.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;
    private final ReviewProvider reviewProvider;

    public ReviewService(ReviewDao reviewDao, ReviewProvider reviewProvider) {
        this.reviewDao = reviewDao;
        this.reviewProvider = reviewProvider;
    }
}
