package com.hanghae.naegahama.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hanghae.naegahama.ex.TokenInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.hanghae.naegahama.security.jwt.JwtTokenUtils.*;


@Component
public class JwtDecoder {


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public String decodeUsername(String token) {
        log.info("token = {}", token);
        String username;
        try {
            DecodedJWT decodedJWT = isValidToken(token)
                    .orElseThrow(() -> new TokenInvalidException("유효한 토큰이 아닙니다."));

            Date expiredDate = decodedJWT
                    .getClaim(CLAIM_EXPIRED_DATE)
                    .asDate();

            Date now = new Date();
            if (expiredDate.before(now)) {
                throw new TokenInvalidException("토큰의 유효시간이 끝났습니다.");
            }

            username = decodedJWT
                    .getClaim(CLAIM_USER_NAME)
                    .asString();
        }catch (TokenInvalidException e){
            throw  new TokenInvalidException("유효한 토큰이 아닙니다.");
        }
            return username;
    }



    public Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}
