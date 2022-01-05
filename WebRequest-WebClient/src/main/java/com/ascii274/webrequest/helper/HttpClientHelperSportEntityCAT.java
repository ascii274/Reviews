package com.ascii274.webrequest.helper;


import com.ascii274.webrequest.config.PropertiesConfig;
import com.ascii274.webrequest.dto.EntitySportCAT_DTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.function.Consumer;

@Component
public class HttpClientHelperSportEntityCAT {
    private PropertiesConfig propertiesConfig;
    WebClient webClient;

    public HttpClientHelperSportEntityCAT(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
        webClient = WebClient.builder()
//                .baseUrl("base url");
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url",propertiesConfig.getDataEntitatEsportivaCAT()))
                .codecs(consumer) // size control
                // code block comment below  // size control like ".codecs(consumer)"
                /*
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(30000000)) //maxInMemorySize(30 * 1024 * 1024))
                        .build())
                 */
                .build();
    }

    final Consumer<ClientCodecConfigurer> consumer = configurer->{
        final ClientCodecConfigurer.ClientDefaultCodecs codecs = configurer.defaultCodecs();
//        codecs.maxInMemorySize(30000000); // TODO: apply in application.yml
        codecs.maxInMemorySize(30 * 1024 * 1024);
    };

    public Mono<EntitySportCAT_DTO[]> getData(){
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(HttpMethod.GET);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(URI.create(propertiesConfig.getDataEntitatEsportivaCAT()));
        return bodySpec.retrieve().bodyToMono(EntitySportCAT_DTO[].class);
    }



}
