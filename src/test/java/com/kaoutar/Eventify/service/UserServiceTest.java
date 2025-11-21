package com.kaoutar.Eventify.service;

import com.kaoutar.Eventify.dto.UserDTO;
import com.kaoutar.Eventify.enums.Role;
import com.kaoutar.Eventify.exception.UsernameAlreadyExistsException;
import com.kaoutar.Eventify.mapper.UserMapper;
import com.kaoutar.Eventify.model.User;
import com.kaoutar.Eventify.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User(1L, "Kaoutar", "kaoutar@gmail.com", "1234", Role.ROLE_USER);
        userDTO = new UserDTO(1L, "Kaoutar", "kaoutar@gmail.com", "1234", Role.ROLE_USER);
    }

    @Test
    void testCreateUser_success() {
        when(userRepository.existsByEmail("kaoutar@gmail.com")).thenReturn(false);
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(passwordEncoder.encode("1234")).thenReturn("1234"); // mot de passe NON crypté
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertEquals("1234", result.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void testCreateUser_emailAlreadyExists() {
        when(userRepository.existsByEmail("kaoutar@gmail.com")).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        List<UserDTO> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("Kaoutar", users.get(0).getName());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertEquals("Kaoutar", result.getName());
    }

    @Test
    void testUpdateUser_success() {
        UserDTO updatedDTO = new UserDTO(1L, "New Name", "kaoutar@gmail.com", "0000", Role.ROLE_ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("kaoutar@gmail.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(updatedDTO);

        UserDTO result = userService.updateUser(1L, updatedDTO);

        assertEquals("New Name", result.getName());
        assertEquals("0000", result.getPassword()); // mot de passe NON crypté
        assertEquals(Role.ROLE_ADMIN, result.getRole());
    }

    @Test
    void testDeleteUser_success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_notFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void testGetCurrentUserProfile() {
        // Fake authentication
        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken("kaoutar@gmail.com", null)
        );

        when(userRepository.findByEmail("kaoutar@gmail.com")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getCurrentUserProfile();

        assertEquals("Kaoutar", result.getName());
    }
}
