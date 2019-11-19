package com.pfa.fatboar.FatboarBack.oauth2;

import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.utilities.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.pfa.fatboar.FatboarBack.common.Constants.homeUrl;


@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        if (response.isCommitted()) {
            return;
        }

        Object objectPrincipal = authentication.getPrincipal();
        String email = null;

        if (objectPrincipal instanceof DefaultOidcUser) { // gooogle
            email = (String) ((DefaultOidcUser) objectPrincipal) .getAttributes().get("email");
        } else if (objectPrincipal instanceof DefaultOAuth2User) { // facebook
            email = (String) ((DefaultOAuth2User) objectPrincipal).getAttributes().get("email");
        } else {
            throw new IllegalArgumentException("Unknown userPrincipal type.");
        }

        String token = jwtTokenUtil.generateToken(email);

        // TODO
        // response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);

        String redirectionUrl = UriComponentsBuilder.fromUriString(homeUrl)

                .queryParam("auth_token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);

    }
}
