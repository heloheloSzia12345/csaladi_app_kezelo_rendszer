package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ApplicationTable")
public class ApplicationEntity extends BaseEntity{
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IconEntity> icons;
}
