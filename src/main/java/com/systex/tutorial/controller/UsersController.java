package com.systex.tutorial.controller;

import com.systex.tutorial.dto.HttpResponseData;
import com.systex.tutorial.dto.user.RegisterForm;
import com.systex.tutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class UsersController {

  private final UserService userService;

  @PostMapping("/test")
  public String register2(@RequestBody RegisterForm form) {
    return "test";
  }

  @PostMapping("/register")
  public HttpResponseData register(@RequestBody RegisterForm form) {
    return userService.register(form);
  }

  @GetMapping("/profile")
  public HttpResponseData profile(@RequestParam Integer id) {
    return userService.profile(id);
  }

}
