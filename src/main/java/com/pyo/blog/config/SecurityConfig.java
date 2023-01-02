package com.pyo.blog.config;


import com.pyo.blog.config.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // 1-0. 빈 등록(IoC 관리)
@EnableWebSecurity // 1-1. 시큐리티 활성화가 되어있지만 -> 기본 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는것 // 위에 3개는 세트임

public class SecurityConfig {

    @Autowired
    private PrincipalDetailService principalDetailService;

    //1-2 WebSecurityConfiguerAdapter를 상속해서 AuthenticationManager를 bean으로 등록했지만
    //이제 직접 등록하면 된다.
    @Bean
    public AuthenticationManager authenticationManager
    (AuthenticationConfiguration authConfiguration) throws Exception {

        return authConfiguration.getAuthenticationManager();
    }
    //1-3 기존의 SecurityConfig 에서 configure 메소드 기능을 한다

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  //csrf 토튼 비활성 (테스트시 걸어두는게 좋다)
                .authorizeRequests()
                .antMatchers("/","/blog","/auth/**","/js/**","/css/**","image/**","/dummy/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")  // 스프링 시큐리티가 해당 주소로 요청되는 로그인을 가로채서 대신 로그인 기능 수행
                .defaultSuccessUrl("/");  //로그인이 끝난다면 여기로

        return http.build();
    }

    //1-11. 비밀번호 해시 설정
    @Bean //IoC 등록
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }



    // 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
    //해당 password 가 어떤걸로 hash가 되어 회원가입되었는지 알아야
    //같은 해쉬로 암호화 해서 DB에있는 해쉬와 비교할 수 있음
    // 없으면 비밀번호 비교를 못함
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }



//    public class MyCustom
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        AuthenticationManager authenticationManager =
//                http.getSharedObject(AuthenticationManager.class);
//        http.addFilter(corsConfig.corsFilter())
//                .addFilter(new jwt)
//    }


}

//    // 1-12. 정적 파일 인증 무시.(2.7.0이상 부터는 이런방식으로 설정해야함 -> bean은 임시로 등록안해놓았음)
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        WebSecurityCustomizer web = new WebSecurityCustomizer() {
//            @Override
//            public void customize(WebSecurity web) {
//                web.ignoring().antMatchers("/tistory/css/**", "/tistory/image/**", "/tistory/js/**");
//            }
//        };
//        return web;
//    }
//
