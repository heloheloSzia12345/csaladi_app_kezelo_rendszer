package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="UserTable")
public class UserEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank(message="Give a name!")
    @Size(min=5, max=50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ThemeEntity theme;

    @ManyToOne
    @JoinColumn(name = "background_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private BackgroundEntity background;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;
}
