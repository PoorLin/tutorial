package com.systex.tutorial.config;

import com.systex.tutorial.filter.LoginFilter;
import com.systex.tutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  private final UserService userService;

  public FilterConfig(UserService userService) {
    this.userService = userService;
  }

  @Bean
  public FilterRegistrationBean<LoginFilter> loginFilter() {
    FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new LoginFilter(userService));
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(1); // 數字越小，優先級越高
    return registrationBean;
  }
}
