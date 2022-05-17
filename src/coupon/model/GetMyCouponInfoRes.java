package com.example.demo.src.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMyCouponInfoRes {
    private int couponId;
    private int userId;
    private String couponImageUrl;
    private String couponName;
    private String cStartDate;
    private String cEndDate;
    private String couponInfo;
}
