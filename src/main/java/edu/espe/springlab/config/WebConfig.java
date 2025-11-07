package edu.espe.springlab.config;

import edu.espe.springlab.interceptor.RequestLoggingInterceptor; // Importa el Interceptor
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLoggingInterceptor loggingInterceptor; // Inyección de dependencia

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registra el interceptor y especifica qué rutas debe aplicar.
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/api/**");
    }
}