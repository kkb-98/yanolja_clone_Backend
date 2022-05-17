package com.example.demo.src.accOwner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccOwner{
    private int accOwnerId;
    private String accOwnerName;
    private String businessName;
    private String businessAddr;
    private String businessEmail;
    private String businessPhone;
    private int businessNum;
}