package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    /**
     * 야놀자 API 1번
     * 내정보 조회 Probider
     * */
    public GetUserRes getUser(int userId) throws BaseException{
        try{
            GetUserRes getUserRes = userDao.getUser(userId);
            return getUserRes;
        } catch (Exception exception){
            throw new BaseException(FAILED_TO_SEARCH_USERINFO); // 조회
        }
    }

    /**
     * 회원가입 시 이메일 체크
     * */
    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        //논리적 validation 처리로 userState에 따라 처리해줘야함. -> userState가 탈퇴면 로그인이 되지않음.
        User user = userDao.getPwd(postLoginReq);
        String encryptPwd;
        try {
            encryptPwd=new SHA256().encrypt(postLoginReq.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        if(user.getUserState() != 0){
            throw new BaseException(FAILED_TO_LOGIN_USERSTATE);
        }
        if(user.getPassword().equals(encryptPwd)){
            int userId = user.getUserId();
            String jwt = jwtService.createJwt(userId);
            return new PostLoginRes(userId,jwt, user.getEmail());
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public PostUserPwdInfoRes checkPwd(PostUserPwdInfoReq postUserPwdInfoReq) throws BaseException{
        User user = userDao.getCheckPwd(postUserPwdInfoReq);
        String encryptPwd;
        try{
            encryptPwd= new SHA256().encrypt(postUserPwdInfoReq.getPassword());
        }catch (Exception ignored){
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        if(user.getPassword().equals(encryptPwd)){
            int userId = user.getUserId();
            return new PostUserPwdInfoRes(userId);
        }
        else {
            throw new BaseException(FAILED_TO_CHECKPASSWORD);

        }
    }

//    public List<GetUserRes> getUsers() throws BaseException{
//        try{
//            List<GetUserRes> getUserRes = userDao.getUsers();
//            return getUserRes;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public List<GetUserRes> getUsersByEmail(String email) throws BaseException{
//        try{
//            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
//            return getUsersRes;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//                    }
//
//
//    public GetUserRes getUser(int userIdx) throws BaseException {
//        try {
//            GetUserRes getUserRes = userDao.getUser(userIdx);
//            return getUserRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }



}
