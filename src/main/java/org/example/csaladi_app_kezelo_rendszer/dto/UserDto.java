package org.example.csaladi_app_kezelo_rendszer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto extends BaseDto{
    private String themeId;
    private String backgroundId;
    private String menuId;
}
