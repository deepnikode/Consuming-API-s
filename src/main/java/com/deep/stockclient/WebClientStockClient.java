package com.deep.stockclient;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
@Log4j2
public class WebClientStockClient
{
    private WebClient webClient;

    public WebClientStockClient(WebClient webClient)
    {
        this.webClient = webClient;
    }

    public Flux<StockPrize> pricesFor(String symbol)
    {
        return webClient.get()
                .uri("http://192.168.1.7:90/api/user")
                .retrieve()
                .bodyToFlux(StockPrize.class)
                .doOnError(IOException.class,e -> log.error(e.getMessage()));

    }
}
