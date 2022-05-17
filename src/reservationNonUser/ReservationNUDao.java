package com.example.demo.src.reservationNonUser;

import com.example.demo.src.reservationNonUser.model.GetResNUMyInfoRes;
import com.example.demo.src.reservationNonUser.model.PostResNUInfoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ReservationNUDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int createReservationByNonUser(PostResNUInfoReq postResNUInfoReq){
        String createResQuery ="insert into ReservationNonUser (accId, roomId, vehicle, checkInDate, checkOutDate, bookerName,\n" +
                " bookerPhone,visitorName, visitorPhone,\n" +
                "payment, reservelStatus) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createResParams = new Object[]{postResNUInfoReq.getAccId(),
                postResNUInfoReq.getRoomId(),postResNUInfoReq.getVehicle(),
                postResNUInfoReq.getCheckInDate(), postResNUInfoReq.getCheckOutDate(),
                postResNUInfoReq.getBookerName(), postResNUInfoReq.getBookerPhone()
                ,postResNUInfoReq.getVisitorName(), postResNUInfoReq.getVisitorPhone(),
                postResNUInfoReq.getPayment(),postResNUInfoReq.getReservelStatus()};

        this.jdbcTemplate.update(createResQuery,createResParams);
        String lastreserveNIndex = "select last_insert_id() from ReservationNonUser";
        return this.jdbcTemplate.queryForObject(lastreserveNIndex,int.class);
    }


    public GetResNUMyInfoRes getNonUserResMyInfo(String bookerName,String bookerPhone,int reservaNIndex){
        String getResMyInfoQuery = "select ReservationNonUser.roomId as roomId, bookerName, bookerPhone, reserveNIndex, category, checkInDate, checkOutDate,\n" +
                "       accImageUrl, accName, roomPick, ReservationNonUser.reservelStatus, vehicle  from ReservationNonUser\n" +
                "join Accommodation A on ReservationNonUser.accId = A.accId\n" +
                "join RoomInfo RI on ReservationNonUser.roomId = RI.roomId\n" +
                "join AccImage AI on RI.accId = AI.accId\n" +
                "where bookerName = ?\n" +
                "and bookerPhone =?\n" +
                "and reserveNIndex =?";
        String getResBookerName = bookerName;
        String getBookerPhone = bookerPhone;
        int getReservaNIdex = reservaNIndex;
        return this.jdbcTemplate.queryForObject(getResMyInfoQuery,
                (rs, rowNum) -> new GetResNUMyInfoRes(
                        rs.getInt("roomId"),
                        rs.getString("bookerName"),
                        rs.getString("bookerPhone"),
                        rs.getInt("reserveNIndex"),
                        rs.getString("category"),
                        rs.getString("checkInDate"),
                        rs.getString("checkOutDate"),
                        rs.getString("accImageUrl"),
                        rs.getString("accName"),
                        rs.getString("roomPick"),
                        rs.getString("reservelStatus"),
                        rs.getString("vehicle")),
                getResBookerName,getBookerPhone,getReservaNIdex);
    }
}



