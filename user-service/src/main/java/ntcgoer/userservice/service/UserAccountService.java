package ntcgoer.userservice.service;

import ntcgoer.userservice.requestdto.CreateAccountRequestDto;
import ntcgoer.userservice.requestdto.UpdateAccountVerifiedAtRequestDto;
import ntcgoer.userservice.requestdto.VerifySignupDataRequestDto;
import ntcgoer.userservice.responsedto.CreateUserAccountResponseDto;
import ntcgoer.userservice.responsedto.UserAccountResponseDto;
import ntcgoer.userservice.responsedto.UserAccountWithPasswordResponseDto;
import ntcgoer.userservice.responsedto.VerifySignupDataResponseDto;

public interface UserAccountService {
    VerifySignupDataResponseDto verifySignupData(VerifySignupDataRequestDto verifySignupDataRequestDto);
    CreateUserAccountResponseDto createUserAccount(CreateAccountRequestDto createAccountRequestDto);
    void updateAccountVerifiedAt(UpdateAccountVerifiedAtRequestDto updateAccountVerifiedAtRequestDto);
    UserAccountResponseDto findById(String id);
    UserAccountWithPasswordResponseDto findAccountByEmailUsername(String emailOrUserName);
}
