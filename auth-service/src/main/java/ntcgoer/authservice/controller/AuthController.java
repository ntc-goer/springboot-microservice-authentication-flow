package ntcgoer.authservice.controller;

import lombok.RequiredArgsConstructor;
import ntcgoer.authservice.requestdto.SigninRequestDto;
import ntcgoer.authservice.requestdto.SignupRequestDto;
import ntcgoer.authservice.responsedto.CreateSignupDataResponse;
import ntcgoer.authservice.responsedto.SigninResponseDto;
import ntcgoer.authservice.responsedto.VerifyEmailResponse;
import ntcgoer.authservice.service.SigninService;
import ntcgoer.authservice.service.SignupService;
import ntcgoer.sharingmodule.exception.advice.HttpResponse;
import ntcgoer.sharingmodule.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class AuthController {
    private final SignupService signupService;
    private final SigninService signinService;

    @PostMapping(path = "signup")
    public ResponseEntity<HttpResponse<CreateSignupDataResponse>> signup(
            @Validated @RequestBody SignupRequestDto requestDto
    ) {
        CreateSignupDataResponse resp = this.signupService.signup(requestDto);
        return ResponseUtil.Ok(resp);
    }

    @GetMapping(path = "verify-email")
    public ResponseEntity<HttpResponse<VerifyEmailResponse>> verifyEmail(
            @RequestParam("token") String token
    ){
        VerifyEmailResponse resp = this.signupService.verifyEmail(token);
        return ResponseUtil.Ok(resp);
    }

    @PostMapping(path = "signin")
    public ResponseEntity<HttpResponse<SigninResponseDto>> signin(
            @Validated @RequestBody SigninRequestDto requestDto
    ) {
        SigninResponseDto resp = this.signinService.signin(requestDto);
        return ResponseUtil.Ok(resp);
    }
}
