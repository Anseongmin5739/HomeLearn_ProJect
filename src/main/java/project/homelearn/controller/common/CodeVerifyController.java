package project.homelearn.controller.common;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.homelearn.dto.common.EmailCodeDto;
import project.homelearn.dto.common.register.RegisterInfoDto;
import project.homelearn.service.common.RegisterService;
import project.homelearn.service.common.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CodeVerifyController {

    private final UserService userService;
    private final RegisterService registerService;

    // 회원가입 전 코드 인증
    @PostMapping("/code-verify")
    public HttpEntity<?> verifyCode(@Valid @RequestBody EmailCodeDto emailCodeDto) {
        boolean result = userService.verifyCode(emailCodeDto);

        if (result) {
            RegisterInfoDto registerInfo = registerService.getRegisterInfo(emailCodeDto);
            return new ResponseEntity<>(registerInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}