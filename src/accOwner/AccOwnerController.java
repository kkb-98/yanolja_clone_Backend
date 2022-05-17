package com.example.demo.src.accOwner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.accOwner.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/app/accOwners")
public class AccOwnerController{
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AccOwnerProvider accOwnerProvider;

    @Autowired
    private final AccOwnerService accOwnerService;

    public AccOwnerController(AccOwnerProvider accOwnerProvider, AccOwnerService accOwnerService) {
        this.accOwnerProvider = accOwnerProvider;
        this.accOwnerService = accOwnerService;
    }

    /**
     * 야놀자 API 3번
     * 숙소 대표자 조회 API
     * [GET]
     * @return BaseResponse<AccOwner>
     * */
    @ResponseBody
    @GetMapping("/{accOwnerId}")
    public BaseResponse<AccOwner> getAccOwner(@PathVariable("accOwnerId") int accOwnerId){
        try{
            AccOwner accOwner = accOwnerProvider.getAccOwner(accOwnerId);
            return new BaseResponse<>(accOwner);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}