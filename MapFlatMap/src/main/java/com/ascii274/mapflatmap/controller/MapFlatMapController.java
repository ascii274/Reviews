package com.ascii274.mapflatmap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1")

public class MapFlatMapController {
    @GetMapping(value="/test-mapflatmap")
    public String test(){
        return "Test mapflatmap";
    }

}
