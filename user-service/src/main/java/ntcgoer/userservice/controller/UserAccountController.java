package ntcgoer.userservice.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import ntcgoer.sharingmodule.exception.advice.HttpResponse;
import ntcgoer.sharingmodule.util.ResponseUtil;
import ntcgoer.userservice.requestdto.CreateAccountRequestDto;
import ntcgoer.userservice.requestdto.UpdateAccountVerifiedAtRequestDto;
import ntcgoer.userservice.requestdto.VerifySignupDataRequestDto;
import ntcgoer.userservice.responsedto.CreateUserAccountResponseDto;
import ntcgoer.userservice.responsedto.UserAccountResponseDto;
import ntcgoer.userservice.responsedto.UserAccountWithPasswordResponseDto;
import ntcgoer.userservice.responsedto.VerifySignupDataResponseDto;
import ntcgoer.userservice.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(path = "/accounts/verify-signup-data")
    public ResponseEntity<HttpResponse<VerifySignupDataResponseDto>> verifySignupData(
            @Validated @RequestBody VerifySignupDataRequestDto verifySignupDataRequestDto
    ) throws Exception {
        VerifySignupDataResponseDto resp = this.userAccountService.verifySignupData(verifySignupDataRequestDto);
        return ResponseUtil.Ok(resp);
    }

    @PostMapping(path = "/accounts")
    public ResponseEntity<HttpResponse<CreateUserAccountResponseDto>> createAccount(
            @Validated @RequestBody CreateAccountRequestDto createAccountRequestDto
    ) {
        CreateUserAccountResponseDto resp = this.userAccountService.createUserAccount(createAccountRequestDto);
        return ResponseUtil.Created(resp);
    }

    @GetMapping(path = "/accounts/{id}")
    public ResponseEntity<HttpResponse<UserAccountResponseDto>> findById(
            @PathVariable String id) {
        UserAccountResponseDto resp = this.userAccountService.findById(id);
        return ResponseUtil.Ok(resp);
    }

    @GetMapping(path = "/accounts")
    public ResponseEntity<HttpResponse<UserAccountWithPasswordResponseDto>> findAccountByEmailUsername(
            @RequestParam String emailOrUsername
    ) {
        UserAccountWithPasswordResponseDto resp = this.userAccountService.findAccountByEmailUsername(emailOrUsername);
        return ResponseUtil.Ok(resp);
    }

    @PostMapping(path = "/accounts/update-verified-at")
    public ResponseEntity<HttpResponse<Object>> updateAccountVerified(
            @Validated @RequestBody UpdateAccountVerifiedAtRequestDto requestDto
    ) {
        this.userAccountService.updateAccountVerifiedAt(requestDto);
        return ResponseUtil.Ok();

    }
}
