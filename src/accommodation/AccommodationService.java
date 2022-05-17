package com.example.demo.src.accommodation;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.accommodation.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class AccommodationService{
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AccommodationDao accommodationDao;
    private final AccommodationProvider accommodationProvider;

    @Autowired
    public AccommodationService(AccommodationDao accommodationDao, AccommodationProvider accommodationProvider) {
        this.accommodationDao = accommodationDao;
        this.accommodationProvider = accommodationProvider;
    }
}