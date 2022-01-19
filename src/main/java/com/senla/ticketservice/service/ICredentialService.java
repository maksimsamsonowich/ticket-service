package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.ConfirmationCredentialDto;
import com.senla.ticketservice.dto.CredentialDto;

public interface ICredentialService {

    void createCredential(CredentialDto credentialDto);

    CredentialDto readCredential(Long id);

    void updateCredential(Long credentialId, ConfirmationCredentialDto credentialDto);

    void deleteCredential(Long id, ConfirmationCredentialDto credentialDto);

    CredentialDto findCredentialByEmail(String email);
}
