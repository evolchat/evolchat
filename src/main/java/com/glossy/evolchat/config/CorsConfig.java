package com.glossy.evolchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 특정 origin을 허용할 경우
        config.addAllowedOrigin("http://localhost:8080");
//        config.addAllowedOrigin("https://example.com");

        // 모든 HTTP 메서드를 허용할 경우
        config.addAllowedMethod("*");

        // 필요한 헤더를 허용할 경우
        config.addAllowedHeader("*");

        // credentials 허용 여부
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
