package ntcgoer.authservice.service;

import ntcgoer.authservice.requestdto.SignupRequestDto;
import ntcgoer.authservice.responsedto.CreateSignupDataResponse;
import ntcgoer.authservice.responsedto.VerifyEmailResponse;

public interface SignupService {
    CreateSignupDataResponse signup(SignupRequestDto signupRequestDto);
    VerifyEmailResponse verifyEmail(String token);
}
