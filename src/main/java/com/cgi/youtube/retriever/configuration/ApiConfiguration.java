package com.cgi.youtube.retriever.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="youtube-api-credentials")
public class ApiConfiguration {

    private String dataApiUrl;
    private String secretKey;
    private String dataPart;
}
