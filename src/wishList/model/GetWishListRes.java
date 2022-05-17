package com.example.demo.src.wishList.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetWishListRes {
    private String checkInPossibleTime;
    private String checkOutPossibleTime;
    private int dePersonCnt;
    private String accImageUrl;
    private String tag;
    private boolean yCare;
    private String businessAddr;
    private String accName;
    private float star;
    private int reviewCount;
    private int daesilMax;
    private int daesilCost;
    private int checkInCost;
}
