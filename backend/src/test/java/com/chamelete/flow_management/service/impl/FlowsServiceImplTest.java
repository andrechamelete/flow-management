package com.chamelete.flow_management.service.impl;

import com.chamelete.flowManagement.model.Companies;
import com.chamelete.flowManagement.model.Flows;
import com.chamelete.flowManagement.model.User;
import com.chamelete.flowManagement.repository.FlowsRepository;
import com.chamelete.flowManagement.repository.UserRepository;
import com.chamelete.flowManagement.security.dto.FlowsRequest;
import com.chamelete.flowManagement.service.CompaniesService;
import com.chamelete.flowManagement.service.impl.FlowsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;

public class FlowsServiceImplTest {
    
    @Mock
    private FlowsRepository flowsRepository;

    @Mock
    private CompaniesService companiesService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FlowsServiceImpl flowsService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void ShouldCreateFlowSuccessfuly() {
        String email = "teste@teste.com";
        FlowsRequest request = new FlowsRequest();
        request.setName("Teste");
        request.setDescription("descrcao do fluxo");
        request.setDepartment("department teste");

        User user = new User();
        user.setEmail(email);
        Companies company = new Companies();
        Flows expectedFlow = new Flows();
        expectedFlow.setName(request.getName());
        expectedFlow.setDescription(request.getDescription());
        expectedFlow.setDepartment(request.getDepartment());
        expectedFlow.setCompany(company);
        expectedFlow.setCreatedBy(user);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(companiesService.findByName(request.getName())).thenReturn(company);
        when(flowsRepository.save(any(Flows.class))).thenReturn(expectedFlow);

        // Act
        Flows createdFlow = flowsService.createFlow(request);

        // Assert
        assertThat(createdFlow.getName()).isEqualTo("Teste");
        assertThat(createdFlow.getCreatedBy()).isEqualTo(user);
        verify(flowsRepository).save(any(Flows.class));
    }

    @Test
    void shouldReturnFlowsByCompany() {
        // Arrange
    Companies company = new Companies();
    Flows flow1 = new Flows();
    Flows flow2 = new Flows();
    when(flowsRepository.getFlowByCompany(company)).thenReturn(List.of(flow1, flow2));

    // Act
    List<Flows> flows = flowsService.getFlowsByCompany(company);

    // Assert
    assertThat(flows).hasSize(2);
    verify(flowsRepository).getFlowByCompany(company);
    }
}
