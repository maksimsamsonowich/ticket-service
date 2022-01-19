package com.senla.ticketservice.service.impl;

import com.senla.ticketservice.exception.artist.ArtistNotFoundException;
import com.senla.ticketservice.metamodel.Roles;
import com.senla.ticketservice.repository.ArtistRepository;
import com.senla.ticketservice.repository.impl.EventRepository;
import com.senla.ticketservice.repository.impl.TicketRepository;
import com.senla.ticketservice.repository.impl.UserRepository;
import com.senla.ticketservice.service.IItemsSecurityExpressions;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ItemsSecurityExpressions implements IItemsSecurityExpressions {

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    private final ArtistRepository artistRepository;

    @Override
    public Boolean isUserOwnedTicket(Long ticketId, Authentication authentication) {

        if (isAdmin(authentication))
            return true;

        String ownerEmail = ticketRepository.readById(ticketId).getOwner()
                .getCredential().getEmail();

        final String currentUserEmail = authentication.getName();

        return ownerEmail.equals(currentUserEmail);
    }

    @Override
    public Boolean isUserOwnedAccount(Long userId, Authentication authentication) {

        if (isAdmin(authentication))
            return true;

        String ownerEmail = userRepository.readById(userId).getCredential().getEmail();

        final String currentUserEmail = authentication.getName();

        return ownerEmail.equals(currentUserEmail);
    }

    @Override
    public Boolean isUserOwnedEvent(Long eventId, Authentication authentication) {

        if (isAdmin(authentication))
            return true;

        String ownerEmail = eventRepository.readById(eventId).getEventOrganizer()
                .getCardOwner().getCredential().getEmail();

        final String currentUserEmail = authentication.getName();

        return ownerEmail.equals(currentUserEmail);
    }

    @Override
    public Boolean isUserOwnedArtistCard(Long artistId, Authentication authentication) {
        if (isAdmin(authentication))
            return true;

        String ownerEmail = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException("There is no such artist"))
                .getCardOwner().getCredential().getEmail();

        final String currentUserEmail = authentication.getName();

        return ownerEmail.equals(currentUserEmail);
    }

    private Boolean isAdmin(@NotNull Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals(Roles.ADMIN));
    }
}
