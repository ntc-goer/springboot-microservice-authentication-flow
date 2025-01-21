package ntcgoer.sharingmodule.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ntcgoer.sharingmodule.exception.TokenErrorException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private final Algorithm algorithm;

    public JwtUtil(String secret) {
        algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(String issuer, Date expiration, Map<String, String> claims) throws TokenErrorException {
        try {
            Builder jwtToken = JWT.create()
                    .withExpiresAt(expiration)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date(System.currentTimeMillis()));
            if (claims != null) {
                for (String key : claims.keySet()) {
                    jwtToken.withClaim(key, claims.get(key));
                }
            }
            return jwtToken.sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new TokenErrorException(ex.getMessage());
        }
    }

    public String generateToken(String issuer, Date expiration) throws TokenErrorException {
        try {
            Builder jwtToken = JWT.create()
                    .withExpiresAt(expiration)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date(System.currentTimeMillis()));
            return jwtToken.sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new TokenErrorException(ex.getMessage());
        }
    }

    public Boolean verifyToken(String token, String issuer) throws TokenErrorException {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            throw new TokenErrorException(ex.getMessage());
        }
    }

    public Map<String, String> verifyTokenAndDecodeClaims(String token, String issuer, String[] claimKeys) throws TokenErrorException {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, String> claims = new HashMap<>();
            for (String claimKey : claimKeys) {
                if(!decodedJWT.getClaim(claimKey).isNull()) {
                    claims.put(claimKey, decodedJWT.getClaim(claimKey).asString());
                }
            }
            return claims;
        } catch (JWTVerificationException ex) {
            throw new TokenErrorException(ex.getMessage());
        }
    }

    public String verifyTokenAndDecodeClaim(String token, String issuer, String claimKey) throws TokenErrorException {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, String> claims = new HashMap<>();
            if(!decodedJWT.getClaim(claimKey).isNull()) {
                return decodedJWT.getClaim(claimKey).asString();
            }
            return null;
        } catch (JWTVerificationException ex) {
            throw new TokenErrorException(ex.getMessage());
        }
    }
}
