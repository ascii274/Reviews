package com.ascii274.webflux.repo;

import com.ascii274.webflux.model.Persona;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPersonaRepo {
//    void iniciarListPersona(List<Persona> list);
    Flux<Persona> registrar (Persona per);
    Mono<Persona> modificar (Persona per);
    Flux<Persona> listar();
    Mono<Persona> listarPorId(Integer id);
    Mono<Persona> eliminar(Integer id);
    Mono<Persona> eliminar2(Integer id);

}
