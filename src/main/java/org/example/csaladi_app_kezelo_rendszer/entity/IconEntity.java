package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="IconTable")
public class IconEntity {
    @Id
    private String iconId = UUID.randomUUID().toString();

    @NotBlank
    @Size(min=5,max=30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;
}
