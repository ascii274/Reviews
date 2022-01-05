package com.ascii274.webrequest.helper;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


/*
 @Component- Spring only pickup and register beans wwith
 @Service and @Repository doesn look in general
*/
@Component
public class HttpClientHelperStarWars {
    /*
    1. create an instance
    2. make a request
    3. handle the response
    */
    HttpClient httpClient;
    WebClient client;

    @Autowired // constructor injection with body-> important


    public HttpClientHelperStarWars() {
        httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5000,TimeUnit.MILLISECONDS))
                );
        client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public Mono<String> getDataString(URL url){
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);// 1:same
//    WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.post(); // 1: same
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(URI.create(url.toString()));
        return bodySpec.retrieve().bodyToMono(String.class);
    }


}
