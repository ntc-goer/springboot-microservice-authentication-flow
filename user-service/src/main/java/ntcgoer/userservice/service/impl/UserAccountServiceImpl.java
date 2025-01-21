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
    private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private final ModelMapper modelMapper;
    private final UserAccountRepository userAccountRepository;

    @Override
    public VerifySignupDataResponseDto verifySignupData(VerifySignupDataRequestDto verifySignupDataRequestDto) {
        try {
            Boolean userNameExists = this.userAccountRepository.existsByUserName(verifySignupDataRequestDto.getUserName());
            Boolean emailExists = this.userAccountRepository.existsByEmail(verifySignupDataRequestDto.getEmail());
            return new VerifySignupDataResponseDto(userNameExists, emailExists);
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    @Transactional
    public CreateUserAccountResponseDto createUserAccount(CreateAccountRequestDto createAccountRequestDto) {
        try {
            UserAccountEntity userAccountEntity = this.modelMapper.map(createAccountRequestDto, UserAccountEntity.class);
            this.userAccountRepository.save(userAccountEntity);
            CreateUserAccountResponseDto createUserAccountResponseDto = this.modelMapper.map(userAccountEntity, CreateUserAccountResponseDto.class);
            createUserAccountResponseDto.setIsVerified(false);
            return createUserAccountResponseDto;
        } catch (Exception e) {
            logger.error(String.format("UserAccountServiceImpl.CreateUserAccountResponseDto.Exception: %s", e.getMessage()));
            throw new InternalServerErrorException();
        }
    }

    @Override
    public void updateAccountVerifiedAt(UpdateAccountVerifiedAtRequestDto updateAccountVerifiedAtRequestDto) {
        try {
            UserAccountEntity userAccountEntity = this.userAccountRepository
                    .findById(updateAccountVerifiedAtRequestDto.getAccountId()).orElseThrow(
                            ResourceNotFoundException::new
                    );
            userAccountEntity.setVerifiedAt(LocalDateTime.now());
            this.userAccountRepository.save(userAccountEntity);
        } catch (ResourceNotFoundException ex) {
            logger.error("UserAccountServiceImpl.UpdateAccountVerifiedAtRequestDto.ResourceNotFoundException:", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("UserAccountServiceImpl.UpdateAccountVerifiedAtRequestDto.Exception: ", ex);
            throw new InternalServerErrorException();
        }
    }

    @Override
    public UserAccountResponseDto findById(String id) {
        try {
            UserAccountEntity userAccountEntity = this.userAccountRepository
                    .findById(id)
                    .orElseThrow(ResourceNotFoundException::new);
            this.userAccountRepository.save(userAccountEntity);
            return this.modelMapper.map(userAccountEntity, UserAccountResponseDto.class);
        } catch (ResourceNotFoundException ex) {
            logger.error("UserAccountServiceImpl.GetUserAccountResponseDto.ResourceNotFoundException: ", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("UserAccountServiceImpl.GetUserAccountResponseDto.Exception: ", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @Override
    public UserAccountWithPasswordResponseDto findAccountByEmailUsername(String emailUserName) {
        try {
            UserAccountEntity userAccountEntity = this.userAccountRepository
                    .findByEmailUserName(emailUserName);
            if (userAccountEntity == null) {
                throw new ResourceNotFoundException();
            }
            return this.modelMapper.map(userAccountEntity, UserAccountWithPasswordResponseDto.class);
        } catch (ResourceNotFoundException ex) {
            logger.error("UserAccountServiceImpl.GetUserAccountResponseDto.ResourceNotFoundException: ", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("UserAccountServiceImpl.GetUserAccountResponseDto.Exception: ", ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}
