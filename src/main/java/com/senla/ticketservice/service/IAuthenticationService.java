package com.senla.ticketservice.service;

import com.senla.ticketservice.dto.AuthenticationAnswerDto;
import com.senla.ticketservice.dto.AuthenticationRequestDto;

public interface IAuthenticationService {

    AuthenticationAnswerDto login(AuthenticationRequestDto requestDto);

}
