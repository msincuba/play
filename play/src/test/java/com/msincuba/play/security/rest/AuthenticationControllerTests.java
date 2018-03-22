package com.msincuba.play.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msincuba.play.security.LoginRequest;
import com.msincuba.play.security.TokenProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mlungisi
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTests {

    private MockMvc mvc;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HttpMessageConverter[] httpMessageConverters;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        AuthenticationController controller = new AuthenticationController(authenticationManager, userDetailsService, tokenProvider);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(httpMessageConverters)
                .build();

    }

    @Test
    @Ignore
    @Transactional
    public void testCreateAuthenticationToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest("admin", "admin");
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(loginRequest);
        mvc.perform(
                post("/login")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isOk()
                );
    }

    @Test
    public void testRefreshAndGetAuthenticationToken() {
    }

    @Test
    public void testHandleAuthenticationException() {
    }

}
