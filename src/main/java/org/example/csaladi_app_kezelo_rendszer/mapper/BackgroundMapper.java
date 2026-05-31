package org.example.csaladi_app_kezelo_rendszer.mapper;

import org.example.csaladi_app_kezelo_rendszer.dto.BackgroundDto;
import org.example.csaladi_app_kezelo_rendszer.entity.BackgroundEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BackgroundMapper {
    @Mapping(source = "users", target = "usersId", qualifiedByName = "usersToUsersId")
    BackgroundDto toDto(BackgroundEntity backgroundEntity);

    @Mapping(target = "users", ignore = true)
    BackgroundEntity toEntity(BackgroundDto backgroundDto);

    @Mapping(target = "users", ignore = true)
    void updateEntityFromDto(BackgroundDto backgroundDto, @MappingTarget BackgroundEntity backgroundEntity);

    @Named("usersToUsersId")
    default List<String> usersToUsersId(List<UserEntity> users)
    {
        if(users==null) return null;
        return users.stream().map(UserEntity::getId).toList();
    }
}
