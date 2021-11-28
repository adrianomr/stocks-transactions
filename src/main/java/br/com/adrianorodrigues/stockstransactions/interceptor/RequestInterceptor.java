package br.com.adrianorodrigues.stockstransactions.interceptor;

import br.com.adrianorodrigues.stockstransactions.interceptor.token.TokenContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    TokenContext tokenContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Bearer " + request.getHeader("Authorization"));
        tokenContext.fromBearerToken(request.getHeader("Authorization"));
        return true;
    }

}