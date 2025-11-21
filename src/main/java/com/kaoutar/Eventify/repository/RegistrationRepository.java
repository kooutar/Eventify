package com.kaoutar.Eventify.repository;

import com.kaoutar.Eventify.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    // Tu peux ajouter des méthodes personnalisées si besoin, par exemple :
     List<Registration> findByUserId(Long userId);
    // List<Registration> findByEventId(Long eventId);
}