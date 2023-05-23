package com.example.userservice.security;


import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    // 인증을 위한 빈을 Spring 으로부터 가져옵니다.
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ObjectPostProcessor<Object> objectPostProcessor;



    /**
     * 인가(권한)를 위한 준비
     * HttpSecurity를 쓰고있는 config는 권한에 관련 메서드입니다.
     */
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {

        try {
            http
                    .csrf().disable()
                    .headers().frameOptions().disable()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/**") // 통과시키지않을 URL
                    .hasIpAddress("172.21.9.177") //통과시킬 IP
                    .and()
                    .addFilter(getAuthenticationFilter()); //인증처리는 해당 필터를 이용하겠습니다.

            http.headers().frameOptions().disable(); //추가하지 않으면 h2-console 접근안됨
        } catch(Exception e){
            e.printStackTrace();
        }
        return http.build();
    }


    /**
     * AuthenticationManagerBuilder는 인증에 관련된 메서드입니다.
     * 인증 후, 권한 메서드를 처리하는 구조입니다.
     * .Encoder를 통해 해독합니다. 사용자가 입력한 비밀번호와 디비에 encrypt되어있는 비밀번호를 비교하기 위한 목적입니다.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        // 사용자 요청 처리 -> bcrypt되어 있는 비밀번호를 Encode(해독)해줌으로써
        // 사용자 요청값과 db에 저장된 bcrypt비밀번호와 비교해줍니다.
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return auth.build();
    }

    /**
     * 인증처리를 위한 필터를 가져옵니다.
     */
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(); //인증 처리
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
        authenticationFilter.setAuthenticationManager(authenticationManager((builder)));
        return authenticationFilter;
    }



}
