package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="UserAccount")
public class UserEntity {
    @Id
    private String id = UUID.randomUUID().toString();
    @NotBlank(message="Give a name!")
    @Size(min=5, max=50)
    private String name;
}
