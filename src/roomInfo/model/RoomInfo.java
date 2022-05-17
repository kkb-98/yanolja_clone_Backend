package com.example.demo.src.roomInfo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomInfo {

    private int roomId;
    private int accId;
    private String roomName;
    private String roomSubTitle;
    private String roomInfo;
    private int daesilMax;
    private String checkInPossibleTime;
    private String checkOutPossibleTime;
    private int dePersonCnt;
    private int maxPersonCnt;
    private int daesilCost;
    private int checkInCost;
    private int discountRate;
    private int emptyRoomCnt;
    private String roomBookNotice;
    private String roomImageUrl;
    private String businessAddr;
    private String businessPhone;
}
