package com.example.demo.src.roomInfo;

import com.example.demo.src.roomInfo.model.RoomInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/roomInfos")
public class RoomInfoController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final RoomInfoProvider roomInfoProvider;

//    @Autowired
//    private final RoomInfoService roomInfoService;

    public RoomInfoController(RoomInfoProvider roomInfoProvider) {
        this.roomInfoProvider = roomInfoProvider;
//        this.roomInfoService = roomInfoService;
    }

    /**
     * 야놀자 API 4번
     * 객실 상세 조회 API
     * [GET]
     * @return BaseResponse<RoomInfo>
     * */
    @ResponseBody
    @GetMapping("/{roomId}")
    public BaseResponse<RoomInfo> getRoomInfo(@PathVariable("roomId") int roomId){
        try{
            RoomInfo roomInfo = roomInfoProvider.getRoomInfo(roomId);
            return new BaseResponse<>(roomInfo);
        }catch (BaseException exception){
            exception.getStackTrace();
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
