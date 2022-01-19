package com.senla.ticketservice.service;


import org.springframework.security.core.Authentication;

public interface IItemsSecurityExpressions {

    Boolean isUserOwnedArtistCard(Long artistId, Authentication authentication);

    Boolean isUserOwnedTicket(Long ticketId, Authentication authentication);

    Boolean isUserOwnedAccount(Long userId, Authentication authentication);

    Boolean isUserOwnedEvent(Long eventId, Authentication authentication);


}
