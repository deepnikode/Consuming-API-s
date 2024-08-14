package com.deep.stockclient;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
public class WebClientStockClient {

    private final WebClient webClient;

    public WebClientStockClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Users> getAllUsers() {
        return webClient.get()
                .uri("http://192.168.1.7:90/api/users")
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse ->
                        Mono.error(new RuntimeException("API error: " + clientResponse.statusCode())))
                .bodyToMono(ApiResponse.class) // Wrap response in ApiResponse
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getUsers())) // Extract user list
                .doOnError(e -> log.error("Error fetching users: ", e))
                .doOnComplete(() -> log.info("Successfully fetched users"));
    }
}
