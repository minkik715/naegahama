package com.hanghae.naegahama.config.jwt;

import com.hanghae.naegahama.config.auth.UserDetailsImplService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    private String secretKey = "1234";

    private Long tokenValidTime = 1000 * 60 * 60L; // 1시간

    private final UserDetailsImplService userDetailsImplService;

    // JWT 토큰 생성
    public String createToken(String userPk) {

        Claims claims = Jwts.claims().setSubject(userPk); // payload에  정보 저장
        claims.put("userId", userPk); // 정보를 저장할 데이터 넣어주기

        Date now = new Date();
        // Jwt 인터페이스의 인스턴스를 만드는 클래스
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                //.setAudience("bob")   // 이런식으로 등록된 claim 사용가능
                // 사용할 암호화 알고리즘 및 signature에 들어갈 secretKey 값 설정
                .compact();
    }

    // 토큰에서 회원 정보 추출
    public String getuserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userId").toString();
    } // 추출하면 username, email등 유저의 정보가 나오게 됨.

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsImplService.checkUserByUserId(this.getuserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Request의 Header에서 token 값을 가져온다. 헤더에 "Authorization" : "TOKEN값" 형식으로 있다.
    public String tokenResolver(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if(cookies ==null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("access-token")){
                return cookie.getValue();
            }
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인  // -> 토큰이 expire되지 않았는지 True/False로 반환해줌.
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            //System.out.println(claims); // JWT 토큰(클라이언트에서 보낸)이 잘 들어오는지 검증하는 부분 -> 서버 콘솔에 token 찍힘.
            return !claims.getBody().getExpiration().before(new Date()); // expire시간이 되지 않았다면 True!
        } catch (Exception e) {
            return false;
        }
    }
}