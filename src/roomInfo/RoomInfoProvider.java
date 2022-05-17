package com.example.demo.src.roomInfo;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.roomInfo.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.demo.config.BaseResponseStatus.*;
@Service
public class RoomInfoProvider {
    private final RoomInfoDao roomInfoDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired

    public RoomInfoProvider(RoomInfoDao roomInfoDao) {
        this.roomInfoDao = roomInfoDao;
    }

    /**
     * 야놀자 API 4번
     * 객실 상세 조회 Provider
     * */
    public RoomInfo getRoomInfo(int roomId) throws BaseException{
        try{
            RoomInfo roomInfo = roomInfoDao.getRoomInfo(roomId);
            return roomInfo;
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(FAILED_TO_SEARCH_ROOMINFO);
        }
    }
}
