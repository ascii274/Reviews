package com.ascii274.lambdas.controller;


import com.ascii274.lambdas.model.BarcelonaZones;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/lambdas/v1")
public class LambdasController {

    @GetMapping(value = "/test")
    public String test(){
        return "Hello lambdas";
    }


    @GetMapping(value="/search")
    public Mono<BarcelonaZones> search_zone(@RequestBody BarcelonaZones barcelonaZones){
        return Mono.just(barcelonaZones);

    }

    @GetMapping(value="/compare-numbers")
    public Mono<String> search_zone2(@RequestBody BarcelonaZones barcelonaZones){
        List<String> mySearch = Arrays.asList("New","Las");
        List<String> cities = Arrays.asList("Sydney","Dhaka","New York","London","Las islas");

//        barcelonaZones.getZones().forEach(x -> return x); // functional
//        List<String> find =mySearch.stream().anyMatch( a -> cities.contains(mySearch.get(0) ));
                //anyMatch(a -> LISTA.contains(a)); (edited)
//        List<String> find = cities.stream().filter(  );
//        mySearch.forEach(x->System.out.println( x )); // functional
//        return Mono.just(find.toString());
        return Mono.justOrEmpty(null);

    }


}
