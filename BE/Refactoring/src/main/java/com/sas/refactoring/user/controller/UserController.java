package com.sas.refactoring.user.controller;

import com.sas.refactoring.mail.controller.MailController;
import com.sas.refactoring.mail.service.MailService;
import com.sas.refactoring.user.dto.UserDto;
import com.sas.refactoring.user.service.MakeRandNum;
import com.sas.refactoring.user.service.UserService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MailController mailController;

    public UserController(UserService userService, MailController mailController) {
        this.userService = userService;
        this.mailController = mailController; //메일 전송용 컨트롤러 의존성 주입
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody List<String> data) throws Exception {
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
