package com.systex.tutorial.config;

import com.systex.tutorial.filter.AuthFilter;
import com.systex.tutorial.filter.CorsFilter;
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
    registrationBean.addUrlPatterns("/users/login");
    registrationBean.setOrder(2); // 數字越小，優先級越高
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilter() {
    FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new CorsFilter());
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(1); // 數字越小，優先級越高
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<AuthFilter> authFilter() {
    FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthFilter(userService));
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(3); // 數字越小，優先級越高
    return registrationBean;
  }
}
