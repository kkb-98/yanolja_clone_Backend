package com.example.demo.src.accOwner;

import com.example.demo.src.accOwner.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccOwnerDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 야놀자 API 3번
     * 숙소 대표자 조회 Dao
     * */
    public AccOwner getAccOwner(int accOwnerId){
        String getAccOwnerQuery = "select * from AccOwner where accOwnerId = ?";
        int getAccOwnerParams = accOwnerId;
        return this.jdbcTemplate.queryForObject(getAccOwnerQuery,
                (rs,rowNow) -> new AccOwner(
                        rs.getInt("accOwnerId"),
                        rs.getString("accOwnerName"),
                        rs.getString("businessName"),
                        rs.getString("businessAddr"),
                        rs.getString("businessEmail"),
                        rs.getString("businessPhone"),
                        rs.getInt("businessNum")),
                getAccOwnerParams);
    }




}