package com.example.demo.src.reservation;

import com.example.demo.src.reservation.model.*;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public int createReservation(PostResInfoReq postResInfoReq){
        String createResQuery = "insert into Reservation (accId, roomId, userId,\n" +
                "                         vehicle, checkInDate, checkOutDate,\n" +
                "                         bookerName, visitorName, visitorPhone,\n" +
                "                         payment, reservelStatus) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createResParams = new Object[]{postResInfoReq.getAccId(),
        postResInfoReq.getRoomId(), postResInfoReq.getUserId(), postResInfoReq.getVehicle(),
                postResInfoReq.getCheckInDate(), postResInfoReq.getCheckOutDate(), postResInfoReq.getBookerName(),
                postResInfoReq.getVisitorName(), postResInfoReq.getVisitorPhone(), postResInfoReq.getPayment(),
                postResInfoReq.getReservalStatus()};
        this.jdbcTemplate.update(createResQuery,createResParams);
        String lastreserveIndexQuery = "select last_insert_id() from Reservation limit 1";
        return this.jdbcTemplate.queryForObject(lastreserveIndexQuery,int.class);

    }





    public int checkReservation(int accId, int roomId, int userId){
        String checkReservationQuery = "select exists(select reserveIndex from Reservation where accId =? and roomId = ? and userId = ?)";
        int checkAccIdParams = accId;
        int checkRoomIdParams = roomId;
        int checkUserIdParams = userId;
        return this.jdbcTemplate.queryForObject(checkReservationQuery,
                int.class,checkAccIdParams,checkRoomIdParams,checkUserIdParams);
    }





    public List<GetResMyInfoRes> getResMyInfo(int userId,String checkOutDate){
        String getResMyInfoQuery = "select userId, reserveIndex, category, checkInDate, checkOutDate,\n" +
                "       accImageUrl, accName, roomPick, reservelStatus, vehicle\n" +
                "from Reservation\n" +
                "join Accommodation A on Reservation.accId = A.accId\n" +
                "join RoomInfo RI on Reservation.roomId = RI.roomId\n" +
                "join AccImage AI on RI.accId = AI.accId\n" +
                "where userId = ?\n" +
                "ANd checkOutDate < ?";
        int getResUserIdParams = userId;
        String getRescheckOutDateParams = checkOutDate;
        return this.jdbcTemplate.query(getResMyInfoQuery,
                (rs,rowNum) -> new GetResMyInfoRes(
                        rs.getInt("userId"),
                        rs.getInt("reserveIndex"),
                        rs.getString("category"),
                        rs.getString("checkInDate"),
                        rs.getString("checkOutDate"),
                        rs.getString("accImageUrl"),
                        rs.getString("accName"),
                        rs.getString("roomPick"),
                        rs.getString("reservelStatus"),
                        rs.getString("vehicle")),
                getResUserIdParams,getRescheckOutDateParams);
    }
}
