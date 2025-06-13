package com.chamelete.flowManagement.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "cards")
public class Cards {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "position", nullable = false)
    private int position;

    @Column(name = "blocked", nullable = false)
    private boolean blocked;

    @Column(name = "finished_at", nullable = true)
    private LocalDateTime finished_at;

    @Column(name = "due_date", nullable = true)
    private LocalDateTime due_date;

    @ManyToOne
    @JoinColumn(name = "flow", nullable = false)
    private Flows flow;

    @ManyToOne
    @JoinColumn(name = "stage", nullable = false)
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User created_by;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = true)
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "class_of_service", nullable = true)
    private ServiceClasses classOfService;

    @ManyToOne
    @JoinColumn(name = "type", nullable = true)
    private CardType type;
}
