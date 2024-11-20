package com.systex.tutorial.dto.user;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

  private String email;

  private String password;

  private String confirmPassword;
  
}
