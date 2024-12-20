package com.example.myfirstsolproj.controller;

import com.example.myfirstsolproj.dto.ItemDTO;
import com.example.myfirstsolproj.dto.MemberDTO;
import com.example.myfirstsolproj.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.util.SpringReactiveModelAdditionsUtils;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup2")
    public String signup2Get(Model model){
        model.addAttribute("memberDTO", new MemberDTO());

        return "member/signup2";
    }

    @PostMapping("/signup2")
    public String signup2Post(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model){

        log.info("저장의 post로 들어온 memberDTO 처음줄" + memberDTO);
        if (bindingResult.hasErrors()){

            log.info(bindingResult.getAllErrors());
            // 유효성 검사에 이상이 있다면 다시 회원가입 페이지로 보낸다.

            return "member/signup2";
        }
        try {
            memberService.saveMember(memberDTO);
        }catch (IllegalStateException e){
            e.getMessage();
            model.addAttribute("msg", e.getMessage());
            return "member/signup2";
        }

        return "member/login";
    }

    @GetMapping("/login")
    public String loginGet(){

        return "member/login";
    }


}
