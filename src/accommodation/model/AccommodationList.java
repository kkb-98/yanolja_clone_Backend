package com.example.demo.src.accommodation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccommodationList {
    private int accId;
    private String category;
    private String accName;
    private String accInfo;
    private String infoUse;
    private String tag;
    private int yCare;
    private int daesilMax;
    private String checkInPossibleTime;
    private int maxPersonCnt;
    private int daesilCost;
    private int checkInCost;
    private int discountRate;
    private int emptyRoomCnt;
    private String roomImageUrl;
    private int reviewCount;
    private float star;
    private String location;
}
