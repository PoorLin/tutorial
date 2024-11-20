package com.systex.tutorial.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systex.tutorial.dto.HttpResponseData;
import com.systex.tutorial.dto.user.LoginDTO;
import com.systex.tutorial.dto.user.LoginForm;
import com.systex.tutorial.dto.user.UserDTO;
import com.systex.tutorial.entity.Users;
import com.systex.tutorial.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.systex.tutorial.constant.Constant.EMAIL_REGEX;
import static com.systex.tutorial.constant.Constant.LOGIN_DATA_ERROR;
import static com.systex.tutorial.util.JwtUtil.generateToken;

public class LoginFilter implements Filter {


  private final UserService userService;

  public LoginFilter(UserService userService) {
    this.userService = userService;
    System.out.println(123);
  }


  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    throws IOException, ServletException {

    System.out.println("trigger");
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;


    HttpSession httpSession = request.getSession();
    String path = request.getRequestURI();
    String jwt = request.getHeader("Authorization");
    InputStream inputStream = request.getInputStream();
    String requestBody = new String(inputStream.readAllBytes());
    System.out.println("this is body" + requestBody);
    inputStream.close();

    if (path.endsWith("login")) {

      ObjectMapper mapper = new ObjectMapper();
      //處理JSON

      LoginForm loginForm = mapper.readValue(inputStream, LoginForm.class);
      inputStream.close();
      String email = loginForm.getEmail();
      String password = loginForm.getPassword();

      //能通過基本的驗證才進入service
      HttpResponseData loginResultData = userService.login(loginForm);
      if (loginResultData.getResponseCode() == 200) {// 登入成功
        Users userDTO = (Users) loginResultData.getResponseData();
//        response.setContentType("application/json");
//        response.getWriter().write(jsonResponse);
      } else { // 登入失敗
        String jsonResponse = mapper.writeValueAsString(loginResultData);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);

      }

      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
      filterChain.doFilter(servletRequest, servletResponse);


    }
  }
}
