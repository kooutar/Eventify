package com.kaoutar.Eventify.model;

import com.kaoutar.Eventify.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Relation vers User

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;  // Relation vers Event

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RegistrationStatus status;


}
