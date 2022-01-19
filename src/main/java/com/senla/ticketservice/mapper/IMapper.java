package com.senla.ticketservice.mapper;

import java.util.List;
import java.util.Set;

public interface IMapper<Dto, Entity> {

    Entity toEntity(Dto dto, Class<?> entity);

    Dto toDto(Entity entity, Class<?> dto);

    Set<Dto> setToDto(Set<Entity> setOfEntities, Class<?> dto);

    Set<Entity> setToEntities(Set<Dto> setOfDto, Class<?> entity);

    List<Entity> listToEntities(List<Dto> listOfDto, Class<?> entity);

    List<Dto> listToDto(List<Entity> listOfEntities, Class<?> dto);
}
