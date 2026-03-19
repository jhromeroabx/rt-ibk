
package org.example.domain.port;

import org.example.domain.model.User;

public interface UserRepositoryPort {
    boolean existsByEmail(String email);
    void save(User user);
}
