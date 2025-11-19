package com.kaoutar.Eventify.dto;

import com.kaoutar.Eventify.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
