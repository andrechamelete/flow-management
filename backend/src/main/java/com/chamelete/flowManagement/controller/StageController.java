package com.chamelete.flowManagement.controller;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.Stage;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.FlowsRepository;
import com.chamelete.flowManagement.repository.StageRepository;
import com.chamelete.flowManagement.repository.UserPermissionRepository;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.dto.StageRequest;
import com.chamelete.flowManagement.security.dto.UpdateStageRequest;
import com.chamelete.flowManagement.service.CompaniesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private CompaniesService companiesService;

    @Autowired
    private FlowsRepository flowsRepository;

    public StageController(
            UserPermissionRepository userPermissionRepository, 
            UserRepository userRepository,
            StageRepository stageRepository,
            CompaniesService companiesService,
            FlowsRepository flowsRepository) {
        this.userPermissionRepository = userPermissionRepository;
        this.userRepository = userRepository;
        this.stageRepository = stageRepository;
        this.companiesService = companiesService;
        this.flowsRepository = flowsRepository;
    }

    //metodo post para criar nova stage
    @PostMapping
    public ResponseEntity<?> createStage(@RequestBody StageRequest stageRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        Companies company = companiesService.findById(stageRequest.getCompanyId());

        if (company == null) {
            return ResponseEntity.badRequest().body("Company not found");
        }

        if (!userPermissionRepository.existsByUserAndCompany(user, company)) {
            return ResponseEntity.badRequest().body("User does not have permission to create a stage in this company");
        }

        Flows flow = flowsRepository.findById(stageRequest.getFlowId()).orElse(null);

        if (flow == null) {
            return ResponseEntity.badRequest().body("Flow not found");
        }

        Stage stage = new Stage();
        stage.setFlow(flow);
        stage.setName(stageRequest.getName());
        stage.setPosition(stageRepository.getStagesByFlow(flow).size());
        stage.setType(stageRequest.getType());
        stage.setWipLimit(stageRequest.getWipLimit());

        stageRepository.save(stage);

        return ResponseEntity.ok(stage);
    }

    //metodo patch para editar nome, position e wipLimit
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStage(@PathVariable Long id, @RequestBody UpdateStageRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        Stage stage = stageRepository.findById(id).orElse(null);
        

        if (stage == null) {
            return ResponseEntity.badRequest().body("Stage not found");
        }

        Companies company = stage.getFlow().getCompany();

        if (!userPermissionRepository.existsByUserAndCompany(user, company)) {
            return ResponseEntity.badRequest().body("User does not have permission to update a stage in this company");
        }

        if (request.getName() != null) {
            stage.setName(request.getName());
        }

        if (request.getPosition() != null) {
            stage.setPosition(request.getPosition());
        }

        if (request.getWipLimit() != null) {
            stage.setWipLimit(request.getWipLimit());
        }

        stageRepository.save(stage);
        return ResponseEntity.ok(stage);
    }
    

    //metodo delete para deletar stage
    
}
