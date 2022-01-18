package com.senla.ticketservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/hello/{name}")
    public String helloWrld(@PathVariable String name) {
        return "Hello " + name;
    }

}
