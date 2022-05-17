package com.example.demo.src.basket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostMyBasketReq {

    private int userId;
    private int accId;
    private int roomId;
    private String accName;
    private String roomName;
}
