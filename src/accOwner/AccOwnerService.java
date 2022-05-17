package com.example.demo.src.accOwner;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.accOwner.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class AccOwnerService{
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AccOwnerDao accOwnerDao;
    private final AccOwnerProvider accOwnerProvider;

    @Autowired

    public AccOwnerService(AccOwnerDao accOwnerDao, AccOwnerProvider accOwnerProvider) {
        this.accOwnerDao = accOwnerDao;
        this.accOwnerProvider = accOwnerProvider;
    }
}