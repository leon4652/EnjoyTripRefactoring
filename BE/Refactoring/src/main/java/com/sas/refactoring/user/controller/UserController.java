package com.sas.refactoring.user.controller;

import com.sas.refactoring.jwt.JwtUtil;
import com.sas.refactoring.mail.controller.MailController;
import com.sas.refactoring.user.dto.UserDto;
import com.sas.refactoring.user.service.MakeRandNum;
import com.sas.refactoring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MailController mailController;
    private final JwtUtil jwtUtil;

    @GetMapping("/test")
    public void jwtTest() {
        //임의로 만든 jwt토큰 값을 파싱해서 해석해보겠음.
        jwtUtil.validateToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi7J2066aEIiwiaWQiOiJpZOuTpOyWtOqwkCIsImV4cCI6MTY4ODM4MzE4OCwic3ViIjoiYWNjZXNzLXRva2VuIn0.lbJwYTZ2Tdnb17cj8LHEdRXjn8RMITShGYnYPfD1trQ");
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody List<String> data) throws Exception {
        String RefreshToken = jwtUtil.createRefreshToken(1L);
        log.info(RefreshToken);
        jwtUtil.validateToken(RefreshToken); //토큰 유효한지 테스트
        return userService.checkLogin(data);
    }

    @PostMapping("/register")
    public void register(@RequestBody List<String> data) throws Exception {
        //data는 각각 id, password, name, email 순으로 들어옵니다.
        data.add(new MakeRandNum().start()); //랜덤 난수 생성
        userService.registerUser(data);
        mailController.sendCheckMail(data); //최초 인증용 메일 전송
    }

    @PostMapping("/checkduplicate")
    public boolean checkDuplicate(@RequestBody List<String> data) throws Exception {
        return userService.checkDuplicate(data);
    }

    @PostMapping("/firstauth")
    public boolean checkFirstAuth(@RequestBody List<String> data) throws Exception {
        //data 0 : code, 1 : id
        System.out.println(data.get(0) + " " + data.get(1));
        if(userService.checkFirstAuth(data)) {
            userService.changeAuth(data.get(0));
            return true;
        }
        else return false;
    }
}
