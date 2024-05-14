package com.phd.chomp.config;

import com.phd.chomp.config.auth.MyUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(this.userDetailsService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // CSRF 설정 Disable
        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )

                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )

                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                /*.requestMatchers("/cart").hasRole("USER")*/
                                .requestMatchers("/index", "/auth/**", "/reissue", "/cart", "/cookie/test").permitAll()
                                .requestMatchers("/bootstrap/**", "/img/**", "/webfonts/**", "/vendors/**", "/fonts/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)

                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/loginProc") // 가로챌 url
                        .usernameParameter("uid")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/index", true)
                        .failureForwardUrl("/auth/login/error")
                        .permitAll()
                )
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/index")
                )
                /*.exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
                )*/;

        return  http.build();

    }

    // css나 js 파일 등 정적 자원에 시큐리티 필터 적용 해제
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
