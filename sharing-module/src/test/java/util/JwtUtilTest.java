package util;

import ntcgoer.sharingmodule.exception.TokenErrorException;
import ntcgoer.sharingmodule.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class JwtUtilTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil("test_secret_key");
    }

    @Test
    public void whenGenerateToken_thenTokenReturned() {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", "test-sub");
        String token = jwtUtil.generateToken(
                "test_issuer",
                new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000),
                claims
        );
        Boolean verified = jwtUtil.verifyToken(token, "test_issuer");
        assertThat(token).isNotEmpty().isNotEmpty();
        assertThat(verified).isTrue();
    }

    @Test
    public void whenVerifyExpiredToken_thenThrowException() {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", "test-sub");
        String token = jwtUtil.generateToken(
                "test_issuer",
                new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000),
                claims
        );
        assertThatThrownBy(() -> jwtUtil.verifyToken(token, "test_issuer")).isInstanceOf(TokenErrorException.class);
    }

    @Test
    public void whenVerifyWrongIssuerToken_thenThrowException() {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", "test-sub");
        String token = jwtUtil.generateToken(
                "test2nd_issuer",
                new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000),
                claims
        );
        assertThatThrownBy(() -> jwtUtil.verifyToken(token, "test_issuer")).isInstanceOf(TokenErrorException.class);
    }

    @Test
    public void whenVerifyToken_thenGetClaim() {
        Map<String, String> claims = new HashMap<>();
        claims.put("sub", "test-sub");
        String token = jwtUtil.generateToken(
                "test_issuer",
                new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000),
                claims
        );

        String claimValue = jwtUtil.verifyTokenAndDecodeClaim(token, "test_issuer", "sub");
        assertThat(claimValue).isEqualTo("test-sub");
    }
}
