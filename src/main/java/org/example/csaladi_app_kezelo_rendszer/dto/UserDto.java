package org.example.csaladi_app_kezelo_rendszer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto{
    private String themeId;
    private String backgroundId;
    private String menuId;
}
