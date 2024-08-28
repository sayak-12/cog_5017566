package main.java.com.example.bookstoreapi.security.filters;

import com.example.bookstoreapi.security.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String token = jwtUtils.extractToken(httpRequest);
        if (token != null && jwtUtils.validateToken(token)) {
            String username = jwtUtils.extractUsername(token);
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(username, null, jwtUtils.getAuthorities(token));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
