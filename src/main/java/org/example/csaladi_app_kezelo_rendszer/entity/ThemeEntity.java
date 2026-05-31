package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name="ThemeTable")
public class ThemeEntity  extends BaseEntity{
    @OneToMany(mappedBy = "theme")
    private List<UserEntity> users;
}
