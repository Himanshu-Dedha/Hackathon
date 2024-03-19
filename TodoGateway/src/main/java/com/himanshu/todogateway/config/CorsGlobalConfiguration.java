//package com.himanshu.todogateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.util.pattern.PathPatternParser;
//
//@Configuration
//public class CorsGlobalConfiguration {
//
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.addAllowedOrigin("*"); // Configure the allowed origins
//        corsConfig.addAllowedMethod("*"); // Configure the allowed methods
//        corsConfig.addAllowedHeader("*"); // Configure the allowed headers
//        corsConfig.setAllowCredentials(true); // Allow credentials
//        corsConfig.setMaxAge(3600L); // Max age
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
//        source.registerCorsConfiguration("/**", corsConfig); // Apply the configuration to all routes
//
//        return new CorsWebFilter(source);
//    }
//}
