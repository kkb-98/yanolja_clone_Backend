package com.example.demo.src.accommodation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Accommodation{
    private int accId;
    private int roomId;
    private String roomName;
    private String roomSubTitle;
    private int daesilMax;
    private String checkInPossibleTime;
    private String checkOutPossibleTime;
    private int dePersonCnt;
    private int maxPersonCnt;
    private int daesilCost;
    private int checkInCost;
    private int discountRate;
    private int emptyRoomCnt;
    private String accName;
    private String accPhone;
    private String accInfo;
    private String infoUse;
    private String bookNotice;
    private String infoPickup;
    private String directions;
    private String accImageUrl;
    private String roomImageUrl;
    private float star;
    private int reviewCount;
    private String businessAddr;
    private String tag;
    private boolean yCare;  // int 도 고려해보자
    private boolean parking;
    private boolean breakfast;
    private boolean wifi;
    private boolean noSmokingRoom;
    private boolean h24Desk;
}