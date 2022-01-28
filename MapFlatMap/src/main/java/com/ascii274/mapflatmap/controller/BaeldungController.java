package com.ascii274.mapflatmap.controller;

/**
 * source:
 * https://github.com/eugenp/tutorials/blob/master/reactor-core/src/test/java/com/baeldung/mono/MonoUnitTest.java
 * https://www.baeldung.com/java-string-from-mono
 * http://localhost:8080/v2/
 */


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.AssertJUnit.assertEquals;

@Controller
@RequestMapping(value = "/v2")
public class BaeldungController {

    private static final Logger Log = LoggerFactory.getLogger(MapFlatMapController.class);

    @GetMapping(value = "/block-hello-world")
    @Test
    public void monoProducesString_thenBlockAndConsume(){
        String result1 = blockingHelloWorld().block();
        assertEquals("Hello World",result1);

        String result2 = blockingHelloWorld()
                .block(Duration.of(1000, ChronoUnit.MILLIS));
        assertEquals("Hello World", result1);

        Optional<String> result3 = Mono.<String>empty().blockOptional();
        assertEquals(Optional.empty(),result3);

        Log.info("\nLog result1:" + result1 + ", log result2:" + result2 + ", log rsult3:" + result3);
//        return result1;
    }

    @Test
    public void monoProduceString_thenConsumeBlocking(){
        blockingHelloWorld()
                .doOnNext( result-> Log.info("With doOnNext and subscribe:" +result) )
                .subscribe();
        blockingHelloWorld()
                .subscribe(result->Log.info("With only subscribre:"+ result ) );

        blockingHelloWorld()
                .subscribe(x->System.out.println("Wit println:" +x));
    }

    private Mono<String> blockingHelloWorld(){
        return Mono.just("Hello World");
    }

    // #################################   #################################

    @Test
    public void monoProducesListElements_thenConvertToFluxOfElements(){

        Mono<List<String>> monoList = monoOfList();

        StepVerifier.create(monoTofluxUsingFlatMapIterable(monoList))
                .expectNext("One","Two","Three","Four")
                .verifyComplete();

        StepVerifier.create(monoTofluxUsingFlatMapMany(monoList))
                .expectNext("One","Two","Three","Four")
                .verifyComplete();
    }

    private <T> Flux<T> monoTofluxUsingFlatMapIterable(Mono<List<T>> monoList){
        return monoList
                .flatMapIterable(list->list)
                .log();
    }

    private <T> Flux<T> monoTofluxUsingFlatMapMany(Mono<List<T>> monoList){
        return monoList
                .flatMapMany(Flux::fromIterable)
                .log();
    }

    // #################################   #################################


    private Mono<List<String>> monoOfList(){ //private Mono<?> monoOfList(){
        List<String> list = new ArrayList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.add("Four");
        return Mono.just(list);
    }

    @Test
    public void whenEmptyList_thenMonoDeferExecuted(){

        Mono<List<String>> emptyList = Mono.defer(()-> monoOfEmptyList() );
        Flux<String> emptyListElements = emptyList.flatMapIterable(l->l)
                .switchIfEmpty(Mono.defer(() -> sampleMsg("EmptyList")))
                .log();
        StepVerifier.create(emptyListElements)
                .expectNext("EmptyList")
                .verifyComplete();
    }


    @Test
    public void whenNonEmptyList_thenMonoDeferNotExecuted() {

        Mono<List<String>> nonEmptyist = Mono.defer(() -> monoOfList());

        //Non empty list, hence Mono publisher in switchIfEmpty won't evaluated.
        Flux<String> listElements = nonEmptyist.flatMapIterable(l -> l)
                .switchIfEmpty(Mono.defer(() -> sampleMsg("NonEmptyList")))
                .log();

        StepVerifier.create(listElements)
                .expectNext("One", "Two", "Three", "Four")
                .verifyComplete();
    }

    private Mono<List<String>> monoOfEmptyList(){
        List<String> list = new ArrayList<>();
        return Mono.just(list);
    }

    private Mono<String> sampleMsg(String str){
        Log.debug("Call to retrieve sambple message!! -->{} at: {}",str,System.currentTimeMillis());
        return Mono.just(str);
    }
}



