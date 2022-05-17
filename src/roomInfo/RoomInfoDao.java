package com.example.demo.src.roomInfo;

import com.example.demo.src.roomInfo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class RoomInfoDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 야놀자 API 4번
     * 객실 상세 조회 Dao
     * */
    public RoomInfo getRoomInfo(int roomId){
        String getRoomInfoQuery = "select RI.roomId as roomId, A.accId as accId, roomName, roomSubTitle,\n" +
                "       businessAddr, businessPhone, roomInfo, dePersonCnt,\n" +
                "       maxPersonCnt, checkInPossibleTime, checkOutPossibleTime,\n" +
                "       daesilMax, daesilCost, checkInCost,emptyRoomCnt, discountRate, roomBookNotice,\n" +
                "       roomImageUrl\n" +
                "from RoomInfo\n" +
                "join RoomImage RI on RoomInfo.roomId = RI.roomId\n" +
                "join Accommodation A on A.accId = RoomInfo.accId\n" +
                "join AccOwner AO on AO.accOwnerId = A.accOwnerId\n" +
                "where RI.roomId = ?";
        int getRoomInfoParams = roomId;
        return this.jdbcTemplate.queryForObject(getRoomInfoQuery,
                (rs,rowNow) -> new RoomInfo(
                        rs.getInt("roomId"),
                        rs.getInt("accId"),
                        rs.getString("roomName"),
                        rs.getString("roomSubTitle"),
                        rs.getString("roomInfo"),
                        rs.getInt("daesilMax"),
                        rs.getString("checkInPossibleTime"),
                        rs.getString("checkOutPossibleTime"),
                        rs.getInt("dePersonCnt"),
                        rs.getInt("maxPersonCnt"),
                        rs.getInt("daesilCost"),
                        rs.getInt("checkInCost"),
                        rs.getInt("discountRate"),
                        rs.getInt("emptyRoomCnt"),
                        rs.getString("roomBookNotice"),
                        rs.getString("roomImageUrl"),
                        rs.getString("businessAddr"),
                        rs.getString("businessPhone")),
                getRoomInfoParams);
    }


}
