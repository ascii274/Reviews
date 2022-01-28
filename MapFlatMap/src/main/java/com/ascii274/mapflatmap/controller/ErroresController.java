package com.ascii274.mapflatmap.controller;

import com.ascii274.mapflatmap.model.Persona;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/v3")
public class ErroresController {
    private static final Logger Log = LoggerFactory.getLogger(ErroresController.class);


    public List<Persona> getPersonas1(){
        List<Persona> personas1 = new ArrayList<>();
        personas1.add(new Persona(1,"Analyn",45));
        personas1.add(new Persona(2,"Shiva",8));
        personas1.add(new Persona(3,"Joel",47));
        return personas1;
    }

    public List<Persona> getPersonas2(){
        List<Persona> personas2 = new ArrayList<>();
        personas2.add(new Persona(4,"Pedro",33));
        personas2.add(new Persona(5,"Juan",22));
        personas2.add(new Persona(6,"Marta",11));
        return personas2;
    }

    @Test
//    @GetMapping(value="/retry")
    public void retry(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .concatWith(Flux.error(new RuntimeException("UN ERROR")))
                .retry(1) // vuelve a lanzar una vez
                .doOnNext(x -> Log.info(x.toString()))
                .subscribe();
    }

    @Test
//    @GetMapping(value="/error-return")
    public void errorReturn(){
        List<Persona> personas1 = getPersonas1();
       Flux.fromIterable(personas1)
                .concatWith(Flux.error(new RuntimeException("UN ERROR")))
                .onErrorReturn(new Persona(0,"XYZ",99))
                .subscribe(x -> Log.info(x.toString()));
    }

    /**
     * https://www.youtube.com/watch?v=1So53aWJ9fE&list=PLvimn1Ins-41pwh18gh_ZkxPOkrEEhXz6&index=9
     */
    @Test
    public  void errorResume(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .concatWith(Flux.error(new RuntimeException("UN ERROR")))
                .onErrorResume(e -> Mono.just( new Persona(0,"XYZ",99)))
                .subscribe( x -> Log.info(x.toString()));

    }

    @Test
    public void errorMap(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .concatWith(Flux.error(new RuntimeException("UN ERROR")))
                .onErrorMap( e -> new InterruptedException(e.getMessage()))
                .subscribe(x -> Log.info(x.toString()));
    }



}
