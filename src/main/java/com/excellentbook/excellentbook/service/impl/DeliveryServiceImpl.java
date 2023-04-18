package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.delivery.PostOfficeDetailsDto;
import com.excellentbook.excellentbook.dto.delivery.external.NovaPoshtaDtoRequest;
import com.excellentbook.excellentbook.dto.delivery.external.NovaPoshtaDtoResponse;
import com.excellentbook.excellentbook.dto.delivery.external.NovaPoshtaPropertiesDto;
import com.excellentbook.excellentbook.exception.WebClientGenericException;
import com.excellentbook.excellentbook.service.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final WebClient webClient;
    private final ModelMapper mapper;
    @Value("${novaPoshta.server.address}")
    private String novaPoshtaServerAddress;

    @Value("${novaPoshta.apiKey}")
    private String novaPoshtaApiKey;

    public DeliveryServiceImpl(WebClient webClient, ModelMapper mapper) {
        this.webClient = webClient;
        this.mapper = mapper;
    }

    @Override
    public PostOfficeDetailsDto getAllAvailablePostOffices(String city, String pageNumber, String pageSize) {
        NovaPoshtaDtoRequest requestBody = new NovaPoshtaDtoRequest();
        requestBody.setApiKey(novaPoshtaApiKey);

        NovaPoshtaPropertiesDto novaPoshtaProperties = new NovaPoshtaPropertiesDto();
        novaPoshtaProperties.setCityName(city);
        novaPoshtaProperties.setPage(pageNumber);
        novaPoshtaProperties.setLimit(pageSize);
        requestBody.setMethodProperties(novaPoshtaProperties);

        NovaPoshtaDtoResponse webClientResponse;
        try {
            log.info("Request: Nova Poshta API with param: queryParam(city: {})", city);
            webClientResponse = webClient.post()
                    .uri(novaPoshtaServerAddress)
                    .body(Mono.just(requestBody), NovaPoshtaDtoRequest.class)
                    .retrieve()
                    .bodyToMono(NovaPoshtaDtoResponse.class)
                    .timeout(Duration.ofSeconds(6))
                    .onErrorMap(TimeoutException.class, e -> new TimeoutException("Error occurred in 3rd party API"))
                    .block();
            log.info("Response: Nova Poshta API with param: queryParam(city: {}) was successful", city);
        } catch (WebClientResponseException e) {
            log.error("Response: Nova Poshta API unable to retrieve data with param: queryParam(city: {}), message: {}",
                    city, e.getMessage());
            throw new WebClientGenericException("Error occurred in 3rd party API");
        }
        return mapper.map(webClientResponse, PostOfficeDetailsDto.class);
    }
}
