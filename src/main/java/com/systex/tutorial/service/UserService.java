package com.systex.tutorial.service;

import com.systex.tutorial.dto.HttpResponseData;
import com.systex.tutorial.dto.user.LoginForm;
import com.systex.tutorial.dto.user.RegisterForm;
import com.systex.tutorial.dto.user.UserDTO;
import com.systex.tutorial.entity.Users;
import com.systex.tutorial.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;

import static com.systex.tutorial.constant.Constant.*;
import static com.systex.tutorial.util.JwtUtil.parseSHA256;

@Service
public class UserService {

  @Autowired
  private UsersRepository usersRepository;


  public HttpResponseData register(RegisterForm form) {
     Users users= new Users();
     users.setEmail(form.getEmail());
     users.setPassword(form.getPassword());
    if (usersRepository.existsByEmail(users.getEmail())) {
      return new HttpResponseData(SUCCESS, REGISTER_ERROR);
    }
    users.setPassword(parseSHA256(users.getPassword()));
    usersRepository.save(users);
    return new HttpResponseData(SUCCESS);
  }

  public UserDTO loginForm(LoginForm user, Map<String, String> errorMsg) {
    Optional<Users> optUser = usersRepository.findByEmail(user.getEmail());
    if (optUser.isPresent()) {
      Users dbuser = optUser.get();
      String sha256Password = parseSHA256(user.getPassword());

      //如果密碼一致則登入成功，沒有則失敗
      if (sha256Password.equals(dbuser.getPassword())) {
        return UserDTO.builder()
          .id(dbuser.getId())
          .name(dbuser.getName())
          .email(dbuser.getEmail())
          .build();
      } else {
        errorMsg.put("password", "帳號密碼錯誤");
        return null;
      }
    } else {
      errorMsg.put("password", "帳號密碼錯誤");
      return null;
    }
  }

  public HttpResponseData login(LoginForm user) {
    Optional<Users> optUser = usersRepository.findByEmail(user.getEmail());
    // 如果此email存在就繼續檢驗密碼，沒有則失敗
    if (optUser.isPresent()) {
      Users dbuser = optUser.get();
      String sha256Password = parseSHA256(user.getPassword());

      //如果密碼一致則登入成功，沒有則失敗
      if (sha256Password.equals(dbuser.getPassword())) {
        return new HttpResponseData<>(SUCCESS,dbuser);
      } else {
        return new HttpResponseData<>(LOGIN_ERROR);
      }
    } else {
      return new HttpResponseData<>(LOGIN_ERROR);
    }
  }


}
