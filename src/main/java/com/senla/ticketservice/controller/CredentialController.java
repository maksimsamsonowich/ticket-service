package com.senla.ticketservice.controller;

import com.senla.ticketservice.dto.ConfirmationCredentialDto;
import com.senla.ticketservice.dto.StatusDto;
import com.senla.ticketservice.repository.CredentialRepository;
import com.senla.ticketservice.repository.projection.CredentialProjection;
import com.senla.ticketservice.service.ICredentialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("personal-settings")
public class CredentialController {

    private final ICredentialService credentialService;

    private final CredentialRepository credentialRepository;

    @PostMapping("{credId}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedAccount(credId, authentication)")
    public ResponseEntity<StatusDto> updateAccount(@PathVariable Long credId,
                                                   @RequestBody ConfirmationCredentialDto credentialDto) {

        credentialService.updateCredential(credId, credentialDto);

        return ResponseEntity.ok(new StatusDto()
                .setMessage("Data successfully updated!")
                .setStatusId(HttpStatus.OK.value()));
    }

    @DeleteMapping("{credId}")
    @PreAuthorize("@itemsSecurityExpressions.isUserOwnedAccount(credId, authentication)")
    public ResponseEntity<StatusDto> deleteAccount(@PathVariable Long credId,
                                                   @RequestBody ConfirmationCredentialDto credentialDto) {

        credentialService.deleteCredential(credId, credentialDto);

        return ResponseEntity.ok(new StatusDto()
                .setMessage("Success!")
                .setStatusId(HttpStatus.OK.value()));
    }

    @GetMapping("{credId}")
    public CredentialProjection getCredentialById(@PathVariable Long credId) {

        return credentialRepository.getCredentialById(credId);
    }

}
