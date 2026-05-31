package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="BackgroundTable")
public class BackgroundEntity  extends BaseEntity{
    @OneToMany(mappedBy = "background")
    private List<UserEntity> users;
}
