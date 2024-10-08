package bo.com.micrium.modulobase.security.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase ya no se utiliza
 * @author alepaco.maton
 * @deprecated
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558923243875L;
    
    private static final Logger log = LogManager.getLogger(JwtAuthenticationEntryPoint.class);
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        log.info("AuthenticationException " + authException.getMessage(), authException);
        
        HttpServletResponse httpResp = (HttpServletResponse) response;
        //httpResp.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        httpResp.setHeader("Access-Control-Max-Age", "1500");
        
        //httpResp.setHeader("Pragma", "no-cache");
        //httpResp.setHeader("Cache-Control", "no-cache");
        //response.setHeader("Cache-Control", "no-cache, no-store, max-age=604800, must-revalidate");
        response.setHeader("Cache-Control", "no-cache, must-understand, no-store, max-age=604800, must-revalidate, private");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("X-XSS-Protection", "1; mode=block");
        //response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline';");
        response.setHeader("X-Content-Type-Options", "nosniff");
        
        //response.setHeader("Content-Security-Policy", "script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline' 'unsafe-eval' https://fonts.googleapis.com https://fonts.googleapis.com; object-src 'self'; img-src 'self' data:; form-action 'self'; font-src 'self' https:; default-src 'self' http:;");
        response.setHeader("Content-Security-Policy", "script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline' 'unsafe-eval'; object-src 'self'; img-src 'self' data:; form-action 'self'; font-src 'self' https:; default-src 'self' http:;");
        
        Enumeration<String> headersEnum = ((HttpServletRequest) request).getHeaders("Access-Control-Request-Headers");
        StringBuilder headers = new StringBuilder();
        String delim = "";
        while (headersEnum.hasMoreElements()) {
            headers.append(delim).append(headersEnum.nextElement());
            delim = ", ";
        }
        httpResp.setHeader("Access-Control-Allow-Headers", headers.toString());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter res = response.getWriter();
        res.append("Unauthorized " + authException.getMessage());
        res.close();
    }
}
