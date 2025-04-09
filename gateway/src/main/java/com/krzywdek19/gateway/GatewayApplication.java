package com.krzywdek19.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("STUDENT-SERVICE", r ->  r
                        .path("/api/students/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8002"))
                .route("COURSE-SERVICE", r -> r
                        .path("/api/courses/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8004"))
                .build();
    }
}
