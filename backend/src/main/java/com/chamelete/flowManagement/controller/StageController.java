package com.chamelete.flowManagement.controller;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.Stage;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.StageRepository;
import com.chamelete.flowManagement.repository.UserPermissionRepository;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.dto.StageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/board/stage")
public class StageController {

    @Autowired
    private UserPermissionRepository UserPermissionRepository;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private StageRepository stageRepository;

    public StageController(
            UserPermissionRepository UserPermissionRepository, 
            UserRepository UserRepository,
            StageRepository stageRepository) {
        this.UserPermissionRepository = UserPermissionRepository;
        this.UserRepository = UserRepository;
        this.stageRepository = stageRepository;
    }

    //metodo post para criar nova stage
    @PostMapping
    public ResponseEntity<?> createStage(@RequestBody StageRequest stageRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = UserRepository.findByEmail(email).orElse(null);

        Companies company = stageRequest.getCompany();

        if (company == null) {
            return ResponseEntity.badRequest().body("Company not found");
        }

        if (!UserPermissionRepository.existsByUserAndCompany(user, company)) {
            return ResponseEntity.badRequest().body("User does not have permission to create a stage in this company");
        }

        Flows flow = stageRequest.getFlow();

        if (flow == null) {
            return ResponseEntity.badRequest().body("Flow not found");
        }

        Stage stage = new Stage();
        stage.setFlow(flow);
        stage.setName(stageRequest.getName());
        stage.setPosition(stageRequest.getPosition());
        stage.setDone(false);
        stage.setWipLimit(stageRequest.getWipLimit());

        stageRepository.save(stage);

        return ResponseEntity.ok(stage);
    }

    //metodo patch para editar nome, position e wipLimit
    //@PatchMapping

    //metodo delete para deletar stage
    
}
