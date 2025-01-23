package ntcgoer.authservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ntcgoer.authservice.client.feignclient.UserFeignClient;
import ntcgoer.authservice.client.feignclient.feignclientrequest.CreateSignupDataClientRequest;
import ntcgoer.authservice.client.feignclient.feignclientrequest.UpdateVerifiedAtClientRequest;
import ntcgoer.authservice.client.feignclient.feignclientrequest.VerifySignupDataClientRequest;
import ntcgoer.authservice.client.feignclient.feignclientresponse.UserAccountClientResponse;
import ntcgoer.authservice.code.OutboxMessageStatus;
import ntcgoer.authservice.code.OutboxMessageType;
import ntcgoer.authservice.entity.OutboxMessageEntity;
import ntcgoer.authservice.publisher.emailtemplatepublisherdata.EmailTemplatePublisherMetadata;
import ntcgoer.authservice.publisher.emailtemplatepublisherdata.EmailTemplatePublisherPayload;
import ntcgoer.authservice.repository.OutboxMessageRepository;
import ntcgoer.authservice.requestdto.SignupRequestDto;
import ntcgoer.authservice.responsedto.CreateSignupDataResponse;
import ntcgoer.authservice.responsedto.VerifyEmailResponse;
import ntcgoer.authservice.service.SignupService;
import ntcgoer.sharingmodule.configuration.UrlConfiguration;
import ntcgoer.sharingmodule.exception.BadRequestException;
import ntcgoer.sharingmodule.exception.ConflictException;
import ntcgoer.sharingmodule.exception.InternalServerErrorException;
import ntcgoer.sharingmodule.exception.TokenErrorException;
import ntcgoer.sharingmodule.exception.advice.HttpResponse;
import ntcgoer.sharingmodule.http.FeignSafeExecutor;
import ntcgoer.sharingmodule.util.DateUtil;
import ntcgoer.sharingmodule.util.HashUtil;
import ntcgoer.sharingmodule.util.JwtUtil;
import ntcgoer.sharingmodule.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {
    private final JwtUtil jwtUtil;
    private final DateUtil dateUtil;
    private final HashUtil hashUtil;
    private final StringUtil stringUtil;

    private final FeignSafeExecutor feignSafeExecutor;

    private final UserFeignClient userFeignClient;
    private final ModelMapper modelMapper;
    private final UrlConfiguration userUrlConfiguration;
    private final OutboxMessageRepository outboxMessageRepository;

    @Value("${spring.application.name}")
    private String appName;

    @Transactional
    @Override
    public CreateSignupDataResponse signup(SignupRequestDto signupRequestDto) throws JsonProcessingException {
        var resp = this.feignSafeExecutor.Call(() -> this.userFeignClient.verifySignupData(
                this.modelMapper.map(
                        signupRequestDto,
                        VerifySignupDataClientRequest.class
                )
        ));
        if (resp.hasError()) {
            throw new InternalServerErrorException();
        }
        if (resp.getResponse().getData().getEmailTaken()) {
            throw new ConflictException("Email taken");
        } else if (resp.getResponse().getData().getUserNameTaken()) {
            throw new ConflictException("Username taken");
        }

        String hashedPassword = this.hashUtil.encode(signupRequestDto.getPassword());
        signupRequestDto.setPassword(hashedPassword);
        var createSignupDataResp = this.feignSafeExecutor.Call(() -> this.userFeignClient.createSignupData(
                this.modelMapper.map(
                        signupRequestDto,
                        CreateSignupDataClientRequest.class
                )
        ));

        if (createSignupDataResp.hasError()) {
            throw new InternalServerErrorException();
        }
        // Generate verify url
        String token = this.jwtUtil.generateToken(
                this.appName,
                this.dateUtil.getDateByPlusDay(1),
                new HashMap<>() {{
                    put("email", signupRequestDto.getEmail());
                    put("accountId", createSignupDataResp.getResponse().getData().getId());
                }}
        );

        EmailTemplatePublisherMetadata emailTemplatePublisherMetadata = new EmailTemplatePublisherMetadata(
                "Verify Email",
                "VERIFY_EMAIL_SIGN_UP",
                signupRequestDto.getEmail()
        );
        Map<String, String> emailTemplatePublisherVariable = new HashMap<>() {{
            put("userName", signupRequestDto.getEmail());
            put("verifyUrl", String.format("%s/v1/verify-email?token=%s",
                    userUrlConfiguration.getGatewayUrl(),
                    token
            ));
        }};
        EmailTemplatePublisherPayload emailTemplatePublisherPayload = new EmailTemplatePublisherPayload(
                emailTemplatePublisherMetadata,
                emailTemplatePublisherVariable
        );

        OutboxMessageEntity outboxMessageEntity = OutboxMessageEntity
                .builder()
                .messageType(OutboxMessageType.EMAIL_TEMPLATE)
                .status(OutboxMessageStatus.PENDING)
                .payload(new ObjectMapper().writeValueAsString(emailTemplatePublisherPayload))
                .build();
        this.outboxMessageRepository.save(outboxMessageEntity);
        return this.modelMapper.map(createSignupDataResp.getResponse().getData(), CreateSignupDataResponse.class);

    }

    @Override
    public VerifyEmailResponse verifyEmail(String token) {
        Map<String, String> claims = this.jwtUtil.verifyTokenAndDecodeClaims(
                token,
                this.appName,
                new String[]{"email", "accountId"}
        );
        if (this.stringUtil.isNullOrEmpty(claims.get("email")) ||
                this.stringUtil.isNullOrEmpty(claims.get("accountId"))) {
            throw new BadRequestException("Invalid token");
        }
        var userAccountClientResponse =
                this.feignSafeExecutor.Call(() ->
                        this.userFeignClient.findAccountById(claims.get("accountId")));
        if (userAccountClientResponse.hasError()) {
            throw new InternalServerErrorException();
        }
        HttpResponse<UserAccountClientResponse> userAccountClientData = userAccountClientResponse.getResponse();

        if (!userAccountClientData.getData().getEmail().equalsIgnoreCase(claims.get("email"))) {
            throw new TokenErrorException("Invalid token");
        }
        if (userAccountClientData.getData().getVerifiedAt() != null) {
            throw new ConflictException("Email verified");
        }
        var resp = this.feignSafeExecutor.Call(() -> this.userFeignClient.updateVerifiedAt(new UpdateVerifiedAtClientRequest(
                userAccountClientData.getData().getId(),
                LocalDateTime.now()
        )));
        if (resp.hasError()) {
            throw new InternalServerErrorException();
        }
        return new VerifyEmailResponse(true);
    }
}
