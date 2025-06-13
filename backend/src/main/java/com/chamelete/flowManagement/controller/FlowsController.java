package com.chamelete.flowManagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.security.dto.FlowsRequest;
import com.chamelete.flowManagement.service.FlowsService;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/flows")
public class FlowsController {
    
    @Autowired
    private final FlowsService flowsService;

    public FlowsController(FlowsService flowsService) {
        this.flowsService = flowsService;
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody FlowsRequest request) {
        //fazer validaçao de se o usuario logado tem permissao de acesso à company que está sendo passada no request
        Flows flow = flowsService.createFlow(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(flow);
    }

    @GetMapping
    public ResponseEntity<List<Flows>> getFlows(@RequestParam Companies company) {
        
        List<Flows> flows =  flowsService.getFlowsByCompany(company);

        return  ResponseEntity.ok(flows);
    }

}
