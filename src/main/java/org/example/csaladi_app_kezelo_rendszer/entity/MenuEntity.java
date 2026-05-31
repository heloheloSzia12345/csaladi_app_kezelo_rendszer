package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="MenuTable")
public class MenuEntity {
    @Id
    private String menuId = UUID.randomUUID().toString();

    @NotBlank
    @Size(min=5,max=30)
    private String name;

    @OneToOne(mappedBy = "menu")
    private UserEntity user;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IconEntity> icons;
}
