package com.pfa.fatboar.FatboarBack.security;

import com.pfa.fatboar.FatboarBack.controllers.TicketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {

        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}
