package com.example.percentage.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Configuration
@Profile("local")
public class WireMockServerConfig {
    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        WireMockConfiguration config = WireMockConfiguration.options()
                .port(8081);

        WireMockServer server = new WireMockServer(config);

        server.stubFor(get(urlEqualTo("/api/percentage"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("10")
                )
        );
        return server;
    }
}