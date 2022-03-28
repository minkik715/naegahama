package com.hanghae.naegahama.security.filter;

import com.hanghae.naegahama.security.jwt.HeaderTokenExtractor;
import com.hanghae.naegahama.security.jwt.JwtPreProcessingToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Token 을 내려주는 Filter 가 아닌  client 에서 받아지는 Token 을 서버 사이드에서 검증하는 클레스 SecurityContextHolder 보관소에 해당
 * Token 값의 인증 상태를 보관 하고 필요할때 마다 인증 확인 후 권한 상태 확인 하는 기능
 */
@Slf4j
public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final HeaderTokenExtractor extractor;
    public JwtAuthFilter(
            RequestMatcher requiresAuthenticationRequestMatcher,
            HeaderTokenExtractor extractor
    ) {
        super(requiresAuthenticationRequestMatcher);
        this.extractor = extractor;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {

        // JWT 값을 담아주는 변수 TokenPayload
        log.info("AUTHFILTER 순서");
            String tokenPayload = request.getHeader("token");
            log.info("token={}", tokenPayload);
            if (tokenPayload == null || tokenPayload.equals("false") || tokenPayload.equals("null") || tokenPayload.equals("undefined")) {
                response.sendRedirect("/api/error");
                return null;
            }
            JwtPreProcessingToken jwtToken = new JwtPreProcessingToken(
                    extractor.extract(tokenPayload, request));

            try {
                return super
                        .getAuthenticationManager()
                        .authenticate(jwtToken);
            }catch (Exception e){
                //response.sendRedirect("/api/error");
                response.setStatus(400);
                return null;
            }

    }


    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        /*
         *  SecurityContext 사용자 Token 저장소를 생성합니다.
         *  SecurityContext 에 사용자의 인증된 Token 값을 저장합니다.
         */
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        // FilterChain chain 해당 필터가 실행 후 다른 필터도 실행할 수 있도록 연결실켜주는 메서드
        chain.doFilter(
                request,
                response
        );
    }

    /*@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String token = httpServletRequest.getHeader("token");
        try {
            DecodedJWT decodedJWT = (DecodedJWT) isValidToken(token)
                    .orElseThrow(() -> new TokenInvalidException("유효한 토큰이 아닙니다."));

            Date expiredDate = decodedJWT
                    .getClaim(CLAIM_EXPIRED_DATE)
                    .asDate();

            Date now = new Date();
            if (expiredDate.before(now)) {
                throw new TokenInvalidException("토큰의 유효시간이 끝났습니다.");
            }
        }catch (TokenInvalidException e){
            httpServletResponse.sendError(400);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }*/

    /*private <T> Optional isValidToken(String token )  {
        DecodedJWT jwt = null;
        log.info("여기서오류1");

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();

            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("여기서오류2");
        return Optional.ofNullable(jwt);
    }
*/
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {




            SecurityContextHolder.clearContext();

            super.unsuccessfulAuthentication(
                    request,
                    response,
                    failed
            );
        }
    }

