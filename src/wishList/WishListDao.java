package com.example.demo.src.wishList;
import com.example.demo.src.wishList.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WishListDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }






    public int createWish(PostWishListReq postWishListReq){
        String createWishQuery = "insert into WishList (userId, accId, accName) VALUES (?,?,?)";
        Object[] createWishParams = new Object[]{postWishListReq.getUserId(),
                postWishListReq.getAccId(), postWishListReq.getAccName()};
        this.jdbcTemplate.update(createWishQuery,createWishParams);
        String lastwishQuery = "select last_insert_id() from WishList limit 1";
        return this.jdbcTemplate.queryForObject(lastwishQuery,int.class);
    }






    public int deleteWish(DeleteWistListReq deleteWistListReq){
        String deleteWishQuery = "delete from WishList where userId =? and accId =?";
        Object[] deleteWishParams = new Object[]{deleteWistListReq.getUserId(), deleteWistListReq.getAccId()};
        this.jdbcTemplate.update(deleteWishQuery, deleteWishParams);
        int result =deleteWistListReq.getAccId();
        return this.jdbcTemplate.update(deleteWishQuery, deleteWishParams);
    }






    public List<GetWishListRes> getWistList(int userId){
        String getWishListQuery = "select checkInPossibleTime, checkOutPossibleTime, dePersonCnt, accImageUrl,\n" +
                "       tag,yCare,businessAddr,WishList.accName,star,reviewCount,daesilMax,daesilCost,\n" +
                "       checkInCost\n" +
                "from WishList\n" +
                "join User U on WishList.userId = U.userId\n" +
                "join Accommodation A on WishList.accId = A.accId\n" +
                "join RoomInfo RI on A.accId = RI.accId\n" +
                "join AccImage AI on RI.accId = AI.accId\n" +
                "join Tag T on A.accId = T.accId\n" +
                "join AccOwner AO on A.accOwnerId = AO.accOwnerId\n" +
                "join (select accId, round(((sum(kind) + sum(clean) + sum(convenience) + sum(fitment))/(count(*)*4)),1) as star,\n" +
                "                   count(*) as reviewCount\n" +
                "                   from ReviewGrade\n" +
                "                   join Review R on ReviewGrade.reviewgrade = R.grade\n" +
                "                   group by accId) as R2 on T.accId = R2.accId\n" +
                "where U.userId = ?\n" +
                "group by A.accId";
        int getUserIdParams = userId;
        return this.jdbcTemplate.query(getWishListQuery,
                (rs, rowNum) -> new GetWishListRes(
                        rs.getString("checkInPossibleTime"),
                        rs.getString("checkOutPossibleTime"),
                        rs.getInt("dePersonCnt"),
                        rs.getString("accImageUrl"),
                        rs.getString("tag"),
                        rs.getBoolean("yCare"),
                        rs.getString("businessAddr"),
                        rs.getString("accName"),
                        rs.getFloat("star"),
                        rs.getInt("reviewCount"),
                        rs.getInt("daesilMax"),
                        rs.getInt("daesilCost"),
                        rs.getInt("checkInCost")),
                getUserIdParams);
    }
}
