package com.systex.tutorial.service;

import com.systex.tutorial.dto.HttpResponseData;
import com.systex.tutorial.dto.user.LoginForm;
import com.systex.tutorial.dto.user.RegisterForm;
import com.systex.tutorial.dto.user.UserDTO;
import com.systex.tutorial.entity.Users;
import com.systex.tutorial.repository.UsersRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static com.systex.tutorial.constant.Constant.*;
import static com.systex.tutorial.util.JwtUtil.parseSHA256;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersRepository usersRepository;

    @Getter
    @Value("${jwt.key}")
    private String jwtKey;
    @Getter
    @Value("${jwt.exipred}")
    private String jwtExipred;


    public HttpResponseData register(RegisterForm form) {
        Users users = new Users();
        users.setEmail(form.getEmail());
        users.setPassword(form.getPassword());
        users.setName(form.getName());
        if(form.getBirthday() == null){
            form.setBirthday(new Date());
        }
        users.setBirthday(form.getBirthday());
        if(!form.getPassword().equals(form.getConfirmPassword())) {
            return new HttpResponseData(REGISTER_ERROR);
        }

        if (usersRepository.existsByEmail(users.getEmail())) {
            return new HttpResponseData(REGISTER_ERROR);
        }
        users.setPassword(parseSHA256(users.getPassword()));
        usersRepository.save(users);
        return new HttpResponseData(SUCCESS);
    }

    public HttpResponseData login(LoginForm user) {
        Optional<Users> optUser = usersRepository.findByEmail(user.getEmail());
        // 如果此email存在就繼續檢驗密碼，沒有則失敗
        if (optUser.isPresent()) {
            Users dbuser = optUser.get();
            String sha256Password = parseSHA256(user.getPassword());

            //如果密碼一致則登入成功，沒有則失敗
            if (sha256Password.equals(dbuser.getPassword())) {
                return new HttpResponseData<>(SUCCESS, dbuser);
            } else {
                return new HttpResponseData<>(LOGIN_ERROR);
            }
        } else {
            return new HttpResponseData<>(LOGIN_ERROR);
        }
    }

    public HttpResponseData profile(Integer userId) {
        Optional<Users> optUser = usersRepository.findById(userId);
        if (optUser.isPresent()) {
            Users dbuser = optUser.get();
            return new HttpResponseData<>(SUCCESS, UserDTO.builder()
                    .id(dbuser.getId())
                    .name(dbuser.getName())
                    .email(dbuser.getEmail())
                    .birthday(dbuser.getBirthday())
                    .build()
            );

        } else {
            return new HttpResponseData<>(PROFILE_NOTEXIST, "查無此人");
        }
    }


}
