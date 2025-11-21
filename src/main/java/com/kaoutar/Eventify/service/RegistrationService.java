package com.kaoutar.Eventify.service;

import com.kaoutar.Eventify.dto.RegistrationDTO;
import com.kaoutar.Eventify.enums.RegistrationStatus;
import com.kaoutar.Eventify.mapper.RegistrationMapper;
import com.kaoutar.Eventify.model.Event;
import com.kaoutar.Eventify.model.Registration;
import com.kaoutar.Eventify.model.User;
import com.kaoutar.Eventify.repository.EventRepository;
import com.kaoutar.Eventify.repository.RegistrationRepository;
import com.kaoutar.Eventify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RegistrationMapper registrationMapper;

    // Créer une inscription
    public RegistrationDTO createRegistration(RegistrationDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.getUserId()));

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id " + dto.getEventId()));

        Registration registration = Registration.builder()
                .user(user)
                .event(event)
                .registeredAt(LocalDateTime.now())
                .status(dto.getStatus() != null ?
                        RegistrationStatus.valueOf(dto.getStatus()) :
                        RegistrationStatus.PENDING)
                .build();
        return registrationMapper.toDTO(registrationRepository.save(registration));
    }

    // Récupérer toutes les inscriptions
    @Transactional(readOnly = true)
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAll().stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Récupérer une inscription par id
    @Transactional(readOnly = true)
    public RegistrationDTO getRegistrationById(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with id " + id));
        return registrationMapper.toDTO(registration);
    }

    // Mettre à jour une inscription
    public RegistrationDTO updateRegistration(Long id, RegistrationDTO dto) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with id " + id));

        if (dto.getStatus() != null) {
            registration.setStatus(RegistrationStatus.valueOf(dto.getStatus()));

        }
        // Si tu veux changer l'user ou l'event
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id " + dto.getUserId()));
            registration.setUser(user);
        }

        if (dto.getEventId() != null) {
            Event event = eventRepository.findById(dto.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found with id " + dto.getEventId()));
            registration.setEvent(event);
        }

        return registrationMapper.toDTO(registrationRepository.save(registration));
    }

    // Supprimer une inscription
    public void deleteRegistration(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with id " + id));
        registrationRepository.delete(registration);
    }

    public RegistrationDTO registerLoggedUserToEvent(Long eventId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));

        Registration registration = Registration.builder()
                .user(user)
                .event(event)
                .registeredAt(LocalDateTime.now())
                .status(RegistrationStatus.PENDING)
                .build();

        return registrationMapper.toDTO(registrationRepository.save(registration));
    }

    public List<RegistrationDTO> getRegistrationsForCurrentUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return registrationRepository.findByUserId(user.getId())
                .stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());
    }

}