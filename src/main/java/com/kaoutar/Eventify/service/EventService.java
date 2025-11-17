package com.kaoutar.Eventify.service;

import com.kaoutar.Eventify.dto.EventDTO;
import com.kaoutar.Eventify.mapper.EventMapper;
import com.kaoutar.Eventify.model.Event;
import com.kaoutar.Eventify.model.User;
import com.kaoutar.Eventify.repository.EventRepository;
import com.kaoutar.Eventify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventMapper eventMapper;

    // Créer un événement
    public EventDTO createEvent(EventDTO dto) {
        User organizer = userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RuntimeException("Organizer not found"));

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
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return eventMapper.toDTO(event);
    }

    // Mettre à jour un événement
    public EventDTO updateEvent(Long id, EventDTO dto) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setLocation(dto.getLocation());
        existing.setDateTime(dto.getDateTime());
        existing.setCapacity(dto.getCapacity());


        if (dto.getOrganizerId() != null) {
            User organizer = userRepository.findById(dto.getOrganizerId())
                    .orElseThrow(() -> new RuntimeException("Organizer not found"));
            existing.setOrganizer(organizer);
        }

        existing = eventRepository.save(existing);
        return eventMapper.toDTO(existing);
    }

    // Supprimer un événement
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        eventRepository.delete(event);
    }
}
