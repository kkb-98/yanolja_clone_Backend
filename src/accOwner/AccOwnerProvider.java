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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class AccOwnerProvider {
    private final AccOwnerDao accOwnerDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AccOwnerProvider(AccOwnerDao accOwnerDao) {
        this.accOwnerDao = accOwnerDao;
    }

    /**
     * 야놀자 API 3번
     * 숙소 대표자 조회 Provider
     * */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public AccOwner getAccOwner(int accOwnerId) throws BaseException{
        try{
            AccOwner accOwner = accOwnerDao.getAccOwner(accOwnerId);
            return accOwner;
        } catch (Exception exception){
            throw new BaseException(FAILED_TO_SEARCH_ACCOWNER);
        }
    }
}