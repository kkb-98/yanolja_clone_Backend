package com.example.demo.src.accommodation;

import com.example.demo.src.accommodation.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccommodationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 야놀자 API 2번
     * 지역별 숙소 조회 Dao
     * */
    public GetAccommodationByLocationRes getAccByLocation(String location){

        String getAccommodationByLocationQuery = "select distinct *\n" +
                "from Accommodation acc\n" +
                "inner join Tag T on acc.accId = T.accId\n" +
                "inner join RoomInfo RI on acc.accId = RI.accId\n" +
                "inner join RoomImage R on RI.roomId = R.roomId\n" +
                "inner join (select accId, round(((sum(kind) + sum(clean) + sum(convenience) + sum(fitment))/(count(*)*4)),1) as star,\n" +
                "                   count(*) as reviewCount\n" +
                "from ReviewGrade\n" +
                "join Review R on ReviewGrade.reviewgrade = R.grade\n" +
                "group by accId) as R1 on T.accId = R1.accId\n" +
                "where location = ?\n" +
                "and checkInPossibleTime >= '2022-05-05 21:00:00'\n" +
                "OR checkOutPossibleTime <= '2022-05-05 21:00:00'\n" +
                "group by acc.accId";
        String getAccommodationByLocationParams = location;
        return this.jdbcTemplate.queryForObject(getAccommodationByLocationQuery,
                (rs,rowNow) -> new GetAccommodationByLocationRes(
                        rs.getInt("accId"),
                        rs.getString("category"),
                        rs.getString("accName"),
                        rs.getString("accInfo"),
                        rs.getString("infoUse"),
                        rs.getString("tag"),
                        rs.getInt("yCare"),
                        rs.getInt("daesilMax"),
                        rs.getString("checkInPossibleTime"),
                        rs.getInt("maxPersonCnt"),
                        rs.getInt("daesilCost"),
                        rs.getInt("checkInCost"),
                        rs.getInt("discountRate"),
                        rs.getInt("emptyRoomCnt"),
                        rs.getString("roomImageUrl"),
                        rs.getInt("reviewCount"),
                        rs.getFloat("star"),
                        rs.getString("location")),
                getAccommodationByLocationParams);
    }

    /**
     * 야놀자 API 6번
     * 이름으로 숙소 조회 Dao
     * */
    public GetAccommodationByLocationRes getAccByAccName(String accName){

        String getAccommodationByLocationQuery = "select distinct *\n" +
                "from Accommodation acc\n" +
                "inner join Tag T on acc.accId = T.accId\n" +
                "inner join RoomInfo RI on acc.accId = RI.accId\n" +
                "inner join RoomImage R on RI.roomId = R.roomId\n" +
                "inner join (select accId, round(((sum(kind) + sum(clean) + sum(convenience) + sum(fitment))/(count(*)*4)),1) as star,\n" +
                "                   count(*) as reviewCount\n" +
                "from ReviewGrade\n" +
                "join Review R on ReviewGrade.reviewgrade = R.grade\n" +
                "group by accId) as R1 on T.accId = R1.accId\n" +
                "where accName = ?\n" +
                "and checkInPossibleTime >= '2022-05-05 21:00:00'\n" +
                "OR checkOutPossibleTime <= '2022-05-05 21:00:00'\n" +
                "group by acc.accId";
        String getAccommodationByLocationParams = accName;
        return this.jdbcTemplate.queryForObject(getAccommodationByLocationQuery,
                (rs,rowNow) -> new GetAccommodationByLocationRes(
                        rs.getInt("accId"),
                        rs.getString("category"),
                        rs.getString("accName"),
                        rs.getString("accInfo"),
                        rs.getString("infoUse"),
                        rs.getString("tag"),
                        rs.getInt("yCare"),
                        rs.getInt("daesilMax"),
                        rs.getString("checkInPossibleTime"),
                        rs.getInt("maxPersonCnt"),
                        rs.getInt("daesilCost"),
                        rs.getInt("checkInCost"),
                        rs.getInt("discountRate"),
                        rs.getInt("emptyRoomCnt"),
                        rs.getString("roomImageUrl"),
                        rs.getInt("reviewCount"),
                        rs.getFloat("star"),
                        rs.getString("location")),
                getAccommodationByLocationParams);
    }

    /**
     * 야놀자 API 7번
     * 등급으로 숙소 조회 Dao
    */
    public List<AccommodationList> getAccByTag(String tag){

        String getAccommodationByTagQuery = "select distinct *\n" +
                "from Accommodation acc\n" +
                "inner join Tag T on acc.accId = T.accId\n" +
                "inner join RoomInfo RI on acc.accId = RI.accId\n" +
                "inner join RoomImage R on RI.roomId = R.roomId\n" +
                "inner join (select accId, round(((sum(kind) + sum(clean) + sum(convenience) + sum(fitment))/(count(*)*4)),1) as star,\n" +
                "                   count(*) as reviewCount\n" +
                "from ReviewGrade\n" +
                "join Review R on ReviewGrade.reviewgrade = R.grade\n" +
                "group by accId) as R1 on T.accId = R1.accId\n" +
                "where tag = ?\n" +
                "and checkInPossibleTime >= '2022-05-05 21:00:00'\n" +
                "OR checkOutPossibleTime <= '2022-05-05 21:00:00'\n" +
                "group by acc.accId";
        String getAccommodationByLocationParams = tag;
        return this.jdbcTemplate.query(getAccommodationByTagQuery,
                (rs,rowNow) -> new AccommodationList(
                        rs.getInt("accId"),
                        rs.getString("category"),
                        rs.getString("accName"),
                        rs.getString("accInfo"),
                        rs.getString("infoUse"),
                        rs.getString("tag"),
                        rs.getInt("yCare"),
                        rs.getInt("daesilMax"),
                        rs.getString("checkInPossibleTime"),
                        rs.getInt("maxPersonCnt"),
                        rs.getInt("daesilCost"),
                        rs.getInt("checkInCost"),
                        rs.getInt("discountRate"),
                        rs.getInt("emptyRoomCnt"),
                        rs.getString("roomImageUrl"),
                        rs.getInt("reviewCount"),
                        rs.getFloat("star"),
                        rs.getString("location")),
                getAccommodationByLocationParams);
    }

    /**
     * 야놀자 API 8번
     * 할인하는 숙소 리스트만 조회 Dao
     * */
    public List<AccommodationList> getAccByDiscountRate(){

        String getAccommodationByTagQuery = "select distinct *\n" +
                "from Accommodation acc\n" +
                "inner join Tag T on acc.accId = T.accId\n" +
                "inner join RoomInfo RI on acc.accId = RI.accId\n" +
                "inner join RoomImage R on RI.roomId = R.roomId\n" +
                "inner join (select accId, round(((sum(kind) + sum(clean) + sum(convenience) + sum(fitment))/(count(*)*4)),1) as star,\n" +
                "                   count(*) as reviewCount\n" +
                "from ReviewGrade\n" +
                "join Review R on ReviewGrade.reviewgrade = R.grade\n" +
                "group by accId) as R1 on T.accId = R1.accId\n" +
                "where discountRate > 1\n" +
                "and checkInPossibleTime >= '2022-05-05 21:00:00'\n" +
                "OR checkOutPossibleTime <= '2022-05-05 21:00:00'\n" +
                "group by acc.accId";

        return this.jdbcTemplate.query(getAccommodationByTagQuery,
                (rs,rowNow) -> new AccommodationList(
                        rs.getInt("accId"),
                        rs.getString("category"),
                        rs.getString("accName"),
                        rs.getString("accInfo"),
                        rs.getString("infoUse"),
                        rs.getString("tag"),
                        rs.getInt("yCare"),
                        rs.getInt("daesilMax"),
                        rs.getString("checkInPossibleTime"),
                        rs.getInt("maxPersonCnt"),
                        rs.getInt("daesilCost"),
                        rs.getInt("checkInCost"),
                        rs.getInt("discountRate"),
                        rs.getInt("emptyRoomCnt"),
                        rs.getString("roomImageUrl"),
                        rs.getInt("reviewCount"),
                        rs.getFloat("star"),
                        rs.getString("location")));
    }

    /**
     * 야놀자 API 9번
     * 예약이 많은 숙소 리스트만 조회 Dao
     * */
    public List<AccommodationTop> getAccByTop(){
        String getAccByTopQuery = "select A.accName as accName, A.category as category, location, count(Reservation.accId) as \"reserveCount\"\n" +
                "    from Reservation\n" +
                "    join RoomInfo RI on Reservation.roomId = RI.roomId\n" +
                "    join Accommodation A on Reservation.accId = A.accId\n" +
                "    group by A.accName\n" +
                "    order by count(A.accId) desc";
        return this.jdbcTemplate.query(getAccByTopQuery,
                (rs,rowNow) -> new AccommodationTop(
                        rs.getString("accName"),
                        rs.getString("category"),
                        rs.getString("location"),
                        rs.getInt("reserveCount")));
    }

    /**
     * 야놀자 API 10번
     * 숙소 상세정보 조회 Dao
     * */
    public List<Accommodation> getAccInfo(int accId){
        String getAccInfoQuery = "select  A.accId as accId, RoomInfo.roomId as roomId,roomName,roomSubTitle,daesilMax,checkInPossibleTime,checkOutPossibleTime,\n" +
                "    dePersonCnt,maxPersonCnt,daesilCost,checkInCost,discountRate,emptyRoomCnt,accName,accPhone,\n" +
                "    accInfo,infoUse,bookNotice,infoPickup,directions,accImageUrl,roomImageUrl, star, reviewCount,\n" +
                "    businessAddr, tag, yCare, parking,breakfast,wifi,noSmokingRoom,h24Desk\n" +
                "    from RoomInfo\n" +
                "    join Accommodation A on RoomInfo.accId = A.accId\n" +
                "    join AccOwner AO on A.accOwnerId = AO.accOwnerId\n" +
                "    join RoomImage R1 on RoomInfo.roomId = R1.roomId\n" +
                "    join AccImage AI on RoomInfo.accId = AI.accId\n" +
                "    join Tag T on A.accId = T.accId\n" +
                "    join Theme T2 on A.accId = T2.accId\n" +
                "    join (select accId, round(((sum(kind) + sum(clean) + sum(convenience) + sum(fitment))/(count(*)*4)),1) as star,\n" +
                "    count(*) as reviewCount\n" +
                "    from ReviewGrade\n" +
                "    join Review R on ReviewGrade.reviewgrade = R.grade\n" +
                "    group by accId) as R2 on T.accId = R2.accId\n" +
                "    where A.accId = ?;";
        int getAccommodationInfoParams = accId;
        return this.jdbcTemplate.query(getAccInfoQuery,
                (rs,rowNow) -> new Accommodation(
                        rs.getInt("accId"),
                        rs.getInt("roomId"),
                        rs.getString("roomName"),
                        rs.getString("roomSubTitle"),
                        rs.getInt("daesilMax"),
                        rs.getString("checkInPossibleTime"),
                        rs.getString("checkOutPossibleTime"),
                        rs.getInt("dePersonCnt"),
                        rs.getInt("maxPersonCnt"),
                        rs.getInt("daesilCost"),
                        rs.getInt("checkInCost"),
                        rs.getInt("discountRate"),
                        rs.getInt("emptyRoomCnt"),
                        rs.getString("accName"),
                        rs.getString("accPhone"),
                        rs.getString("accInfo"),
                        rs.getString("infoUse"),
                        rs.getString("bookNotice"),
                        rs.getString("infoPickup"),
                        rs.getString("directions"),
                        rs.getString("accImageUrl"),
                        rs.getString("roomImageUrl"),
                        rs.getFloat("star"),
                        rs.getInt("reviewCount"),
                        rs.getString("businessAddr"),
                        rs.getString("tag"),
                        rs.getBoolean("yCare"),
                        rs.getBoolean("parking"),
                        rs.getBoolean("breakfast"),
                        rs.getBoolean("wifi"),
                        rs.getBoolean("noSmokingRoom"),
                        rs.getBoolean("h24Desk")),
                getAccommodationInfoParams);
    }

}