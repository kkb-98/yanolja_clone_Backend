package com.example.demo.src.basket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBasketRes {

    private int userId;
    private String tag;
    private String accName;
    private String category;
    private String businessAddr;
    private String roomName;
    private String roomSubTitle;
    private String checkInPossibleTime;
    private String checkOutPossibleTime;
    private int dePersonCnt;
    private int maxPersonCnt;
    private int roomPick;
    private int daesilMax;
    private int daesilCost;
    private int checkInCost;
    private int emptyRoomCnt;
}
