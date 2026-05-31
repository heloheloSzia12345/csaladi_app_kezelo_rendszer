package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="UserTable")
public class UserEntity  extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "theme_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ThemeEntity theme;

    @ManyToOne
    @JoinColumn(name = "background_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private BackgroundEntity background;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id", unique = true)
    private MenuEntity menu;
}
