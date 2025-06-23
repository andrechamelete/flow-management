package com.chamelete.flowManagement.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chamelete.flowManagement.model.CardType;
import com.chamelete.flowManagement.model.Cards;
import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.CycleTime;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.ServiceClasses;
import com.chamelete.flowManagement.model.Stage;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.CardTypeRepository;
import com.chamelete.flowManagement.repository.CardsRepository;
import com.chamelete.flowManagement.repository.CycleTimeRepository;
import com.chamelete.flowManagement.repository.FlowsRepository;
import com.chamelete.flowManagement.repository.ServiceClassesRepository;
import com.chamelete.flowManagement.repository.StageRepository;
import com.chamelete.flowManagement.repository.UserPermissionRepository;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.dto.CardRequest;
import com.chamelete.flowManagement.security.dto.UpdateCardRequest;
import com.chamelete.flowManagement.service.CompaniesService;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/board/card")
public class CardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private CompaniesService companiesService;

    @Autowired
    private FlowsRepository flowsRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private ServiceClassesRepository serviceClassesRepository;

    @Autowired
    private CycleTimeRepository cycleTimeRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;


    public CardController(UserRepository userRepository, 
                        UserPermissionRepository userPermissionRepository, 
                        CompaniesService companiesService, 
                        FlowsRepository flowsRepository,
                        StageRepository stageRepository,
                        CardsRepository cardsRepository,
                        ServiceClassesRepository serviceClassesRepository,
                        CycleTimeRepository cycleTimeRepository,
                        CardTypeRepository cardTypeRepository) {
        this.userRepository = userRepository;
        this.userPermissionRepository = userPermissionRepository;
        this.companiesService = companiesService;
        this.flowsRepository = flowsRepository;
        this.stageRepository = stageRepository;
        this.cardsRepository = cardsRepository;
        this.serviceClassesRepository = serviceClassesRepository;
        this.cycleTimeRepository = cycleTimeRepository;
        this.cardTypeRepository = cardTypeRepository;
    }

    //metodo post para criar card
    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        User assignee = new User();

        if(request.getAssigneeEmail() == null || request.getAssigneeEmail().isEmpty()) {
            assignee = user;
        } else {
            assignee = userRepository.findByEmail(request.getAssigneeEmail()).orElse(null);
        }

        Companies company = companiesService.findById(request.getCompanyId());

        if (request.getCompanyId() == null || request.getFlowId() == null) {
            return ResponseEntity.badRequest().body("Company ID and Flow ID are required.");
        }

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        if (!userPermissionRepository.existsByUserAndCompany(user, company)) {
            return ResponseEntity.badRequest().body("User does not have permission to create cards in this company.");
        }

        Flows flow = flowsRepository.findById(request.getFlowId()).orElse(null);
        Stage stage = stageRepository.findById(request.getStageId()).orElse(null);

        if (flow == null || stage == null) {
            return ResponseEntity.badRequest().body("Flow or stage not found");
        }
        int position = cardsRepository.findByStage(stage).size();

        Cards card = new Cards();
        card.setName(request.getName());
        card.setDescription(request.getDescription());
        card.setPosition(position);
        card.setDueDate(request.getDueDate());
        card.setCreatedAt(LocalDateTime.now());
        card.setAssignedTo(assignee);
        card.setClassOfService(request.getClassOfService());
        //card.setType();
        card.setBlocked(false);
        card.setFlow(flow);
        card.setStage(stage);
        card.setCreatedBy(user);

        CycleTime cycleTime = new CycleTime();
        cycleTime.setFlow(flow);
        cycleTime.setStage(stage);
        cycleTime.setCard(card);
        cycleTime.setCreatedAt(LocalDateTime.now());
        
        cardsRepository.save(card);
        cycleTimeRepository.save(cycleTime);        

        return ResponseEntity.ok(card);
    }

    //metodo patch para editar nome, description, position, blocked, due_date, stage, assigned_to, classOfService e type
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody UpdateCardRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        Cards card = cardsRepository.findById(id).orElse(null);

        if(card == null) {
            return ResponseEntity.badRequest().body("Card not found");
        }

        Companies company = companiesService.findById(card.getFlow().getCompany().getId());

        if (!userPermissionRepository.existsByUserAndCompany(user, company)) {
            return ResponseEntity.badRequest().body("User does not have permission to create cards in this company.");
        }

        if(request.getName() != null) {
            card.setName(request.getName());
        }

        if(request.getDescription() != null) {
            card.setDescription(request.getDescription());
        }

        if(request.getDueDate() != null) {
            card.setDueDate(request.getDueDate());
        }

        User assignee = userRepository.findByEmail(request.getAssignedTo()).orElse(null);
        if(request.getAssignedTo() != null) {
            if (userPermissionRepository.existsByUserAndCompany(assignee, company)) {
                card.setAssignedTo(assignee);
            } else {
                return ResponseEntity.badRequest().body("Usuário sem permisão.");
            }
        } else {
            return ResponseEntity.badRequest().body("usuario nulo.");
        }
        if(request.getClassOfService() != null) {
            Long classOfServiceId = request.getClassOfService();
            ServiceClasses classOfService = serviceClassesRepository.findById(classOfServiceId).orElse(null);
            card.setClassOfService(classOfService);
        }
        
        if(request.getType() != null) {
            Long cardTypeId = request.getType();;
            CardType cardType = cardTypeRepository.findById(cardTypeId).orElse(null);
            card.setType(cardType);
        }

        card.setBlocked(request.isBlocked());

        cardsRepository.save(card);
        return ResponseEntity.ok(card);
    }

    //metodo delete para deletar card
    
}
