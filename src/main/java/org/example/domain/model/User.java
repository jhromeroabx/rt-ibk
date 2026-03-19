
package org.example.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class User {
    private UUID id;
    private String name;
    private String email;
    private String passwordHash;
    private List<Phone> phones;
    private Instant created;
    private Instant modified;
    private Instant lastLogin;
    private String token;
    private Boolean active;
}
