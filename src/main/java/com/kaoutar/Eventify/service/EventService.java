package com.kaoutar.Eventify.service;

import com.kaoutar.Eventify.dto.EventDTO;
import com.kaoutar.Eventify.dto.UserDTO;
import com.kaoutar.Eventify.exception.EventNotFoundException;
import com.kaoutar.Eventify.mapper.EventMapper;
import com.kaoutar.Eventify.model.Event;
import com.kaoutar.Eventify.model.User;
import com.kaoutar.Eventify.repository.EventRepository;
import com.kaoutar.Eventify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;


    private final UserRepository userRepository;

    private  final  UserService userService;


    private final EventMapper eventMapper;

    // Créer un événement
    public EventDTO createEvent(EventDTO dto) {
        User organizer = userService.getCurrentUserEntity();

        Event event = eventMapper.toEntity(dto);
        event.setOrganizer(organizer);

        event = eventRepository.save(event);
        return eventMapper.toDTO(event);
    }

    // Lire tous les événements
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lire un événement par ID
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() ->   new EventNotFoundException("Event not found with id: " + id));
        return eventMapper.toDTO(event);
    }

    // Mettre à jour un événement
    public EventDTO updateEvent(Long id, EventDTO dto) {

        // 1. Récupérer l'événement
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));

        // 2. Récupérer l'utilisateur connecté
        User currentUser = userService.getCurrentUserEntity();

        // 3. Vérifier que l'organisateur ou admin
        boolean isOwner = existing.getOrganizer().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole().name().equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You are not allowed to modify this event");
        }

        // 4. Mettre à jour les champs autorisés
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setLocation(dto.getLocation());
        existing.setDateTime(dto.getDateTime());
        existing.setCapacity(dto.getCapacity());

        // ❗ 5. Importante : on ignore dto.getOrganizerId()
        // Pour empêcher qu’un user change le propriétaire de l’event

        // 6. Sauvegarder
        existing = eventRepository.save(existing);
        return eventMapper.toDTO(existing);
    }



    public void deleteEvent(Long id) {

        // 1. Récupérer l'événement
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));

        // 2. Récupérer l'utilisateur connecté
        User currentUser = userService.getCurrentUserEntity();

        // 3. Vérifier permissions
        boolean isOwner = event.getOrganizer().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole().name().equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete this event");
        }

        // 4. Effacer l'événement
        eventRepository.delete(event);
    }

}
