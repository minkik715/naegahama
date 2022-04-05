<<<<<<< HEAD
/*
=======
package com.hanghae.naegahama.security.filter;/*
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
package com.hanghae.naegahama.security.filter;

import com.hanghae.naegahama.ex.TokenInvalidException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }catch (TokenInvalidException e){
            response.sendRedirect("/api/error");
        }
    }
}
*/
