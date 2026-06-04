package org.example.csaladi_app_kezelo_rendszer.mapper;

import org.example.csaladi_app_kezelo_rendszer.dto.ApplicationDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ApplicationEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.IconEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(source = "icons", target = "iconsId", qualifiedByName = "iconsToIconsId")
    ApplicationDto toDto(ApplicationEntity applicationEntity);

    @Mapping(target = "icons", ignore = true)
    ApplicationEntity toEntity(ApplicationDto applicationDto);

    @Mapping(target = "icons", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ApplicationDto applicationDto, @MappingTarget ApplicationEntity applicationEntity);

    @Named("iconsToIconsId")
    default List<String> iconsToIconsId(List<IconEntity> icons) {
        if (icons == null) return null;
        return icons.stream().map(IconEntity::getId).toList();
    }
}
