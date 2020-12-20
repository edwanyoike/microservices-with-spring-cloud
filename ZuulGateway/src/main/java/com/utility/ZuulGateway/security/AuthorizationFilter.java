package com.utility.ZuulGateway.security;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private Environment  environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));

        if(authorizationHeader == null || !authorizationHeader.startsWith(Objects.requireNonNull(environment.getProperty("authorization.token.header.prefix")))){
            chain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request,authorizationHeader);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,String authorizationHeader) {
        if(authorizationHeader == null){
            return null ;
        }

        String token = authorizationHeader.replace(Objects.requireNonNull(environment.getProperty("authorization.token.header.prefix")),"");

        String userId = Jwts.parser()
                .setSigningKey(environment.getProperty("token.secret"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        if(userId == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userId,null,new ArrayList<>());

    }
}
