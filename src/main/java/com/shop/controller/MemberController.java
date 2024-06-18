package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
                             Model model){
        // @Valid 붙은 객체를 검사해서 결과에 에러가 있으면 실행
        if(bindingResult.hasErrors()){
            return "member/memberForm"; // 다시 회원가입으로 돌려 보냅니다.
        }

        try{
            // Member 객체 생성
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            // 데이터베이스에 저장
            memberService.saveMember(member);
        }
        catch (IllegalStateException e){ // 똑같은 데이터가 있으면
            model.addAttribute("errorMessage", e.getMessage()); // 이미 가입된 회원입니다.
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
