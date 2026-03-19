
package org.example.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    private String id;

    private String name;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneEntity> phones;
}