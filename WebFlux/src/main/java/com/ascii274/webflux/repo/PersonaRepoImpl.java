package com.ascii274.webflux.repo;

import com.ascii274.webflux.config.LoadDataPersona;
import com.ascii274.webflux.model.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class PersonaRepoImpl implements IPersonaRepo{

//    @Autowired
    LoadDataPersona config = new LoadDataPersona();

    private static final Logger Log = LoggerFactory.getLogger(PersonaRepoImpl.class);
    private List<Persona> personas = config.loadDataPersona();

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }



    @Override
    public Flux<Persona> registrar(Persona per) {
        Log.info((per.toString()));
        personas.add(per);
        return Flux.fromIterable(personas);
    }

    @Override
    public Mono<Persona> modificar(Persona per) {
        Log.info((per.toString()));
        return Mono.just(per);
    }

    @Override
    public Flux<Persona> listar() {
        return Flux.fromIterable(personas);
    }

    @Override
    public Mono<Persona> listarPorId(Integer id) {
        return Mono.just(new Persona(id,"Ascii"));
    }

    /**
     * Solo devuelve un Mono vacio.
     * @param id
     * @return
     */
    @Override
    public Mono<Persona> eliminar(Integer id) {
//        return Mono.empty();
        //deve
        personas.removeIf(x->x.getIdPersona()==id);
        return Mono.just(new Persona(id,"DELETED PERSONA"));
    }

    @Override
    public Mono<Persona> eliminar2(Integer id) {
        return Mono.just(new Persona(id,"DELETED PERSONA"));
    }


}
