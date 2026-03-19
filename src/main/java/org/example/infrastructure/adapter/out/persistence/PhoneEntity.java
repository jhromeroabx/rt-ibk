package org.example.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String citycode;
    private String countrycode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}