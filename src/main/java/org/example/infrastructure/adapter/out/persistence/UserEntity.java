
package org.example.infrastructure.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
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