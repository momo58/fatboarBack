package com.pfa.fatboar.FatboarBack.utilities;

import com.pfa.fatboar.FatboarBack.controllers.TicketController;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

import static com.pfa.fatboar.FatboarBack.common.Constants.TOKEN_EXPIRATION_MSEC;
import static com.pfa.fatboar.FatboarBack.common.Constants.TOKEN_SECRET;

@Component
public class TokenProvider {

    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        //Date now = new Date();
        Calendar now = Calendar.getInstance();
        Date expiryDate = new Date(now.getTimeInMillis() + TOKEN_EXPIRATION_MSEC);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
