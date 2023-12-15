package com.group.doconnect.component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.group.doconnect.dto.StatusDTO;
import com.group.doconnect.dto.UserResponseDTO;
import com.group.doconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.group.doconnect.service.LogoutTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtilityComponent implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.token-validity-time}")
    private int JWT_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private LogoutTokenService logoutTokenService;

    @Autowired
    private UserService userService;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        StatusDTO<UserResponseDTO> userStatus = userService.getUserByUsername(userDetails.getUsername());
        if (userStatus.getIsValid()) {
            UserResponseDTO user = userStatus.getObject();
            claims.put("isAdmin", user.getIsAdmin());
            claims.put("email", user.getEmail());
            claims.put("name", user.getName());
        }
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //generate token
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //check if there is valid token
    public Boolean validateToken(String token, UserDetails userDetails) {
        if (logoutTokenService.TokenExists(token)) {
            return false;
        }
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
