package com.etech7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.etech7.dto.ConnectWiseClients;
import com.etech7.dto.Integrations;
import com.etech7.dto.MSPConnectWiseManageDetails;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DBService {

    private String dbsUrl;

    private final WebClient webClient;

    @Autowired
    public DBService(WebClient.Builder webClientBuilder, @Value("${dbsUrl:}") String dbsUrl) {
        this.dbsUrl = dbsUrl;

        if (dbsUrl.isEmpty()) {
            throw new IllegalStateException("dbsUrl is not configured. Please check your application properties.");
        }
        this.webClient = webClientBuilder.baseUrl(this.dbsUrl).build();
    }

    public Mono<Integrations> getIntegration(String mspCustomDomain) {
        log.info("Attempting to connect to: " + dbsUrl + "/" + mspCustomDomain + "/integrations");
        return webClient.get()
                .uri("/{mspCustomDomain}/integrations", mspCustomDomain)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> 
                    Mono.error(new RuntimeException("Error: " + clientResponse.statusCode()))
                )
                .bodyToMono(Integrations.class);
    }

    public Mono<Integrations> updateIntegration(String mspCustomDomain, Integrations integrations) {
        log.info("Attempting to connect to: " + dbsUrl + "/" + mspCustomDomain + "/integrations/update");
        return webClient.post()
                .uri("/{mspCustomDomain}/integrations/update", mspCustomDomain)
                .bodyValue(integrations)
                .retrieve()
                .bodyToMono(Integrations.class);
    }
    
    
    public Mono<MSPConnectWiseManageDetails> getMSPConnectWiseManageDetails(String mspCustomDomain) {
        log.info("Attempting to connect to: " + dbsUrl + "/" + mspCustomDomain + "/connectWiseManageDetails");
        return webClient.get()
                .uri("/{mspCustomDomain}/connectWiseManageDetails", mspCustomDomain)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> 
                    Mono.error(new RuntimeException("Error: " + clientResponse.statusCode()))
                )
                .bodyToMono(MSPConnectWiseManageDetails.class);
    }
    
    public Mono<List<ConnectWiseClients>> getConnectWiseClients(String mspCustomDomain) {
        log.info("Attempting to connect to: " + dbsUrl + "/" + mspCustomDomain + "/connectWiseClients");
        return webClient.get()
                .uri("/{mspCustomDomain}/connectWiseClients", mspCustomDomain)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> 
                    Mono.error(new RuntimeException("Error: " + clientResponse.statusCode()))
                )
                .bodyToMono(new ParameterizedTypeReference<List<ConnectWiseClients>>() {});
    }


    public Mono<MSPConnectWiseManageDetails> updateMSPConnectWiseManageDetails(String mspCustomDomain, MSPConnectWiseManageDetails mspConnectWiseManageDetails) {
        log.info("Attempting to connect to: " + dbsUrl + "/" + mspCustomDomain + "/connectWiseManageDetails/update");
        return webClient.post()
                .uri("/{mspCustomDomain}/connectWiseManageDetails/update", mspCustomDomain)
                .bodyValue(mspConnectWiseManageDetails)
                .retrieve()
                .bodyToMono(MSPConnectWiseManageDetails.class);
    }
}
