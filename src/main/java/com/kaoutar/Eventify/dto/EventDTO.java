package com.kaoutar.Eventify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime dateTime;
    private Integer capacity;
    private Long organizerId;
    private String organizerName;
}
