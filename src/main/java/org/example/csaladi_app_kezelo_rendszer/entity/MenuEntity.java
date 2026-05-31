package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="MenuTable")
public class MenuEntity  extends BaseEntity{
    @OneToOne(mappedBy = "menu")
    private UserEntity user;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IconEntity> icons;
}
