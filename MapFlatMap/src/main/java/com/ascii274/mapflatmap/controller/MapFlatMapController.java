package com.ascii274.mapflatmap.controller;

import com.ascii274.mapflatmap.model.Persona;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.testng.AssertJUnit.assertEquals;

/*
source https://www.baeldung.com/java-difference-map-and-flatmap
*/
//http://localhost:8080/v1/
@RestController
@RequestMapping(value="/v1")

public class MapFlatMapController {

    private static final Logger Log = LoggerFactory.getLogger(MapFlatMapController.class);

    @GetMapping(value="/test-map")
    public String test(){
        return "Test mapflatmap";
    }


    @GetMapping(value="/map")
    public String testMap_1(){
        List<Integer> numbers = new ArrayList<>(asList(1,2,3,4));
        List<Integer>  numbersSquares = numbers
                .stream()
                .map( x -> x * x)
                .collect(Collectors.toList());

        Optional<String> str = Optional.of("test");

        //str2
        // pretty cumbersome better use fltamap( algo engoroso usar flatmap)
        //estructura anidad Optional<Optional<String>>

        assertEquals(Optional.of(Optional.of("STRING")),
            Optional
                .of("string")
                .map( str2 -> Optional.of("STRING")));

        return "Map:" + numbersSquares.toString() +  // [1.2.4.9.16]
                "\n" + str ;
    }

    @GetMapping(value = "/map-in-optional")
    public Optional<String> testMap_2(){
        Optional<String> s = Optional.of("test");
        assertEquals(Optional.of("TEST"), s.map(String::toUpperCase)); //assertEquals(expected, actual)
        return s;
    }

    @GetMapping(value = "/flatmap-in-optional")
    public Optional<String> testFlatMap(){
        Optional<String> s = Optional.of("test");
        assertEquals(Optional.of("STRING") ,Optional
                .of("string")
                .flatMap(s2 -> Optional.of("STRING"))
        );
        return s;
    }

    /**
     * Streams
     */

    @GetMapping(value = "/map-stream")
    public List<String> convertStringToUpperCaseStreams() {
        List<String> collected = Stream.of("a", "b", "hello") // Stream of String
                .map(String::toUpperCase) // Returns a stream consisting of the results of applying the given function to the elements of this stream.
                .collect(Collectors.toList());
        assertEquals(asList("A", "B", "HELLO"), collected);
        return collected; //return ["A","B","HELLO"]
    }

    @GetMapping(value = "/map-stream-2")
    public List<List<String>>  listOfList(){
        List<List<String>> list = Arrays.asList(
          Arrays.asList("a","b"),
          Arrays.asList("b","c"));
          return list; // return [ ["a","b"] ["c","d"]]
    }

    @GetMapping(value = "/flatmap-stream")
    public List<String> listOfListFltMap(){
//        List<List<String>> list = listOfList();
        List<List<String>> list = Arrays.asList(
                Arrays.asList("1","2"),
                Arrays.asList("3","4"));
        return list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList()); // return flattened ["1","2","3","4"]
    }

    // video mitocode
    @GetMapping(value="/react")
    public void reactor(){
        Mono.just(new Persona(1,"Joel",45))
                .doOnNext( p-> { // uso de mas de una linea
                    Log.info("[reactor] Persona" + p); // sin subscribe no lo muestra, usar depuracion
                })
                .subscribe(p->Log.info("[reactor] Persona" + p)); // para datos finales
    }

    @GetMapping(value="/rxjava2")
    public void rxjava2(){
        Observable.just(new Persona(1,"Joel",45))
                .doOnNext(p->Log.info("[reactor] Persona" + p))
                .subscribe(p->Log.info("[reactor] Persona" + p));
    }

    @GetMapping(value="/flux")
    public void flux(){
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1,"Analyn",45));
        personas.add(new Persona(2,"Shiva",8));
        personas.add(new Persona(3,"Joel",47));
        Flux.fromIterable(personas).subscribe(p -> Log.info(p.toString())); // proces asincrono

    }

    @GetMapping(value="/mono")
    public void mono() {
        Mono.just(new Persona(1,"Analyn",45))
                .subscribe( p -> Log.info(p.toString())); // para enterarme lo que pasa con Mono.
    }

    //https://www.youtube.com/watch?v=jJeYYiqhOTg&list=PLvimn1Ins-41pwh18gh_ZkxPOkrEEhXz6&index=5
    @GetMapping(value="/flux-mono")
    public void fluxMono(){
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1,"Analyn",45));
        personas.add(new Persona(2,"Shiva",8));
        personas.add(new Persona(3,"Joel",47));
        Flux<Persona> fx = Flux.fromIterable(personas);
        fx.collectList().subscribe(lista -> Log.info(lista.toString()));

    }

}
