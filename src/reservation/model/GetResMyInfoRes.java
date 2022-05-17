package com.example.demo.src.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetResMyInfoRes {

    private int userId;
    private int reserveIndex;
    private String category;
    private String checkInDate;
    private String checkOutDate;
    private String accImageUrl;
    private String accName;
    private String roomPick;
    private String reservalStatus;
    private String vehicle;
}
