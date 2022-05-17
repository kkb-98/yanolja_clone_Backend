package com.example.demo.src.reservationNonUser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetResNUMyInfoRes {
    private int roomId;
    private String bookerName;
    private String bookerPhone;
    private int reserveNIndex;
    private String category;
    private String checkInDate;
    private String checkOutDate;
    private String accImageUrl;
    private String accName;
    private String roomPick;
    private String reservelStatus;
    private String vehicle;
}
