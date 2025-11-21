package com.kaoutar.Eventify.controller;

import com.kaoutar.Eventify.dto.UserDTO;
import com.kaoutar.Eventify.service.EventService;
import com.kaoutar.Eventify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final EventService eventService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserDTO> changeUserRole(
            @PathVariable Long id,
            @RequestParam String role) {

        UserDTO user = userService.getUserById(id);
        user.setRole(Enum.valueOf(com.kaoutar.Eventify.enums.Role.class, role));
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // --- Supprimer un événement ---
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


}
