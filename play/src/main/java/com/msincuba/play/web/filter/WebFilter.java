package com.msincuba.play.web.filter;

import com.msincuba.play.web.RequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 *
 * Passes request as RequestWrapper to get payload
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class WebFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ((StringUtils.containsIgnoreCase(request.getRequestURL(), "/login") || StringUtils.containsIgnoreCase(request.getRequestURI(), "/login"))) {
            filterChain.doFilter(request, response);
        } else {
            //if instance of multipart skip request wrapping so file uploads works
            MultipartResolver multipartResolver = lookupMultipartResolver();
            String encoding = StringUtils.isBlank(request.getCharacterEncoding()) ? Charset.defaultCharset().name() : request.getCharacterEncoding();
            HttpServletRequest processedRequest = request;
            if (multipartResolver.isMultipart(processedRequest)) {
                processedRequest = multipartResolver.resolveMultipart(processedRequest);

                try {
                    filterChain.doFilter(processedRequest, response);
                } finally {
                    if (processedRequest instanceof MultipartHttpServletRequest) {
                        multipartResolver.cleanupMultipart((MultipartHttpServletRequest) processedRequest);
                    }
                }
            } else {
                // A regular request...
                RequestWrapper wrapperRequest = new RequestWrapper((HttpServletRequest) request);

                InputStream inputStream = wrapperRequest.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));
                String requestBody = bufferedReader.lines().collect(Collectors.joining());
                wrapperRequest.setAttribute("requestBody", requestBody);
                filterChain.doFilter(wrapperRequest, response);
            }

        }
    }

    protected MultipartResolver lookupMultipartResolver() {
        return new StandardServletMultipartResolver();
    }

}
