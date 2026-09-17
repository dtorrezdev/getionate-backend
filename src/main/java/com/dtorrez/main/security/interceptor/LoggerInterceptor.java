package com.dtorrez.main.security.interceptor;

import bo.com.micrium.logger.LoggerMain;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.security.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

public class LoggerInterceptor implements HandlerInterceptor {
    private static final String START_TIME = "startTime";

    private final CurrentUserProvider currentUserProvider;

    public LoggerInterceptor(CurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map<String, Object> result = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            String value = (values != null && values.length > 0) ? values[0] : "";
            result.put(key+" ", value);
        });

        String authHeader = request.getHeader("Authorization");
        String authType = "";
        String token = "";
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authType = "Bearer";
            token = authHeader.substring(7,14) + "...";
        }

        String username = currentUserProvider.getUsername();
        final String form = request.getHeader(JwtTokenUtil.ROUTE);
        final String tenantId = request.getHeader(JwtTokenUtil.TENANT_ID);

        result.put("url ", request.getRequestURL().toString());
        result.put("metodo ", request.getMethod());
        result.put("authType ", authType);
        result.put("token ", token);
        result.put("trazabilidad ", username);
        result.put("ipClient ", request.getRemoteAddr());
        result.put("form ", form);
        result.put("tenantId ", tenantId);
        request.setAttribute(START_TIME, System.currentTimeMillis());
        LoggerMain.printRequest(result);
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME);
        long duration = System.currentTimeMillis() - startTime;
        String username = currentUserProvider.getUsername();

        Map<String, Object> result = new HashMap<>();
        result.put("trazabilidad " , username);
        result.put("ipClient " , request.getRemoteAddr());
        result.put("size " , response.getBufferSize());
        result.put("status " , response.getStatus());
        result.put("timeResponse al endPoint  " , request.getRequestURI() + " tardo " + duration + " ms");
        LoggerMain.printResponse(result);
    }
}
