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
import org.apache.coyote.Response;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.systex.tutorial.constant.Constant.*;
import static com.systex.tutorial.util.JwtUtil.generateToken;

public class LoginFilter implements Filter {


  private final UserService userService;

  public LoginFilter(UserService userService) {
    this.userService = userService;
  }


  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    throws IOException, ServletException {


    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;



    HttpSession httpSession = request.getSession();
    String path = request.getRequestURI();
    String jwt = request.getHeader("Authorization");

      ObjectMapper mapper = new ObjectMapper();
      //處理JSON
      InputStream inputStream = request.getInputStream();
     // System.out.println(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
      LoginForm loginForm = mapper.readValue(inputStream, LoginForm.class);
      System.out.println(loginForm);
      //LoginForm loginForm = new LoginForm();
      inputStream.close();

      String email = loginForm.getEmail();
      String password = loginForm.getPassword();

      boolean isnull = false;

      if( null == email){
        isnull = true;
      }

      if( null == password){
        isnull = true;
      }

      if(isnull){
        HttpResponseData loginResultData = new HttpResponseData(LOGIN_DATA_ERROR,"請輸入正確的資料");
        String jsonResponse = mapper.writeValueAsString(loginResultData);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
        return;
      }
      System.out.println("有近來嗎?");
      //能通過基本的驗證才進入service
      HttpResponseData loginResultData = userService.login(loginForm);
      if (loginResultData.getResponseCode() == 200) {// 登入成功
        Users user = (Users) loginResultData.getResponseData();

        System.out.println("有登入成功嗎?");
        String token=generateToken("token", user.getId(), Long.valueOf(userService.getJwtExipred()), userService.getJwtKey());
        HttpResponseData<LoginDTO> successData = new HttpResponseData<>(SUCCESS,new LoginDTO(token, user.getId()));
        String jsonResponse = mapper.writeValueAsString(successData);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
        return;
//       response.getWriter().write(jsonResponse);
      } else { // 登入失敗
        System.out.println("登入失敗?");
        String jsonResponse = mapper.writeValueAsString(loginResultData);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
        return;
      }

  }
}
