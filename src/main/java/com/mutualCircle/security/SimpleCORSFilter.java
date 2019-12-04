package com.mutualCircle.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class SimpleCORSFilter implements Filter {

    private static Logger logger = LogManager.getLogger(SimpleCORSFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        logger.info("SIMPLE CORS FILTER HAS BEEN REACHED => " + request.getRequestURL().toString());

        String[] allowedDomains = {"http://localhost:8000", "http://localhost:8080", "http://localhost:4200"};
        
        Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(allowedDomains));

        String origin = request.getHeader("Origin");

        if (allowedOrigins.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        } else {
            response.setHeader("Access-Control-Allow-Origin", allowedDomains[0]);
        }
        
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method,Access-Control-Request-Headers,Authorization, Cache-Control, Expires");
        if (request.getMethod()
            .equals(HttpMethod.OPTIONS.name())) {

            logger.info("BROWSER JUST SENT OPTIONS HEADER");

            response.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            chain.doFilter(req, res);
        }
        
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
