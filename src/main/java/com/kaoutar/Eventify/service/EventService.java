package com.kaoutar.Eventify.service;

import com.kaoutar.Eventify.dto.EventDTO;
import com.kaoutar.Eventify.exception.EventNotFoundException;
import com.kaoutar.Eventify.mapper.EventMapper;
import com.kaoutar.Eventify.model.Event;
import com.kaoutar.Eventify.model.User;
import com.kaoutar.Eventify.repository.EventRepository;
import com.kaoutar.Eventify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;


    private final UserRepository userRepository;


    private final EventMapper eventMapper;

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
                .orElseThrow(() ->   new EventNotFoundException("Event not found with id: " + id));
        return eventMapper.toDTO(event);
    }

    // Mettre à jour un événement
    public EventDTO updateEvent(Long id, EventDTO dto) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));

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
                .orElseThrow(() ->  new EventNotFoundException("Event not found with id: " + id));
        eventRepository.delete(event);
    }
}
