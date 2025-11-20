package com.kaoutar.Eventify.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDTO {

    private Long id;

    private Long userId;

    private Long eventId;

    private LocalDateTime registeredAt;

    private String status; // On garde String ici pour faciliter la sérialisation JSON
}