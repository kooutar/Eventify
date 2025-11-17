package com.kaoutar.Eventify.repository;


import com.kaoutar.Eventify.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Si besoin, tu peux ajouter des méthodes personnalisées, par exemple :
    // List<Event> findByOrganizerId(Long organizerId);
}
