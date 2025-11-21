package com.kaoutar.Eventify.controller;

import com.kaoutar.Eventify.dto.EventDTO;
import com.kaoutar.Eventify.dto.RegistrationDTO;
import com.kaoutar.Eventify.dto.UserDTO;
import com.kaoutar.Eventify.service.RegistrationService;
import com.kaoutar.Eventify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RegistrationService registrationService;

    // 1. Profil utilisateur connecté
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    // 2. S'inscrire à un événement
    @PostMapping("/events/{eventId}/register")
    public ResponseEntity<RegistrationDTO> registerToEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(registrationService.registerLoggedUserToEvent(eventId));
    }

    // 3. Liste des inscriptions de l'utilisateur
    @GetMapping("/registrations")
    public ResponseEntity<List<RegistrationDTO>> getUserRegistrations() {
        return ResponseEntity.ok(registrationService.getRegistrationsForCurrentUser());
    }
}
