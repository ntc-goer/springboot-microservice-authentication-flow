package ntcgoer.authservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ntcgoer.authservice.client.feignclient.UserFeignClient;
import ntcgoer.authservice.client.feignclient.feignclientresponse.UserAccountClientResponse;
import ntcgoer.authservice.requestdto.SigninRequestDto;
import ntcgoer.authservice.responsedto.SigninResponseDto;
import ntcgoer.authservice.service.SigninService;
import ntcgoer.sharingmodule.exception.ForbiddenException;
import ntcgoer.sharingmodule.exception.InternalServerErrorException;
import ntcgoer.sharingmodule.exception.UnauthorizedException;
import ntcgoer.sharingmodule.http.FeignSafeExecutor;
import ntcgoer.sharingmodule.util.HashUtil;
import ntcgoer.sharingmodule.util.RedisUtil;
import ntcgoer.sharingmodule.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SigninServiceImpl implements SigninService {
    private static final Logger logger = LoggerFactory.getLogger(SigninServiceImpl.class);

    private final HashUtil hashUtil;
    private final StringUtil stringUtil;
    private final RedisUtil redisUtil;
    private final UserFeignClient userFeignClient;
    private final FeignSafeExecutor feignSafeExecutor;

    @Value("${np-redis.auth-expire}")
    private int authExpireTime = 48;

    @Override
    @Transactional
    public SigninResponseDto signin(SigninRequestDto requestDto) {
        try {
            var userAccountClientResp =
                    this.feignSafeExecutor.Call(() -> this.userFeignClient
                            .findAccountByEmailUsername(requestDto.getUserNameOrEmail()));
            if (userAccountClientResp.hasNotFoundError()) {
                throw new UnauthorizedException();
            }
            if (userAccountClientResp.hasError()) {
                throw new InternalServerErrorException();
            }
            if (userAccountClientResp.getResponse().getData().getVerifiedAt() == null) {
                throw new ForbiddenException("UNVERIFIED_ACCOUNT");
            }

            Boolean matched = this.hashUtil.matches(
                    requestDto.getPassword(),
                    userAccountClientResp.getResponse().getData().getPassword());
            if (!matched) {
                throw new UnauthorizedException();
            }

            String accessToken = this.stringUtil.generateRandomString();
            this.handleStoreRedisSigninData(accessToken, userAccountClientResp.getResponse().getData());

            return new SigninResponseDto(accessToken);
        } catch (UnauthorizedException ex) {
            logger.error(String.format("SigninServiceImpl.signin.UnauthorizedException: %s", ex.getMessage()));
            throw ex;
        } catch (ForbiddenException ex) {
            logger.error(String.format("SigninServiceImpl.signin.ForbiddenException: %s", ex.getMessage()));
            throw ex;
        } catch (Exception ex) {
            logger.error(String.format("SigninServiceImpl.signin.Exception: %s", ex.getMessage()));
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    private void handleStoreRedisSigninData(String accessToken, UserAccountClientResponse resp) {
        // Check old token
        String redisKey = String.format("auth-accountId:%s:token", resp.getId());
        var tokens = this.redisUtil.getList(redisKey);
        for (var token : tokens) {
            // remove all old token data
            this.redisUtil.delete(String.format("auth-token:%s:data", token));
        }
        this.redisUtil.delete(redisKey);

        // Create access token
        // auth-accountId:<accountId>:tokens -> [<token>]
        String tokenKey = String.format("auth-accountId:%s:token", resp.getId());
        this.redisUtil.rightPushToList(tokenKey, accessToken, authExpireTime);
        // auth-token:<token>:data -> data
        String tokenDataKey = String.format("auth-token:%s:data", accessToken);
        HashMap<Object, Object> redisValue = new HashMap<>() {{
            put("accountId", resp.getId());
            put("email", resp.getEmail());
            put("userName", resp.getUserName());
            put("verifiedAt", resp.getVerifiedAt());
        }};
        this.redisUtil.addHashMap(tokenDataKey, redisValue, authExpireTime);
    }
}
