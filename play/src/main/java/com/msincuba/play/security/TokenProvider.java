package com.msincuba.play.security;

import com.msincuba.play.security.config.AuthenticationProperties;
import com.msincuba.play.utils.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClock;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenProvider implements Serializable {

    private final Clock clock = DefaultClock.INSTANCE;
    private static final String AUTHORITIES_KEY = "auth";

    private final AuthenticationProperties authenticationProperties;

    public TokenProvider(AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }
  
    public String createToken(Authentication authentication) {
        return getToken(authentication.getName(), authentication.getAuthorities());

    }

    public String createToken(UserDetails userDetails) {

        return getToken(userDetails.getUsername(), userDetails.getAuthorities());
    }

    private String getToken(String username, Collection<? extends GrantedAuthority> roles) {
        String authorities = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date expiration = new Date(new Date().getTime() * authenticationProperties.getExpiration());
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(clock.now())
                .signWith(SignatureAlgorithm.HS512, authenticationProperties.getSecret())
                .setExpiration(expiration)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date EXPIRATION_DATE = getExpirationDateFromToken(token);
        return EXPIRATION_DATE.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsReolver) {
        final Claims CLAIMS = getAllClaimsFromToken(token);

        return claimsReolver.apply(CLAIMS);
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(authenticationProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    private Date calculateExpirationDate(final Date createdDate) {
        return new Date(createdDate.getTime() + authenticationProperties.getExpiration() * 1000);
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, authenticationProperties.getSecret())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        UserDto user = (UserDto) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        return (username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, DateUtil.convert(user.getLastPasswordResetDate())));
    }
    
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(authenticationProperties.getSecret())
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(authenticationProperties.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
    
}
