package org.example.csaladi_app_kezelo_rendszer.mapper;

import org.example.csaladi_app_kezelo_rendszer.dto.IconDto;
import org.example.csaladi_app_kezelo_rendszer.entity.IconEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IconMapper {
    @Mapping(source = "menu.id", target = "menuId")
    @Mapping(source = "application.id", target = "applicationId")
    IconDto toDto(IconEntity iconEntity);

    @Mapping(source = "menuId", target = "menu.id")
    @Mapping(source = "applicationId", target = "application.id")
    IconEntity toEntity(IconDto iconDto);

    @Mapping(source = "menuId", target = "menu.id")
    @Mapping(source = "applicationId", target = "application.id")
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(IconDto iconDto, @MappingTarget IconEntity iconEntity);
}
