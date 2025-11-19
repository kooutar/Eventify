package com.kaoutar.Eventify.mapper;

import com.kaoutar.Eventify.dto.EventDTO;
import com.kaoutar.Eventify.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

    // Entity → DTO
    @Mapping(source = "organizer.id", target = "organizerId")
    @Mapping(source = "organizer.name", target = "organizerName")
    EventDTO toDTO(Event event);

    // DTO → Entity
    @Mapping(source = "organizerId", target = "organizer.id")
    Event toEntity(EventDTO dto);
}
