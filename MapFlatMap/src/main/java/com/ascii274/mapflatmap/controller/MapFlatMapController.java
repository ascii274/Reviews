package com.ascii274.mapflatmap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.testng.AssertJUnit.assertEquals;

/*
source https://www.baeldung.com/java-difference-map-and-flatmap
*/

@RestController
@RequestMapping(value="/v1")

public class MapFlatMapController {

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




}
