package com.example.demo.src.reservationNonUser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostResNUInfoReq {
    private int accId;
    private int roomId;
    private String vehicle;
    private String checkInDate;
    private String checkOutDate;
    private String bookerName;
    private String bookerPhone;
    private String visitorName;
    private String visitorPhone;
    private int payment;
    private String reservelStatus;
}
