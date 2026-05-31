package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="IconTable")
public class IconEntity  extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;
}
