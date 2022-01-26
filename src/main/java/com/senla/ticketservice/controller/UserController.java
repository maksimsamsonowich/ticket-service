package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.RoleDto;
import com.senla.ticketservice.dto.StatusDto;
import com.senla.ticketservice.dto.UserDto;
import com.senla.ticketservice.metamodel.Roles;
import com.senla.ticketservice.service.IUserService;
import eu.senla.customlibrary.trackstatus.TrackStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("user-management")
public class UserController {

    private final IUserService iUserService;

    @TrackStatus
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(iUserService.createUser(userDto));
    }

    @TrackStatus
    @GetMapping("{userId}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedAccount(#userId, authentication)")
    public ResponseEntity<UserDto> readUser(@PathVariable Long userId) {
        return ResponseEntity.ok(iUserService.readUser(userId));
    }

    @TrackStatus
    @PutMapping("{userId}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedAccount(#userId, authentication)")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(iUserService.update(userId, userDto));
    }

    @TrackStatus
    @DeleteMapping("{userId}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedAccount(#userId, authentication)")
    public void deleteUser(@PathVariable Long userId) {
        iUserService.deleteUser(userId);
    }

    @TrackStatus
    @Secured(Roles.ADMIN)
    @PutMapping("grant-role/{userId}")
    public ResponseEntity<StatusDto> grantRole(@PathVariable Long userId, @RequestBody RoleDto roleDto) {
        iUserService.grantRole(userId, roleDto);

        return ResponseEntity.ok(new StatusDto()
                .setStatusId(HttpStatus.OK.value())
                .setMessage("Success!"));
    }
}
