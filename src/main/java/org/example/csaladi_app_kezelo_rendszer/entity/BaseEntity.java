package org.example.csaladi_app_kezelo_rendszer.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    @Id
    private String id;

    @NotBlank
    @Size(min=5,max=50)
    private String name;

    @PrePersist
    public final void prePersist()
    {
        if (id == null)
        {
            id = UUID.randomUUID().toString();
        }
        onPrePersist();
    }

    public void onPrePersist() {}
}
