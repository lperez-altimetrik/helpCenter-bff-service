package com.helpcenter.bff.config;

import com.okta.sdk.resource.client.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OktaConfig {

    @Value("${okta.client.orgUrl}")
    private String orgUrl;

    @Value("${okta.client.token}")
    private String token;

    @Bean
    public ApiClient oktaApiClient() {
        return new ApiClient()
                .setBasePath(orgUrl)
                .setApiKey(token);
    }
}
