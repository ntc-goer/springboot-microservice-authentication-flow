package ntcgoer.authservice.client.feignclient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ntcgoer.authservice.client.feignclient.feignclientrequest.CreateSignupDataClientRequest;
import ntcgoer.authservice.client.feignclient.feignclientrequest.UpdateVerifiedAtClientRequest;
import ntcgoer.authservice.client.feignclient.feignclientrequest.VerifySignupDataClientRequest;
import ntcgoer.authservice.client.feignclient.feignclientresponse.CreateSignupDataClientResponse;
import ntcgoer.authservice.client.feignclient.feignclientresponse.UserAccountClientResponse;
import ntcgoer.authservice.client.feignclient.feignclientresponse.VerifySignupDataClientResponse;
import ntcgoer.sharingmodule.exception.advice.HttpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "userservice")
public interface UserFeignClient {
    @CircuitBreaker(name = "verifySignupData")
    @PostMapping("/v1/accounts/verify-signup-data")
    HttpResponse<VerifySignupDataClientResponse> verifySignupData(
            @RequestBody VerifySignupDataClientRequest requestBody
    );

    @PostMapping("/v1/accounts")
    HttpResponse<CreateSignupDataClientResponse> createSignupData(
            @RequestBody CreateSignupDataClientRequest requestBody
    );

    @CircuitBreaker(name = "findAccountById")
    @GetMapping("/v1/accounts/{id}")
    HttpResponse<UserAccountClientResponse> findAccountById(@PathVariable("id") String id);

    @GetMapping("/v1/accounts")
    HttpResponse<UserAccountClientResponse> findAccountByEmailUsername(
            @RequestParam("emailOrUsername") String emailOrUsername
    );

    @PostMapping("/v1/accounts/update-verified-at")
    HttpResponse<UserAccountClientResponse> updateVerifiedAt(
            @RequestBody UpdateVerifiedAtClientRequest requestBody
    );

}
