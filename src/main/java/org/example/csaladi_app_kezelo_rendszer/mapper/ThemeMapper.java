package org.example.csaladi_app_kezelo_rendszer.mapper;

import org.example.csaladi_app_kezelo_rendszer.dto.ThemeDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ThemeEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemeMapper {
    @Mapping(source = "users", target = "usersId", qualifiedByName = "usersToUsersId")
    ThemeDto toDto(ThemeEntity themeEntity);

    @Mapping(target = "users", ignore = true)
    ThemeEntity toEntity(ThemeDto themeDto);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ThemeDto themeDto, @MappingTarget ThemeEntity themeEntity);

    @Named("usersToUsersId")
    default List<String> usersToUsersId(List<UserEntity> users) {
        if (users == null) return null;
        return users.stream().map(UserEntity::getId).toList();
    }
}
