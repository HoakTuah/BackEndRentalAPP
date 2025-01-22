package com.openclassroom.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

// ----------------------------------------------------------------------------------------
// Filter for handling Cross-Origin Resource Sharing (CORS)
// Handles cross-origin requests from the frontend application
// ----------------------------------------------------------------------------------------

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    // ----------------------------------------------------------------------------------------
    // Filter Implementation
    // ----------------------------------------------------------------------------------------

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // ----------------------------------------------------------------------------------------
        // Set CORS headers
        // ----------------------------------------------------------------------------------------

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200"); // Allowed origin
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // Allowed methods
        response.setHeader("Access-Control-Max-Age", "3600"); // Cache preflight results for 1 hour
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, content-type"); // Allowed
                                                                                                             // headers
        response.setHeader("Access-Control-Allow-Credentials", "true"); // Allow credentials

        // ----------------------------------------------------------------------------------------
        // Handle preflight requests
        // ----------------------------------------------------------------------------------------

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}