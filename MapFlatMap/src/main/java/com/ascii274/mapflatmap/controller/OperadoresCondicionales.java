package com.ascii274.mapflatmap.controller;

import com.ascii274.mapflatmap.model.Persona;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequestMapping(value = "v4")
public class OperadoresCondicionales {
    private static final Logger Log = LoggerFactory.getLogger(OperadoresCondicionales.class);

    public List<Persona> getPersonas1(){
        List<Persona> personas1 = new ArrayList<>();
        personas1.add(new Persona(1,"Analyn",45));
        personas1.add(new Persona(2,"Shiva",8));
        personas1.add(new Persona(3,"Joel",47));
        return personas1;
    }

//    @GetMapping(value="/default-empty")
    @Test
    public void defaultIfEmpty(){
        // por si alguna salida que vien en una base de datos y nos lo devuelve vacía.
        Mono.empty()
                .defaultIfEmpty(new Persona(0,"DEFAULT",99))
                .subscribe(x -> Log.info("Mono:" + x.toString()));

        Flux.empty()
                .defaultIfEmpty(new Persona(0,"DEFAULT",99))
                .subscribe(x -> Log.info("Flux:" + x.toString()));

        // instancia Mono con valor dentro, ejecuta solo new Persona ( muestra )
        Mono.just(new Persona(1,"Asci274",45))
                .defaultIfEmpty(new Persona(0,"DEFAULT",99))
                .subscribe(x -> Log.info("Mono con instancia:" + x.toString()));
    }

    @Test
    public void takeUntil(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .takeUntil(p->p.getEdad() > 27) // muestra solo el primero que encuentra
                .subscribe(x -> Log.info(x.toString()));
    }

    /**
     * No hace nada
     * No ejecuta el thread, pendiente de ver en modo consola.
     * @throws InterruptedException
     */
    @Test
    public void timeOut() throws InterruptedException {
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .delayElements(Duration.ofSeconds(3))
                .timeout(Duration.ofSeconds(2))
                .subscribe(x -> Log.info(x.toString()));
        Thread.sleep(1000);
    }

    @Test
    public void average(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .collect(Collectors.averagingInt(Persona::getEdad))
                .subscribe(x -> Log.info(x.toString()));
    }

    @Test
    public void count(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .count()
                .subscribe( x -> Log.info("Cantidad:" + x));
    }

    @Test
    public void min(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .collect(Collectors.minBy(Comparator.comparing(Persona::getEdad)))
                .subscribe( p -> Log.info(p.get().toString()));

    }

    @Test
    public void sum(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .collect(Collectors.summingInt(Persona::getEdad))
                .subscribe( x -> Log.info("Suma:" + x));
    }

    @Test
    public void summarizing(){
        List<Persona> personas1 = getPersonas1();
        Flux.fromIterable(personas1)
                .collect(Collectors.summarizingInt(Persona::getEdad))
                .subscribe( x -> Log.info("Summarizing:" + x));


    }


}
