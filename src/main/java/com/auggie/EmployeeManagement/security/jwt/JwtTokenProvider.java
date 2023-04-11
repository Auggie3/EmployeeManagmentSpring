package com.auggie.EmployeeManagement.security.jwt;


import com.auggie.EmployeeManagement.services.EmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.tokenValidityInMinutes}")
    private long tokenValidityInMinutes;

    @Value("${jwt.refreshTokenValidityInMinutes}")
    private long refreshTokenValidityInMinutes;

    @Autowired
    private EmployeeService employeeService;




    public JwtTokenDTO generateToken(Authentication authentication, boolean rememberMe){

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Integer id = employeeService.findIdOfUsername(authentication.getName());

        long now = new Date().getTime();
        Date accessTokenValidity = new Date(now + tokenValidityInMinutes * 60_000);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("credentials",id)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(accessTokenValidity)
                .compact();

        String refreshToken = null;

        if(rememberMe){
            Date refreshTokenValidity = new Date(now + refreshTokenValidityInMinutes * 60_000);
            refreshToken = Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim("auth", authorities)
                    .claim("credentials",id)
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .setExpiration(refreshTokenValidity)
                    .compact();
        }
        boolean isAdmin = false;
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            isAdmin = true;
        }

        return new JwtTokenDTO(accessToken, refreshToken, isAdmin);
    }


    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return true;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        Integer credentials = claims.get("credentials",Integer.class);

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(username,credentials, authorities);
    }
}
