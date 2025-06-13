package com.chamelete.flowManagement.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/board/card")
public class CardController {

    //metodo post para criar card

    //metodo patch para editar nome, description, position, blocked, due_date, stage, assigned_to, classOfService e type

    //metodo delete para deletar card
    
}
