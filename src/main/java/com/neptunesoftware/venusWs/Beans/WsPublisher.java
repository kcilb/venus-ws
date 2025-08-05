package com.neptunesoftware.venusWs.Beans;

import com.neptunesoftware.venusWs.Controller.XChannel;
import jakarta.annotation.PostConstruct;
import jakarta.xml.ws.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class WsPublisher {

    @Value("${app.venus.url}")
    private String endpointUrl;

    @Value("${app.venus.provider.url}")
    public String providerUrl;

    private static String providerApi;

    @PostConstruct
    public void publish() {
        providerApi = providerUrl;
        Endpoint.publish(endpointUrl, new XChannel());
    }

    public static String getProviderApi() {
        return providerApi;
    }

}
