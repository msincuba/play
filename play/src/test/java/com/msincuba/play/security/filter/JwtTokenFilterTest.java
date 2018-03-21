package com.msincuba.play.security.filter;

import com.msincuba.play.security.AuthoritiesConstants;
import com.msincuba.play.security.TokenProvider;
import com.msincuba.play.security.config.AuthenticationProperties;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author mlungisi
 */
public class JwtTokenFilterTest {

    private TokenProvider tokenProvider;

    private JwtTokenFilter jwtFilter;

    private AuthenticationProperties authenticationProperties;
    
    private final long ONE_MINUTE = 60000L;

    @Before
    public void setUp() {
        authenticationProperties = new AuthenticationProperties();
        authenticationProperties.setSecret("secretKey");
        authenticationProperties.setExpiration(ONE_MINUTE);
        authenticationProperties.setTokenHeader("Authorization");
        tokenProvider = new TokenProvider(authenticationProperties);
        jwtFilter = new JwtTokenFilter(tokenProvider, authenticationProperties);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void givenValidUserAndRoles_thenAccessToApiIsGranted() throws ServletException, IOException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                "test-user",
                "test-user-password",
                Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))
        );
        String token = tokenProvider.createToken(authenticationToken);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(authenticationProperties.getTokenHeader(), "Bearer " + token);
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("test-user");
        assertThat(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()).isEqualTo(token);
    }

    @Test
    public void givenInvalidToken_thenAuthenticationFails() throws ServletException, IOException {
        String token = "wrong_jwt";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(authenticationProperties.getTokenHeader(), "Bearer " + token);
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    public void givenNoToken_thenNoAuthentication() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    public void givenNoTokenOnHeader_thenNoAuthentication() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(authenticationProperties.getTokenHeader(), "Bearer ");
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    public void givenWrongScheme_thenNoAuthentication() throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "test-user",
                "test-password",
                Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))
        );
        String token = tokenProvider.createToken(authentication);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(authenticationProperties.getTokenHeader(), "Basic " + token);
        request.setRequestURI("/api/test");
    }

}
