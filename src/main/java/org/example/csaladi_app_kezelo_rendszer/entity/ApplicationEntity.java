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
@Table(name="ApplicationTable")
public class ApplicationEntity {
    @Id
    private String applicationId = UUID.randomUUID().toString();

    @NotBlank
    @Size(min=5,max=30)
    private String name;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IconEntity> icons;
}
