package com.kaoutar.Eventify.mapper;

import com.kaoutar.Eventify.dto.RegistrationDTO;
import com.kaoutar.Eventify.model.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "status", target = "status")
    RegistrationDTO toDTO(Registration registration);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "eventId", target = "event.id")
    @Mapping(source = "status", target = "status")
    Registration toEntity(RegistrationDTO dto);
}