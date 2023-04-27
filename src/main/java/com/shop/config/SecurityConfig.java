package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableWebSecurity  /*WebSecurityConfigurerAdapter를 상속받는 클래스에 @EnableWebSecurity 선언하면 SpringSecurityFilterChain이
                        자동으로 포함. WebSecurityConfigurerAdapter를 상속받아 메소드 오버라이딩을 통해 보안 설정 커스터마이징 가능*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {  /*http 요청에 대한 보안 설정. 페이지 권한 설정, 로그인 페이지
                                                                        설정, 로그아웃 메소드 등에 대한 설정 작성*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {      /*비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원
                                                        정보가 그대로 노출된다. 이를 위해, BCryptPasswordEncoder의 해시 함수를
                                                        이용하여 비밀번호를 암호화하여 저장. @Bean 등록한다.*/
        return new BCryptPasswordEncoder();
    }

}