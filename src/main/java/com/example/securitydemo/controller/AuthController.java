package com.example.securitydemo.controller;

import com.example.securitydemo.dto.SignupDTO;
import com.example.securitydemo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("authorities", userDetails.getAuthorities());
            model.addAttribute("password", userDetails.getPassword());
        }
        return "dashboard";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupDTO", new SignupDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute SignupDTO signupDTO,
            BindingResult bindingResult
    ) {
        if(!signupDTO.getPassword().equals(signupDTO.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "mismatch", "비밀번호가 일치하지 않습니다.");
        }

        // 검증 실패
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // DB 조회가 필요한 검증
        // 아이디 중복체크
        if(userService.existsByUsername(signupDTO.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", "이미 사용중인 아이디입니다.");
            return "signup";
        }

        // 이메일 중복체크
        if(userService.existsByEmail(signupDTO.getEmail())) {
            bindingResult.rejectValue("email","duplicate","이미 사용중인 이메일입니다.");
            return "signup";
        }

        // 검증 성공 => 회원가입 처리
        userService.register(signupDTO);


        return "redirect:/login";
    }
}
