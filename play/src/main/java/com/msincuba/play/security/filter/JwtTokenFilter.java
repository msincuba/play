package com.msincuba.play.security.filter;

import com.msincuba.play.security.TokenProvider;
import com.msincuba.play.security.config.AuthenticationProperties;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter{

    private final TokenProvider tokenProvider;
    private final AuthenticationProperties authenticationProperties;

    public JwtTokenFilter(final TokenProvider tokenProvider, 
            final AuthenticationProperties authenticationProperties) {
        this.tokenProvider = tokenProvider;
        this.authenticationProperties = authenticationProperties;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authToken = resolveToken(httpServletRequest);
        
        if (StringUtils.isNoneBlank(authToken) && tokenProvider.validateToken(authToken)) {
            Authentication authentication = this.tokenProvider.getAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(authenticationProperties.getTokenHeader());
        if (org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
