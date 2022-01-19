package com.senla.ticketservice.service.impl;

import com.senla.ticketservice.dto.ArtistDto;
import com.senla.ticketservice.entity.Artist;
import com.senla.ticketservice.entity.Role;
import com.senla.ticketservice.entity.User;
import com.senla.ticketservice.exception.artist.ArtistNotFoundException;
import com.senla.ticketservice.mapper.IMapper;
import com.senla.ticketservice.repository.ArtistRepository;
import com.senla.ticketservice.repository.CredentialRepository;
import com.senla.ticketservice.repository.impl.RoleRepository;
import com.senla.ticketservice.repository.impl.UserRepository;
import com.senla.ticketservice.service.IArtistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class ArtistService implements IArtistService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ArtistRepository artistRepository;

    private final IMapper<ArtistDto, Artist> artistMapper;

    private final CredentialRepository credentialRepository;

    @Override
    public void createArtist(Long userId, ArtistDto artistDto) {

        Role artistRole = roleRepository.findByName("ARTIST");

        User currentUser = userRepository.readById(userId);

        Artist currentArtistEntity = artistMapper.toEntity(artistDto, Artist.class);

        if (currentUser.getCredential().getRoles().stream()
                .noneMatch(role -> role.getRole().equals(artistRole.getRole()))) {

            currentUser.getCredential().getRoles().add(artistRole);
            //credentialRepository.save(currentUser.getCredential());
        }
        currentArtistEntity.setCardOwner(currentUser);

        artistRepository.save(currentArtistEntity);

    }

    @Override
    @Transactional(readOnly = true)
    public ArtistDto getArtist(Long artistId) {

        Artist currentArtist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException("There is no such artist"));

        return artistMapper.toDto(currentArtist, ArtistDto.class);
    }

    @Override
    public ArtistDto updateArtist(Long artistId, ArtistDto artistDto) {

        artistDto.setId(artistId);

        Artist currentArtist = artistMapper.toEntity(artistDto, Artist.class);

        return artistMapper.toDto(artistRepository.save(currentArtist), ArtistDto.class);
    }

    @Override
    public void deleteArtist(Long artistId) {
        artistRepository.deleteById(artistId);
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistDto getArtistByEventId(Long eventId) {
        Artist currentArtist = artistRepository.getArtistByEventsId(eventId);

        if (Objects.isNull(currentArtist))
            throw new ArtistNotFoundException("There is no such artist");
        else
            return artistMapper.toDto(currentArtist, ArtistDto.class);
    }

}
