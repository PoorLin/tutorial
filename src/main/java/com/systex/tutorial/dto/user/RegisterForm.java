package com.systex.tutorial.dto.user;

import lombok.*;

import java.util.Date;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

  private String email;

  private String name;

  private String password;

  private String confirmPassword;

  private Date birthday;
  
}
