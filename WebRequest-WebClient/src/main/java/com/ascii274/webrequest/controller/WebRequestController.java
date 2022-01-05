package com.ascii274.webrequest.controller;

import com.ascii274.webrequest.config.PropertiesConfig;
import com.ascii274.webrequest.dto.EntitySportCAT_DTO;
import com.ascii274.webrequest.helper.HttpClientHelperSportEntityCAT;
import com.ascii274.webrequest.helper.HttpClientHelperStarWars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping(value="v1/")
public class WebRequestController {
    @Autowired
    HttpClientHelperStarWars httpClientHelperStarWars;
    @Autowired
    HttpClientHelperSportEntityCAT httpClientHelperSportEntityCAT;

    @Autowired
    PropertiesConfig propertiesConfig;

    @GetMapping(value="/test")
    public String test(){
        return "Test";
    }


    @GetMapping(value="/starwars/vehicles")
    public Mono<String> getVehicles(){
        try {
            return  httpClientHelperStarWars.getDataString(new URL(propertiesConfig.getDataVehicles()));
        }  catch (MalformedURLException mue) {
            throw  new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Resource not found",mue);
        }
    }


    @GetMapping(value="/sports-entities")
    public Mono<EntitySportCAT_DTO[]> getSportEntity(){
        return httpClientHelperSportEntityCAT.getData();

    }


}
