package com.msincuba.play.security.config;

import com.msincuba.play.security.JwtAuthenticationEntryPoint;
import com.msincuba.play.security.TokenProvider;
import com.msincuba.play.security.filter.JwtTokenFilter;
import com.msincuba.play.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProperties authenticationProperties;

    private final JwtAuthenticationEntryPoint authorizedHandler;

    private final TokenProvider tokenProvider;

    private final JwtUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    WebSecurityConfig(AuthenticationProperties authenticationProperties,
            JwtAuthenticationEntryPoint authorizedHandler,
            TokenProvider tokenProvider,
            JwtUserDetailsService userDetailsService,
            final PasswordEncoder passwordEncoder) {
        super();
        this.authenticationProperties = authenticationProperties;
        this.authorizedHandler = authorizedHandler;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authorizedHandler).and()
                .sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS)).and()
                .authorizeRequests()
                .antMatchers("/h2-console/**/**", "/login/**", "/signup/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
                .anyRequest().authenticated();

        JwtTokenFilter authorizationTokenFilter = new JwtTokenFilter(tokenProvider, authenticationProperties);

        http.addFilterBefore(authorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().sameOrigin().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(HttpMethod.POST, "/login").and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).and()
                .ignoring()
                .antMatchers("/h2-console/**/**").and()
                .ignoring()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/webjars/**");

    }

}
