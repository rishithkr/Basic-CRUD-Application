package com.example.crudSpringBootDemo.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.IOException;

//@Component
public class AlterRequestAndResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpServletResponse);

        BufferedReader reader = httpServletRequest.getReader();
        StringBuilder body = new StringBuilder();
        String line = reader.readLine();
        while (line != null){
            body.append(line);
            line= reader.readLine();
        }
        System.out.println(body);

        filterChain.doFilter(servletRequest, wrappedResponse);

        byte[] originalBodyInBytes = wrappedResponse.getContentAsByteArray();
        String originalBody = new String(originalBodyInBytes);
        String modifiedBody =
                """
                        "originalBody" : %s,
                        "app-name" : "Student-Management-System"
                        """.formatted(originalBody);
        wrappedResponse.resetBuffer();
        wrappedResponse.getWriter().write(modifiedBody);
        wrappedResponse.copyBodyToResponse();

    }
}
