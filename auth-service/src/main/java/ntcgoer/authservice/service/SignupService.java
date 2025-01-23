package ntcgoer.authservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ntcgoer.authservice.requestdto.SignupRequestDto;
import ntcgoer.authservice.responsedto.CreateSignupDataResponse;
import ntcgoer.authservice.responsedto.VerifyEmailResponse;

public interface SignupService {
    CreateSignupDataResponse signup(SignupRequestDto signupRequestDto) throws JsonProcessingException;
    VerifyEmailResponse verifyEmail(String token);
}
