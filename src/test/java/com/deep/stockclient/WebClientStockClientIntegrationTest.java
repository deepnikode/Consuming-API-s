package com.deep.stockclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


class WebClientStockClientIntegrationTest {


    private WebClient webClient= WebClient.builder().build();

    @Test
    void shouldRetrieveStockPricesFromTheService()
    {

        WebClientStockClient webClientStockClient = new WebClientStockClient(webClient);
        Flux<Users> prices = webClientStockClient.getAllUsers();

        Assertions.assertNotNull(prices);
        Assertions.assertTrue(prices.take(5).count().block() > 0);
    }
}
