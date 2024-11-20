package com.systex.tutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer{

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:4200") // 替换为前端地址
      .allowedMethods("*")
      .allowedHeaders("*")
      .allowCredentials(true)
      .maxAge(3600);
  }
}
