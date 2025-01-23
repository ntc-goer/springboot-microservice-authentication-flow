package ntcgoer.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ntcgoer.sharingmodule.exception.InternalServerErrorException;
import ntcgoer.sharingmodule.exception.ResourceNotFoundException;
import ntcgoer.userservice.entity.UserAccountEntity;
import ntcgoer.userservice.repository.UserAccountRepository;
import ntcgoer.userservice.requestdto.CreateAccountRequestDto;
import ntcgoer.userservice.requestdto.UpdateAccountVerifiedAtRequestDto;
import ntcgoer.userservice.requestdto.VerifySignupDataRequestDto;
import ntcgoer.userservice.responsedto.CreateUserAccountResponseDto;
import ntcgoer.userservice.responsedto.UserAccountResponseDto;
import ntcgoer.userservice.responsedto.UserAccountWithPasswordResponseDto;
import ntcgoer.userservice.responsedto.VerifySignupDataResponseDto;
import ntcgoer.userservice.service.UserAccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final ModelMapper modelMapper;
    private final UserAccountRepository userAccountRepository;

    @Override
    public VerifySignupDataResponseDto verifySignupData(VerifySignupDataRequestDto verifySignupDataRequestDto) {
        Boolean userNameExists = this.userAccountRepository.existsByUserName(verifySignupDataRequestDto.getUserName());
        Boolean emailExists = this.userAccountRepository.existsByEmail(verifySignupDataRequestDto.getEmail());
        return new VerifySignupDataResponseDto(userNameExists, emailExists);
    }

    @Override
    @Transactional
    public CreateUserAccountResponseDto createUserAccount(CreateAccountRequestDto createAccountRequestDto) {
        UserAccountEntity userAccountEntity = this.modelMapper.map(createAccountRequestDto, UserAccountEntity.class);
        this.userAccountRepository.save(userAccountEntity);
        CreateUserAccountResponseDto createUserAccountResponseDto = this.modelMapper.map(userAccountEntity, CreateUserAccountResponseDto.class);
        createUserAccountResponseDto.setIsVerified(false);
        return createUserAccountResponseDto;
    }

    @Override
    public void updateAccountVerifiedAt(UpdateAccountVerifiedAtRequestDto updateAccountVerifiedAtRequestDto) {
        UserAccountEntity userAccountEntity = this.userAccountRepository
                .findById(updateAccountVerifiedAtRequestDto.getAccountId()).orElseThrow(
                        ResourceNotFoundException::new
                );
        userAccountEntity.setVerifiedAt(LocalDateTime.now());
        this.userAccountRepository.save(userAccountEntity);
    }

    @Override
    public UserAccountResponseDto findById(String id) {
        UserAccountEntity userAccountEntity = this.userAccountRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        this.userAccountRepository.save(userAccountEntity);
        return this.modelMapper.map(userAccountEntity, UserAccountResponseDto.class);
    }

    @Override
    public UserAccountWithPasswordResponseDto findAccountByEmailUsername(String emailUserName) {
        UserAccountEntity userAccountEntity = this.userAccountRepository
                .findByEmailUserName(emailUserName);
        if (userAccountEntity == null) {
            throw new ResourceNotFoundException();
        }
        return this.modelMapper.map(userAccountEntity, UserAccountWithPasswordResponseDto.class);
    }
}
