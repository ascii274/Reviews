package com.ascii274.webflux.controller;

import com.ascii274.webflux.model.Persona;
import com.ascii274.webflux.repo.PersonaRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio Rest,  webflux trabaja de dos formas los servicios rest: enfoque annotaciones o enfoque funcional
 * source: https://www.youtube.com/watch?v=LpI0FyVyRpI&list=PLvimn1Ins-41pwh18gh_ZkxPOkrEEhXz6&index=13
 */

@RestController
@RequestMapping(value="/v1/web-flux/personas")
public class PersonaController {

    @Autowired
    PersonaRepoImpl personaRepoImp;

    private static final Logger Log = LoggerFactory.getLogger(PersonaController.class);

    @GetMapping(value = "/listar") // functional
    public Flux<Persona> listar(){
        return personaRepoImp.listar();
    }


    @GetMapping(value="listar/{id}") //functional
    public Mono<Persona> listarPorId(@PathVariable("id") Integer id){

        return personaRepoImp.listarPorId(id);
    }

    @PostMapping(value="/registrar") // functional
    public Flux<Persona> registrar(@RequestBody Persona per){
        return personaRepoImp.registrar(per);
    }



    @PutMapping (value="/actualizar") // functional
    public Mono<Persona> modificar(@RequestBody Persona per){
        return personaRepoImp.modificar(per);
    }

    @DeleteMapping(value="eliminar/{id}") // functional
    public Mono<Persona> eliminar(@PathVariable("id") Integer id){
        return personaRepoImp.eliminar(id);
    }


    @DeleteMapping(value="buscar-eliminar/{id}") // functional
    public Mono<Persona> buscarLuegoEliminar(@PathVariable("id") Integer id){
        return personaRepoImp.listarPorId(id) // devuelve un Mono de lo que ha encontrado
                .flatMap( p-> personaRepoImp.eliminar(p.getIdPersona())); // de lo que he encotrado hago una eliminación
    }


    // busca primero luego elimina
    @DeleteMapping(value="buscar-eliminar2/{id}") // functional
    public Mono<Persona> buscarLuegoEliminar2(@PathVariable("id") Integer id){
        return personaRepoImp.listarPorId(id) // devuelve un Mono de lo que ha encontrado
                .flatMap( p-> personaRepoImp.eliminar2(p.getIdPersona())); // de lo que he encotrado hago una eliminación
    }


    /**
     * DEVELOP
     */
    @GetMapping(value="/ver-link")
    public String listarPersonas(Model model){
        model.addAttribute("titulo","Titulo página listar.html");
        return "verlink";
    }

    @PostMapping(value="/getlistar")
    public String getListar(Model model){
        model.addAttribute("titulo","Titulo página listar.html");
        return "listar";

    }

}
