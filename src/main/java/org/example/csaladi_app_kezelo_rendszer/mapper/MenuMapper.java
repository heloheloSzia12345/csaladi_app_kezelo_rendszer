package org.example.csaladi_app_kezelo_rendszer.mapper;

import org.example.csaladi_app_kezelo_rendszer.dto.MenuDto;
import org.example.csaladi_app_kezelo_rendszer.entity.IconEntity;
import org.example.csaladi_app_kezelo_rendszer.entity.MenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "icons", target = "iconsId", qualifiedByName = "iconsToIconsId")
    MenuDto toDto(MenuEntity menuEntity);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "icons", ignore = true)
    MenuEntity toEntity(MenuDto menuDto);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "icons", ignore = true)
    void updateEntityFromDto(MenuDto menuDto, @MappingTarget MenuEntity menuEntity);

    @Named("iconsToIconsId")
    default List<String> iconsToIconsId(List<IconEntity> icons)
    {
        if(icons==null) return null;
        return icons.stream().map(IconEntity::getId).toList();
    }
}
