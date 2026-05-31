package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name="BackgroundTable")
public class BackgroundEntity {
    @Id
    private String backgroundId = UUID.randomUUID().toString();

    @NotBlank
    @Size(min=5,max=50)
    private String name;

    @OneToMany(mappedBy = "background")
    private List<UserEntity> users;
}
