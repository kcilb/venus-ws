package com.neptunesoftware.venusWs.Beans;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    public String ws;

    @Value("${app.venus.provider.url}")
    public String endpointUrl;
}
