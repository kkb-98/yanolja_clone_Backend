package com.example.demo.src.basket;

import com.example.demo.src.basket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BasketDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    public int createBasket(PostMyBasketReq postMyBasketReq){
        String createBasketQuery = "insert into Basket (userId, accId, roomId, accName, roomName)\n" +
                "VALUES (?,?,?,?,?)";
        Object[] createBasketParams = new Object[]{postMyBasketReq.getUserId(), postMyBasketReq.getAccId(),
        postMyBasketReq.getRoomId(),postMyBasketReq.getAccName(),postMyBasketReq.getRoomName()};
        this.jdbcTemplate.update(createBasketQuery,createBasketParams);
        String lastBasketQuery = "select last_insert_id() from Basket limit 1";
        return this.jdbcTemplate.queryForObject(lastBasketQuery,int.class);
    }




    public int deleteBasket(DeleteBasketReq deleteBasketReq){
        String deleteBasketQuery = "delete from Basket\n" +
                "where userId =?\n" +
                "and roomId =?";
        Object[] deleteBasketParams = new Object[]{deleteBasketReq.getUserId(),deleteBasketReq.getRoomId()};
        return this.jdbcTemplate.update(deleteBasketQuery,deleteBasketParams);
    }




    public List<GetBasketRes> getMyBasket(int userId){
        String getMyBasketQuery = "select Basket.userId, tag,A.accName as accName ,category,businessAddr,RI.roomName as roomName, roomSubTitle,\n" +
                "       checkInPossibleTime,checkOutPossibleTime,dePersonCnt,maxPersonCnt,\n" +
                "       roomPick,daesilMax,daesilCost,checkInCost,emptyRoomCnt\n" +
                "from Basket\n" +
                "join Accommodation A on Basket.accId = A.accId\n" +
                "join RoomInfo RI on Basket.roomId = RI.roomId\n" +
                "join AccImage AI on RI.accId = AI.accId\n" +
                "join AccOwner AO on A.accOwnerId = AO.accOwnerId\n" +
                "join Tag T on A.accId = T.accId\n" +
                "join User U on Basket.userId = U.userId\n" +
                "where Basket.userId = ?";
        int getUserIdParams = userId;
        return this.jdbcTemplate.query(getMyBasketQuery,
                (rs,rowNum) -> new GetBasketRes(
                        rs.getInt("userId"),
                        rs.getString("tag"),
                        rs.getString("accName"),
                        rs.getString("category"),
                        rs.getString("businessAddr"),
                        rs.getString("roomName"),
                        rs.getString("roomSubTitle"),
                        rs.getString("checkInPossibleTime"),
                        rs.getString("checkOutPossibleTime"),
                        rs.getInt("dePersonCnt"),
                        rs.getInt("maxPersonCnt"),
                        rs.getInt("roomPick"),
                        rs.getInt("daesilMax"),
                        rs.getInt("daesilCost"),
                        rs.getInt("checkInCost"),
                        rs.getInt("emptyRoomCnt")),
        getUserIdParams);
    }
}
