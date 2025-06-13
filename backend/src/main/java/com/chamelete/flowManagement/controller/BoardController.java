package com.chamelete.flowManagement.controller;

import org.springframework.web.bind.annotation.RestController;

import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.Stage;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.CardsRepository;
import com.chamelete.flowManagement.repository.StageRepository;
import com.chamelete.flowManagement.repository.UserPermissionRepository;
import com.chamelete.flowManagement.security.dto.BoardResponse;
import com.chamelete.flowManagement.service.UserService;
import com.chamelete.flowManagement.repository.CompaniesRepository;
import com.chamelete.flowManagement.repository.FlowsRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserPermissionRepository userPermissionRepository;

    @Autowired
    private final CardsRepository cardsRepository;

    @Autowired
    private final StageRepository stageRepository;

    @Autowired
    private final CompaniesRepository companyRepository;

    @Autowired 
    private final FlowsRepository flowsRepository;

    public BoardController(
            UserService userService, 
            UserPermissionRepository userPermissionRepository,
            CardsRepository cardsRepository,
            StageRepository stageRepository,
            CompaniesRepository companyRepository,
            FlowsRepository flowsRepository) {
        this.userService = userService;
        this.userPermissionRepository = userPermissionRepository;
        this.cardsRepository = cardsRepository;
        this.stageRepository = stageRepository;
        this.companyRepository = companyRepository;
        this.flowsRepository = flowsRepository;
    }

    @GetMapping
    public ResponseEntity<?> getStagesAndCards(@RequestParam Companies company, @RequestParam Flows flow) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        Optional<Companies> companyOpt = companyRepository.findById(company.getId());
        Optional<Flows> flowOpt = flowsRepository.findById(flow.getId());

        if (companyOpt.isEmpty() || flowOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid company or flow");
        }

        
        if(!userPermissionRepository.existsByUserAndCompany(user, company)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have permission to access this company");
        }

        List<Stage> stages = stageRepository.getStagesByFlow(flow);
        List<Cards> cards = cardsRepository.findByFlow(flow);

        return ResponseEntity.ok(new BoardResponse(stages, cards));
    }
    
}
