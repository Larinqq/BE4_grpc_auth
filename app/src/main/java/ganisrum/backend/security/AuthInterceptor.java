package ganisrum.backend.security;

import ganisrum.backend.annotations.security.PublicApi;
import ganisrum.backend.domain.service.AuthService;
import ganisrum.backend.web.dto.response.UserValidateTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {


    private final AuthService authClient;
    private final ObjectMapper objectMapper;

    public AuthInterceptor(AuthService authClient, ObjectMapper objectMapper) {
        this.authClient = authClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        if (isPublic(handlerMethod)) {
            return true;
        }
        return authenticate(request, response, handlerMethod);
    }


    private boolean isPublic(HandlerMethod hm) {
        return hm.hasMethodAnnotation(PublicApi.class)
                || AnnotatedElementUtils.hasAnnotation(hm.getBeanType(), PublicApi.class);
    }

    private boolean authenticate(HttpServletRequest request,
                                 HttpServletResponse response,
                                 HandlerMethod hm) throws IOException, AuthenticationException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        UserValidateTokenResponse validation;
        validation = authClient.validateToken(header);
        request.setAttribute("userEmail", validation.email());
        return true;

    }

}