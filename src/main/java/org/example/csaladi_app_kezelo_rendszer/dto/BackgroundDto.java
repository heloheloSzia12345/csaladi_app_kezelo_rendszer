package org.example.csaladi_app_kezelo_rendszer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundDto extends BaseDto {
    private List<String> usersId;
}
