package com.ascii274.webrequest.controller;

import com.ascii274.webrequest.config.PropertiesConfig;
import com.ascii274.webrequest.dto.EntitySportCAT_DTO;
import com.ascii274.webrequest.dto.Root_DTO;
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
    @Autowired
    Root_DTO root_dto;

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

    /**
     * - Getting file.json and show all data as array[Entity_DTO]
     * - Data to show is SportsEntities in Catalonia.
     * @return
     */
    @GetMapping(value="/starwars/sports-entities")
    public Mono<EntitySportCAT_DTO[]> getSportEntityV1(){
        return httpClientHelperSportEntityCAT.getData();
    }

    @GetMapping(value="/sports-entities-2")
    public Mono<Root_DTO> getSportEntityV2(){
        Mono<EntitySportCAT_DTO[]> response = httpClientHelperSportEntityCAT.getData();
        return response.flatMap(
                dto ->{
                    root_dto.setResults(dto);
                    root_dto.setCount(dto.length);
                    return Mono.just(root_dto);
                });
    }

    /** My Question:
     * - public Mono<Root_DTO<EntitySportCAT_DTO>> getSportEntityV3() doesn't work
     * - More see about Mono
     *
     * @return
     */
    @GetMapping(value="/sports-entities-3")
//    public Mono<Root_DTO<EntitySportCAT_DTO>> getSportEntityV3(){
    public Mono getSportEntityV3(){
        try {
            Mono<EntitySportCAT_DTO[]> response = httpClientHelperSportEntityCAT.getData_2(new URL(propertiesConfig.getDataEntitatEsportivaCAT()),EntitySportCAT_DTO[].class);
            return  response.flatMap( dto -> {
                root_dto.setCount(dto.length);
                root_dto.setResults(dto);
                return Mono.just(root_dto);
            });

        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
        }

    }


}
