package com.kaoutar.Eventify.controller;

import com.kaoutar.Eventify.dto.EventDTO;
import com.kaoutar.Eventify.dto.UserDTO;
import com.kaoutar.Eventify.service.EventService;
import com.kaoutar.Eventify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {
    private final UserService userService;
    private final EventService eventService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getPublicEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
}
