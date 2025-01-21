package ntcgoer.authservice.service;

import ntcgoer.authservice.requestdto.SigninRequestDto;
import ntcgoer.authservice.responsedto.SigninResponseDto;

public interface SigninService {
    SigninResponseDto signin(SigninRequestDto requestDto);
}
