package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import javax.servlet.http.HttpSession;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }


    public String GetToken(String code) throws IOException{
        String access_Token = "";
        String refresh_Token = "";
        String host = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";
        try{
            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=8511c38e2d874555288974c3ae93e0e6");
            sb.append("&redirect_uri=http://localhost:8080/app/users/oauth/kakao");
            // 로컬 테스트용 리다이렉트
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("result = " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

//            System.out.println("access_token : " + access_Token);
//            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("        토큰발행 성공 ->       " + access_Token);
        System.out.println("토큰발행 서비스 클리어\n\n\n");
        return access_Token;
    }

    public PostLoginRes createKakaoUser(String token) throws BaseException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        //access_token을 이용하여 사용자 정보 조회
        String email = null;
        PostLoginRes postLoginRes = null;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

//            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

//            System.out.println("id : " + id);
            System.out.println("email : " + email);
            if (userProvider.checkEmail(email) != 0) {
                PostLoginReq postLoginReq = new PostLoginReq(email, "");
                User user = userDao.getPwd(postLoginReq);

                postLoginReq.setPassword(user.getPassword());

                String jwt = jwtService.createJwt(user.getUserId());

                postLoginRes = new PostLoginRes(user.getUserId(), jwt, email);
                System.out.println("카카오 로그인 서비스 클리어\n\n\n");
                return postLoginRes;
            }
            System.out.println("일단 저장된 이메일 주소 ------->    " + email);
            postLoginRes = new PostLoginRes(0, null, email);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return postLoginRes;

    }

    /**
     * kakao 로그아웃 api
     *
     * */
    public void kakaoLogout(String access_Token){
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("경과 ->    "+result);
            System.out.println("로그아웃 서비스 클리어\n\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    /**
     * 닉네임 변경 시
     * */
    public void modifyUserNickName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserNickName(patchUserReq);
            if (result == 0){
                throw new BaseException(MODIFY_FAIL_NICKNAME);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 패스워드 변경 시
     * */
    public void modifyUserPassword(PatchUserReq patchUserReq) throws BaseException {
        String pwd;
        try{
            pwd = new SHA256().encrypt(patchUserReq.getPassword());
            patchUserReq.setPassword(pwd);

            int result = userDao.modifyUserPassword(patchUserReq);
            if (result == 0){
                throw new BaseException(MODIFY_FAIL_PASSWORD);
            }


        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 연락처 변경 시
     * */
    public void modifyUserPhone(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserPhone(patchUserReq);
            if (result == 0){
                throw new BaseException(MODIFY_FAIL_PHONE);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 회원 탈퇴 시
     * */
    public void SecessionByUser(PatchUserReq patchUserReq) throws BaseException {

        try{
            int result = userDao.SecessionByUser(patchUserReq);
            if (result == 0){
                throw new BaseException(MODIFY_FAIL_SECESSION);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**  SecessionByUser
     * 회원 가입
     * 회원 가입할때 이메일, 패스워드, 연락처 닉네임은 안받아도 되는데 나는 null 허용 안했으니 그냥 입력받는다. - 야놀자
     * 야놀자 서비스는 회원가입 후 바로 로그인이 되기에 JWT발급 또한 회원가입에서도 이루어진다.
     * */
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }


//    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
//        try{
//            int result = userDao.modifyUserName(patchUserReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USERNAME);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
