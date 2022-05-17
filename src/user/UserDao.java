package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * userId 가져오기
     * */
    public GetUserIdRes getUserId(int userId){
        String getUserIdQuery = "select userId from User where userId = ?";
        int getUserIdParams =userId;
        return this.jdbcTemplate.queryForObject(getUserIdQuery,
                (rs,rowNow) -> new GetUserIdRes(
                        rs.getInt("userId")),
                getUserIdParams);
    }

    /**
     * 야놀자 API 1번
     * 내정보 조회 Dao
     * */
    public GetUserRes getUser(int userId){
        String getUserQuery = "select * from  User where userId = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs,rowNow) -> new GetUserRes(
                        rs.getInt("userId"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("userPhone"),
                        rs.getString("nickName"),
                        rs.getInt("point"),
                        rs.getInt("userState")),
                getUserParams);

    }

    /**
     * 야놀자 API 2번
     * 내정보  수정 - 닉네임 변경 Dao
     * */
    public int modifyUserNickName(PatchUserReq patchUserReq){
        String modifyUserNickNameQuery = "update User set nickName = ? where userId = ?";
        Object[] modifyUserNickNameParams = new Object[]{patchUserReq.getNickName(), patchUserReq.getUserId()};
        return this.jdbcTemplate.update(modifyUserNickNameQuery, modifyUserNickNameParams);
    }
    /**
     * 야놀자 API
     * 내정보  수정 - 패스워드 변경 Dao
     * */
    public int modifyUserPassword(PatchUserReq patchUserReq){
        String modifyUserPasswordQuery = "update User set password = ? where userId = ?";
        Object[] modifyUserPasswordParams = new Object[]{patchUserReq.getPassword(), patchUserReq.getUserId()};
        return this.jdbcTemplate.update(modifyUserPasswordQuery, modifyUserPasswordParams);
    }
    /**
     * 야놀자 API
     * 내정보  수정 - 연락처 변경 Dao
     * */
    public int modifyUserPhone(PatchUserReq patchUserReq){
        String modifyUserPhoneNameQuery = "update User set userPhone = ? where userId = ?";
        Object[] modifyUserPhoneParams = new Object[]{patchUserReq.getUserPhone(), patchUserReq.getUserId()};
        return this.jdbcTemplate.update(modifyUserPhoneNameQuery, modifyUserPhoneParams);
    }


    /**
     * 야놀자 API
     * 회원 탈퇴 - UserState 변경 Dao
     * */
    public int SecessionByUser(PatchUserReq patchUserReq){
        String modifyUserStateQuery = "update User set userState = 1 where userId = ?"; //0이 활동, 1이 탈퇴
        Object[] modifyUserStateParams = new Object[]{patchUserReq.getUserId()}; //오류면 여기 수정
        return this.jdbcTemplate.update(modifyUserStateQuery, modifyUserStateParams);
    }

    public User getCheckPwd(PostUserPwdInfoReq postUserPwdInfoReq){
        String getPwdQuery = "select *\n" +
                "from User\n" +
                "where userId = ?";
        int getPwdParams = postUserPwdInfoReq.getUserId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userId"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("userPhone"),
                        rs.getString("nickName"),
                        rs.getInt("point"),
                        rs.getInt("userState")),
                getPwdParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (email, password, userPhone, nickName) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getPassword(),
                postUserReq.getUserPhone(), postUserReq.getNickName()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }


    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select *\n" +
                "from User\n" +
                "where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userId"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("userPhone"),
                        rs.getString("nickName"),
                        rs.getInt("point"),
                        rs.getInt("userState")),
                getPwdParams);
    }

//    public List<GetUserRes> getUsers(){
//        String getUsersQuery = "select * from UserInfo";
//        return this.jdbcTemplate.query(getUsersQuery,
//                (rs,rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password"))
//                );
//    }
//
//
//    public List<GetUserRes> getUsersByEmail(String email){
//        String getUsersByEmailQuery = "select * from UserInfo where email =?";
//        String getUsersByEmailParams = email;
//        return this.jdbcTemplate.query(getUsersByEmailQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUsersByEmailParams);
//    }
//
//    public GetUserRes getUser(int userIdx){
//        String getUserQuery = "select * from UserInfo where userIdx = ?";
//        int getUserParams = userIdx;
//        return this.jdbcTemplate.queryForObject(getUserQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUserParams);
//    }
//
//


//    public int modifyUserName(PatchUserReq patchUserReq){
//        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
//        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};
//
//        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
//    }
//



}
