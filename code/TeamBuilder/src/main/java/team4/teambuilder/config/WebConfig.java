package team4.teambuilder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Specify the API endpoints
                .allowedOrigins("http://localhost:4200") // Allow requests from your Angular app
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify allowed HTTP methods
                .allowedHeaders("*"); // Allow all headers
    }
}