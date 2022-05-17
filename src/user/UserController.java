package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;




    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 야놀자 API 1번
     * 내정보 조회 API
     * [GET] /users/:userId
     * @return BaseResponse<GetUserRes>
     * */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") int userId){
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetUserRes getUserRes = userProvider.getUser(userId);
            return new BaseResponse<>(getUserRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API 2번
     * 내정보 변경 - 닉네임 변경 API
     * [PATCH] /users/fixNickName/:userId
     * @return BaseResponse<PatchUserReq>
     * */
    @ResponseBody
    @PatchMapping("/fixNickName/{userId}")
    public BaseResponse<String> modifyUserNickName(@PathVariable("userId")int userId, @RequestBody User user){
        try{
            //Jwt에서 userId 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 동일한지 검증.
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchUserReq patchUserReq = new PatchUserReq(userId, user.getNickName(), user.getPassword(), user.getUserPhone(), user.getUserState());
            userService.modifyUserNickName(patchUserReq);

            String result ="변경 되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API
     * 내정보 변경 - 패스워드 변경 API
     * [PATCH]
     * @return BaseResponse<String>
     * */
    @ResponseBody
    @PatchMapping("/fix-password/{userId}")
    public BaseResponse<String> modifyUserPassword(@PathVariable("userId")int userId, @RequestBody User user){
        try{
            //Jwt에서 userId 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 동일한지 검증.
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchUserReq patchUserReq = new PatchUserReq(userId, user.getNickName(), user.getPassword(), user.getUserPhone(), user.getUserState());
            userService.modifyUserPassword(patchUserReq);

            String result ="변경 되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 야놀자 API
     * 내정보 변경 - 연락처 변경 API
     * [PATCH]
     * @return BaseResponse<String>
     * */
    @ResponseBody
    @PatchMapping("/fix-phone/{userId}")
    public BaseResponse<String> modifyUserPhone(@PathVariable("userId")int userId, @RequestBody User user){
        try{
            //Jwt에서 userId 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 동일한지 검증.
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchUserReq patchUserReq = new PatchUserReq(userId, user.getNickName(), user.getPassword(), user.getUserPhone(), user.getUserState());
            userService.modifyUserPhone(patchUserReq);

            String result ="변경 되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 야놀자 API
     * 회원 탈퇴  API
     * [PATCH]
     * @return BaseResponse<String>
     * */
    @ResponseBody
    @PatchMapping("/secession-users/{userId}")
    public BaseResponse<String> SecessionByUser(@PathVariable("userId")int userId, @RequestBody User user){
        try{
            //Jwt에서 userId 추출.
            int userIdByJwt = jwtService.getUserId();
            //userId와 접근한 유저가 동일한지 검증.
            if(userId != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PostUserPwdInfoReq postUserPwdInfoReq = new PostUserPwdInfoReq(userId,user.getPassword());
            PostUserPwdInfoRes postUserPwdInfoRes = userProvider.checkPwd(postUserPwdInfoReq);
            int checkPwd = postUserPwdInfoRes.getUserId();
            if(checkPwd != userIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchUserReq patchUserReq = new PatchUserReq(userId, user.getNickName(), user.getPassword(), user.getUserPhone(), user.getUserState());
            userService.SecessionByUser(patchUserReq);

            String result ="변경 되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /users/login
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * test API
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @RequestMapping(value = "/oauth/kakao",method = RequestMethod.GET)
    public BaseResponse<PostLoginRes> kakaoLogin(@RequestParam String code,HttpSession session) throws IOException{
        try {
            System.out.println(code);
            String accessToken = userService.GetToken(code);
            session.setAttribute("access_Token",accessToken);
            System.out.println("세션에 토큰 등록 성공\n\n");
            PostLoginRes postLoginRes = userService.createKakaoUser(accessToken);
            if(postLoginRes.getJwt() != null) {
                System.out.println("동기화된 유저 Id값 = " + postLoginRes.getUserId());
                System.out.println("동기화된 유저 JWT값 = " + postLoginRes.getJwt());
                System.out.println(session.getAttribute("access_Token"));
                System.out.println("소셜 로그인 성공");
                System.out.println("카카오 로그인 컨트롤러 끝\n\n\n");
            }else {
                System.out.println("DB에 저장된 이메일과 일치하는 정보가 존재 하지 않아\n회원가입을 진행하셔야 합니다.\n");
                String reqURL = "https://localhost:8080/app/users";
//                위 경로는 로컬 테스트용

                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                System.out.println("회원정보를 입력받는 폼이 필요함.\n\n");
            }
            return new BaseResponse<>(postLoginRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

//    @ResponseBody
    @RequestMapping(value = "/oauth/kakao-logout",method = RequestMethod.GET)
    public String kakaoLogout(HttpSession session){
        userService.kakaoLogout((String) session.getAttribute("access_Token"));
        System.out.println("세션 토큰말소전 토큰 값 ->  "+session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        System.out.println("세션 토큰말소  ->    "+session.getAttribute("access_Token"));
        System.out.println("카카오 로그아웃 컨트롤러 끝 \n\n\n");
        return "로그아웃 성공?";
    }

//        /**
//     * 회원 조회 API
//     * [GET] /users
//     * 회원 번호 및 이메일 검색 조회 API
//     * [GET] /users? Email=
//     * @return BaseResponse<List<GetUserRes>>
//     */
//    //Query String
//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
//    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
//        try{
//            if(Email == null){
//                List<GetUserRes> getUsersRes = userProvider.getUsers();
//                return new BaseResponse<>(getUsersRes);
//            }
//            // Get Users
//            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
//            return new BaseResponse<>(getUsersRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

//    /**
//     * 회원 1명 조회 API
//     * [GET] /users/:userIdx
//     * @return BaseResponse<GetUserRes>
//     */
//    // Path-variable
//    @ResponseBody
//    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
//    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
//        // Get Users
//        try{
//            GetUserRes getUserRes = userProvider.getUser(userIdx);
//            return new BaseResponse<>(getUserRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//    }


//    /**
//     * 유저정보변경 API
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{userIdx}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
//        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//            //같다면 유저네임 변경
//            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
//            userService.modifyUserName(patchUserReq);
//
//            String result = "";
//        return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


}
