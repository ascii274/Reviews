package com.ascii274.lambdas.controller;


import com.ascii274.lambdas.model.BarcelonaZones;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
}
