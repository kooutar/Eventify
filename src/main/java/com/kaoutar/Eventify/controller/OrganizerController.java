package com.kaoutar.Eventify.controller;

import com.kaoutar.Eventify.dto.EventDTO;
import com.kaoutar.Eventify.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizer")
@RequiredArgsConstructor
public class OrganizerController {

    private final EventService eventService;

    @PostMapping("/events")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO dto) {
        return ResponseEntity.ok(eventService.createEvent(dto));
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO dto) {
        return ResponseEntity.ok(eventService.updateEvent(id, dto));
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
