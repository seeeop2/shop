package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableWebSecurity  /*WebSecurityConfigurerAdapter를 상속받는 클래스에 @EnableWebSecurity 선언하면 SpringSecurityFilterChain이
                        자동으로 포함. WebSecurityConfigurerAdapter를 상속받아 메소드 오버라이딩을 통해 보안 설정 커스터마이징 가능*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {  /*http 요청에 대한 보안 설정. 페이지 권한 설정, 로그인 페이지
                                                                        설정, 로그아웃 메소드 등에 대한 설정 작성*/
        http.formLogin()
                .loginPage("/members/login")    //로그인할 페이지 URL 설정
                .defaultSuccessUrl("/")     //로그인 성공 시 이동할 URL 설정
                .usernameParameter("email") //로그인시 사용할 파리미터 이름으로 email 지정
                .failureUrl("/members/login/error") //로그인 실패시 이동할 URL 설정
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 URL 설정
                .logoutSuccessUrl("/")  //로그아웃 성공 시 이동할 URL 설정
        ;

        http.authorizeRequests()    //시큐리티 처리에 httpServeltRequest를 이용한다는 의미
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                /*permitAll()을 통해 모든 사용자가 인증(로그인) 없이 해당 경로에 접근할 수 있도록 설정. 메인 페이지, 회원 관련 URL, 뒤에서 만들
                    상품 상세 페이지, 상품 이미지를 불러오는 경로가 이에 해당*/
                .mvcMatchers("/admin/**").hasRole("ADMIN")/* 1차 주소 /admin 경로는 ADMIN role일 경우에만 접근 가능*/
                .anyRequest().authenticated()/*permitAll(), hasRole("ADMIN") 제외한 나머지 경로들은 모두 인증을 요구하도록 설정*/
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())/*인증되지 않은 사용자가 리소스에 접근하였을 때
                                                                                    수행되는 핸들러 등록*/
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {      /*비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원
                                                        정보가 그대로 노출된다. 이를 위해, BCryptPasswordEncoder의 해시 함수를
                                                        이용하여 비밀번호를 암호화하여 저장. @Bean 등록한다.*/
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*Spring Security에서 인증은 AuthenticationManager를 통해 이루어지며,
            AuthenticationManagerBuilder 객체가 AuthenticationManager를 생성*/
        auth.userDetailsService(memberService)      //userDetailsService를 구현하고 있는 객체로 memberService를 지정
                .passwordEncoder(passwordEncoder());    //비밀번호 암호화를 위해 passwordEncoder를 지정
    }

    @Override
    public void configure(WebSecurity web) throws Exception {       /*static 디렉터리의 하위 파일은 인증을 무시하도록 설정*/
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

}