package com.example.SpringBootFilters.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Order(2)
public class LoggingFilter implements Filter  {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestId = UUID.randomUUID().toString();

        System.out.println("Incoming request: " + httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI() + " entered logging filter at: " + LocalDateTime.now() + " with ID: " + requestId);
        httpServletResponse.setHeader("x-request-id", requestId);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception e){
            System.out.println("Exception occurred: " + e.getMessage());
        }

        finally {
        System.out.println("Request: " +httpServletResponse.getHeader("x-request-id")+ " exited logging filter at: " + LocalDateTime.now() + " with status: " + httpServletResponse.getStatus() );
        }
    }

}
