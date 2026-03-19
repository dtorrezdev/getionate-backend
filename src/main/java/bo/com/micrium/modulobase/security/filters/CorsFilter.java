package bo.com.micrium.modulobase.security.filters;

import bo.com.micrium.logger.LoggerMain;
import bo.com.micrium.modulobase.security.interceptor.LoggerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

@Configuration
public class CorsFilter implements WebMvcConfigurer {


    @Value("${spring.client.url}")
    private String clientUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        String[] urls = clientUrl.split(",");

        registry.addMapping("/**")
                .allowedOrigins(urls)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor());
    }
}
