package com.eastshine.springbootdev.controller;


import com.eastshine.springbootdev.dto.AddUserRequest;
import com.eastshine.springbootdev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request); // 회원가입 메서드 호출
        return "redirect:/login"; // 회원가입이 완료된 이후에 로그인 페이지로 이동
    }
}