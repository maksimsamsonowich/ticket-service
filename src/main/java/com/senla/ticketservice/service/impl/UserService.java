package com.senla.ticketservice.service.impl;

import com.senla.ticketservice.dto.RoleDto;
import com.senla.ticketservice.dto.UserDto;
import com.senla.ticketservice.entity.Role;
import com.senla.ticketservice.entity.User;
import com.senla.ticketservice.exception.role.RoleNotFoundException;
import com.senla.ticketservice.mapper.IMapper;
import com.senla.ticketservice.repository.impl.RoleRepository;
import com.senla.ticketservice.repository.impl.UserRepository;
import com.senla.ticketservice.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final IMapper<UserDto, User> userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User currentUser = userMapper.toEntity(userDto, User.class);

        userRepository.create(currentUser);

        return userMapper.toDto(currentUser, UserDto.class);
    }

    @Override
    public UserDto readUser(Long userId) {
        User currentUser = userRepository.readById(userId);

        return userMapper.toDto(currentUser, UserDto.class);
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) {
        userDto.setId(userId);
        User currentUser = userMapper.toEntity(userDto, User.class);

        userRepository.update(currentUser);

        return userMapper.toDto(currentUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void grantRole(Long userId, RoleDto roleDto) {

        User currentUser = userRepository.readById(userId);

        if (Objects.isNull(currentUser))
            throw new UsernameNotFoundException("There is no such user.");

        Role roleToGrant = roleRepository.findByName(roleDto.getRole());

        if (Objects.isNull(roleToGrant))
            throw new RoleNotFoundException("There is role mismatch :(");

        if (currentUser.getCredential().getRoles().stream()
                .anyMatch(role -> role.getRole().equals(roleToGrant.getRole())))
            throw new com.senla.ticketservice.exception.role.RoleNotFoundException("The user already has such role.");

        currentUser.getCredential().getRoles().add(roleToGrant);

        userRepository.update(currentUser);

    }
}
