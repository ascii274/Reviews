package com.ascii274.mapflatmap.controller;

import com.ascii274.mapflatmap.model.Persona;
import com.ascii274.mapflatmap.model.Venta;
import io.reactivex.Observable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.testng.AssertJUnit.assertEquals;

/**
 * source:
 * https://www.baeldung.com/java-difference-map-and-flatmap
 * https://www.youtube.com/watch?v=yZGrmg-2-qQ&list=PLvimn1Ins-41pwh18gh_ZkxPOkrEEhXz6&index=8
 * http://localhost:8080/v1/
*/

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

    @Test
    public void flatMapPersona(){
        // fltamap sirve para manipular datos
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1,"Analyn",45));
        personas.add(new Persona(2,"Shiva",8));
        personas.add(new Persona(3,"Joel",47));
        Flux.fromIterable(personas) // iteramos por cada elmento
                .flatMap(p->{
                    p.setEdad(p.getEdad() + 10);
                    return Mono.just(p); //retornamos otro flujo de datos
                        })
                .subscribe(p->Log.info(p.toString())); // mostramos contenido después de la transformación
    }

    @Test
    public void groupBy(){
        // prueba de cambiar el idPersona para cambiar el resultado de la agrupacion
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1,"Analyn",45));
        personas.add(new Persona(1,"Shiva",8));
        personas.add(new Persona(1,"Joel",47));
        Flux.fromIterable(personas)
//                .groupBy(p->p.getIdPersona()) // (Persona::getIdPersona)
                // refactorizacion método referencia, agrupamos por IdPersona en diferentes flux
                .groupBy(Persona::getIdPersona)
                // recolectamos como una lista
                .flatMap(idFlux->idFlux.collectList())
                .subscribe(x->Log.info(x.toString()));
    }

    @Test
    public void filter() {
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1,"Analyn",45));
        personas.add(new Persona(1,"Shiva",8));
        personas.add(new Persona(1,"Joel",47));
        Flux.fromIterable(personas)
                .filter(p->p.getEdad()>28)
                .subscribe(p->Log.info(p.toString()));
    }

    /**
     * distinct(); Es bueno para controlar los valores repetidos
     * Ojo para filtrar @Override Hash and equals en class Persona
     */
    @Test
    public void distinct(){
        Flux.fromIterable(List.of(1,1,2,2))
                .distinct()
                .subscribe(p -> Log.info(p.toString()));

        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1,"Analyn",45));
        personas.add(new Persona(1,"Shiva",8));
        personas.add(new Persona(3,"Joel",47));
        Flux.fromIterable(personas)
                .distinct()
                .subscribe(p -> Log.info( p.toString()));
    }

    @Test
    public void take(){
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1,"Analyn",45));
        personas.add(new Persona(1,"Shiva",8));
        personas.add(new Persona(3,"Joel",47));
        Flux.fromIterable(personas)

                // del flujo se coge el elmento de inicio hasta take. En este caso los dos primeros elementos del flujo de datos
                .take(2)
                .subscribe(p -> Log.info("take:" + p.toString()));

        Flux.fromIterable(personas)
                // del flujo se coge el elmento partiendo desde el final hasta el inicio. En este caso los dos primeros elementos comenzado por el final
                .takeLast(2)
                .subscribe(p -> Log.info("takelast:" + p.toString()));

        Flux.fromIterable(personas)
                // Evitamos el primer elemento del flujo
                .skip(1)
                .subscribe(p -> Log.info("skip:" + p.toString()));

        Flux.fromIterable(personas)
                // Evitamos el primer elemento del flujo
                .skipLast(1)
                .subscribe(p -> Log.info("skip:" + p.toString()));

    }

    @Test
    public void merge(){
        List<Persona> personas1 = new ArrayList<>();
        personas1.add(new Persona(1,"Analyn",45));
        personas1.add(new Persona(2,"Shiva",8));
        personas1.add(new Persona(3,"Joel",47));

        List<Persona> personas2 = new ArrayList<>();
        personas2.add(new Persona(4,"Pedro",45));
        personas2.add(new Persona(5,"Juan",8));
        personas2.add(new Persona(6,"Marta",47));

        List<Venta> ventas = new ArrayList<>();
        ventas.add(new Venta(1, LocalDateTime.now()));

        Flux<Persona> flux1 = Flux.fromIterable(personas1);
        Flux<Persona> flux2 = Flux.fromIterable(personas2);
        Flux<Venta> fx3 = Flux.fromIterable(ventas);
        Flux.merge(flux1,flux2,fx3)
                .subscribe(p -> Log.info(p.toString()));
    }

    //https://www.youtube.com/watch?v=89tks2M46uY&list=PLvimn1Ins-41pwh18gh_ZkxPOkrEEhXz6&index=8
    @Test
    public void zip(){
        List<Persona> personas1 = new ArrayList<>();
        personas1.add(new Persona(1,"Analyn",45));
        personas1.add(new Persona(2,"Shiva",8));
        personas1.add(new Persona(3,"Joel",47));

        List<Persona> personas2 = new ArrayList<>();
        personas2.add(new Persona(4,"Pedro",33));
        personas2.add(new Persona(5,"Juan",22));
        personas2.add(new Persona(6,"Marta",11));

        List<Venta> ventas = new ArrayList<>();
        ventas.add(new Venta(1, LocalDateTime.now()));

        Flux<Persona> flux1 = Flux.fromIterable(personas1);
        Flux<Persona> flux2 = Flux.fromIterable(personas2);
        Flux<Venta> flux3 = Flux.fromIterable(ventas);
        /*
        Flux.zip(flux1, flux2, (p1,p2) -> String.format("Flux1: %s, Flux2:%s",p1,p2))
                .subscribe(x -> Log.info(x));
        */
        Flux.zip(flux1,flux2,flux3)
                .subscribe( x -> Log.info(x.toString()));
    }

    @Test
    public void zipWith(){
        List<Persona> personas1 = new ArrayList<>();
        personas1.add(new Persona(1,"Analyn",45));
        personas1.add(new Persona(2,"Shiva",8));
        personas1.add(new Persona(3,"Joel",47));

        List<Persona> personas2 = new ArrayList<>();
        personas2.add(new Persona(4,"Pedro",33));
        personas2.add(new Persona(5,"Juan",22));
        personas2.add(new Persona(6,"Marta",11));

        List<Venta> ventas = new ArrayList<>();
        ventas.add(new Venta(1, LocalDateTime.now()));

        Flux<Persona> flux1 = Flux.fromIterable(personas1);
        Flux<Persona> flux2 = Flux.fromIterable(personas2);
        Flux<Venta> flux3 = Flux.fromIterable(ventas);

        flux1.zipWith(flux2, (p1,p2) -> String.format("Flux1: %s, Flux2:%s",p1,p2))
                .subscribe(x -> Log.info(x));

    }


}
