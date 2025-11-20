package com.kaoutar.Eventify.controller;

import com.kaoutar.Eventify.dto.RegistrationDTO;
import com.kaoutar.Eventify.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    // Créer une inscription
    @PostMapping
    public ResponseEntity<RegistrationDTO> createRegistration(@RequestBody RegistrationDTO dto) {
        RegistrationDTO created = registrationService.createRegistration(dto);
        return ResponseEntity.ok(created);
    }

    // Récupérer toutes les inscriptions
    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> getAllRegistrations() {
        List<RegistrationDTO> list = registrationService.getAllRegistrations();
        return ResponseEntity.ok(list);
    }

    // Récupérer une inscription par id
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getRegistrationById(@PathVariable Long id) {
        RegistrationDTO dto = registrationService.getRegistrationById(id);
        return ResponseEntity.ok(dto);
    }

    // Mettre à jour une inscription
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDTO> updateRegistration(
            @PathVariable Long id,
            @RequestBody RegistrationDTO dto
    ) {
        RegistrationDTO updated = registrationService.updateRegistration(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Supprimer une inscription
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}