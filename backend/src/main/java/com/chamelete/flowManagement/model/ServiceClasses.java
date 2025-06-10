package com.chamelete.flowManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "classes_of_service")
public class ServiceClasses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "service_class", nullable = false)
    private String serviceClass;

    @ManyToOne
    @JoinColumn(name = "company", nullable = false)
    private Companies company;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy; 
    
}
