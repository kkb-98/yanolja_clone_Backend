package com.example.demo.src.coupon;


import com.example.demo.src.coupon.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CouponDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetMyCouponInfoRes getMyCouponInfo(int userId,int couponId){
        String getMyCouponInfoQuery = "select c.couponId as couponId, U.userId as userId, couponImageUrl, couponName,\n" +
                "       cStartDate, cEndDate, couponInfo\n" +
                "from Coupon c\n" +
                "join CouponImage CI on c.couponId = CI.couponId\n" +
                "join User U on c.userId = U.userId\n" +
                "where U.userId = ?\n" +
                "and c.couponId =?";
        int getUserIdParams = userId;
        int getCouponParams = couponId;
        return this.jdbcTemplate.queryForObject(getMyCouponInfoQuery,
                (rs, rowNum) -> new GetMyCouponInfoRes(
                        rs.getInt("couponId"),
                        rs.getInt("userId"),
                        rs.getString("couponImageUrl"),
                        rs.getString("couponName"),
                        rs.getString("cStartDate"),
                        rs.getString("cEndDate"),
                        rs.getString("couponInfo")),
                getUserIdParams,getCouponParams);
    }
}
