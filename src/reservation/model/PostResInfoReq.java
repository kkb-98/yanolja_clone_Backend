package com.example.demo.src.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostResInfoReq {

    private int accId;
    private int roomId;
    private int userId;
    private String vehicle;
    private String checkInDate;
    private String checkOutDate;
    private String bookerName;
    private String visitorName;
    private String visitorPhone;
    private int payment;
    private String reservalStatus;
}
