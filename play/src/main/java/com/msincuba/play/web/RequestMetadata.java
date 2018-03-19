package com.msincuba.play.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class RequestMetadata {

    @JsonIgnore
    private HttpServletRequest request;

    private String characterEncoding;
    private String contentType;
    private String localAddr;
    private String localName;
    private int localPort;
    private Locale locale;
    private String method;
    private Map<String, String[]> parameterMap;
    private String protocol;
    private String queryString;
    private String remoteAddr;
    private String remoteHost;
    private int remotePort;
    private String remoteUser;
    private String requestURI;
    private StringBuffer requestURL;
    private String scheme;
    private String serverName;
    private int serverPort;
    private String servletPath;
    private Principal userPrincipal;
    private Cookie[] cookies;

    public RequestMetadata() {
    }

    public RequestMetadata(HttpServletRequest request) {
        this.request = request;
        this.characterEncoding = request.getCharacterEncoding();
        this.contentType = request.getContentType();
        this.localAddr = request.getLocalAddr();
        this.localName = request.getLocalName();
        this.localPort = request.getLocalPort();
        this.locale = request.getLocale();
        this.method = request.getMethod();
        this.parameterMap = request.getParameterMap();
        this.protocol = request.getProtocol();
        this.queryString = request.getQueryString();
        this.remoteAddr = request.getRemoteAddr();
        this.remoteHost = request.getRemoteHost();
        this.remotePort = request.getRemotePort();
        this.remoteUser = request.getRemoteUser();
        this.requestURI = request.getRequestURI();
        this.requestURL = request.getRequestURL();
        this.scheme = request.getScheme();
        this.serverName = request.getServerName();
        this.serverPort = request.getServerPort();
        this.servletPath = request.getServletPath();
        this.userPrincipal = request.getUserPrincipal();
        this.cookies = request.getCookies();
    }

    public String metadataAsString() {
        ObjectMapper mapper = new ObjectMapper();
        String requestMetadataAsString = "";
        try {
            requestMetadataAsString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            log.error("Failed to read trace information to a string", ex);
        }
        return requestMetadataAsString;
    }

}
