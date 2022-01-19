package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.RoleDto;
import com.senla.ticketservice.dto.UserDto;

public interface IUserService {

    UserDto createUser(UserDto user);

    UserDto readUser(Long id);

    UserDto update(Long id, UserDto user);

    void deleteUser(Long id);

    void grantRole(Long userId, RoleDto roleDto);

}
