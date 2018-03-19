package com.msincuba.play.rest;

import com.msincuba.play.security.JwtTokenUtil;
import com.msincuba.play.security.JwtUser;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Value("${jwt.header}")
    private String tokenHeader;
    
    private final JwtTokenUtil jwtTokenUtil;
    
    private UserDetailsService userDetailsService;

    public UserRestController(final JwtTokenUtil jwtTokenUtil, @Qualifier("jwtUserDetailsService") final UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
    
    @GetMapping
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
    
}
