package org.example.csaladi_app_kezelo_rendszer.mapper;

import org.example.csaladi_app_kezelo_rendszer.dto.UserDto;
import org.example.csaladi_app_kezelo_rendszer.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "theme.id", target = "themeId")
    @Mapping(source = "background.id", target = "backgroundId")
    @Mapping(source = "menu.id", target = "menuId")
    UserDto toDto(UserEntity userEntity);

    @Mapping(source = "themeId", target = "theme.id")
    @Mapping(source = "backgroundId", target = "background.id")
    @Mapping(source = "menuId", target = "menu.id")
    UserEntity toEntity(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "themeId", target = "theme.id")
    @Mapping(source = "backgroundId", target = "background.id")
    @Mapping(source = "menuId", target = "menu.id")
    void updateEntityFromDto(UserDto userDto, @MappingTarget UserEntity userEntity);
}
