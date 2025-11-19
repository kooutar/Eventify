package com.kaoutar.Eventify.mapper;

import com.kaoutar.Eventify.dto.UserDTO;
import com.kaoutar.Eventify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
