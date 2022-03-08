package com.ascii274.webflux.config;

import com.ascii274.webflux.model.Persona;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadDataPersona {

    public List<Persona> loadDataPersona() {
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1,"Analyn"));
        personas.add(new Persona(2,"Shiva"));
        personas.add(new Persona(3,"Joel"));
        return personas;

    }

}
