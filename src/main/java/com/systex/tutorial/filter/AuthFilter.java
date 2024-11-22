package com.systex.tutorial.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systex.tutorial.dto.HttpResponseData;
import com.systex.tutorial.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.systex.tutorial.constant.Constant.JWT_ERROR;
import static com.systex.tutorial.constant.Constant.LOGIN_DATA_ERROR;
import static com.systex.tutorial.util.JwtUtil.validToken;

@Slf4j
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {



        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(request.getRequestURI().endsWith("register")){
            filterChain.doFilter(servletRequest, servletResponse);
            log.info("don't need token api");
            return;
        }


        String authorization=request.getHeader("Authorization");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = authorization.substring(7);

        try {
            log.info("need token api");
            Claims claims = validToken(token,userService.getJwtKey());
        }catch (JwtException e){
            ObjectMapper mapper = new ObjectMapper();
            HttpResponseData loginResultData = new HttpResponseData(JWT_ERROR,"認證失效，請重新登入");
            String jsonResponse = mapper.writeValueAsString(loginResultData);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
            return;
        }


       filterChain.doFilter(request, response);
    }
}
