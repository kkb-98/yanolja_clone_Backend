package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMyReviewRes {
    private int userId;
    private String nickName;
    private String accName;
    private float star;
    private String reviewText;
    private String reviewImageUrl;
    private String createdAt;
}
