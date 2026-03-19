
package org.example.infrastructure.adapter.out.persistence;

import org.example.domain.model.User;
import org.example.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(mapToEntity(user));
    }

    private UserEntity mapToEntity(User user) {

        UserEntity entity = new UserEntity();
        entity.setId(user.getId().toString());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());

        List<PhoneEntity> phoneEntities = user.getPhones().stream()
                .map(p -> {
                    PhoneEntity pe = new PhoneEntity();
                    pe.setNumber(p.getNumber());
                    pe.setCitycode(p.getCitycode());
                    pe.setCountrycode(p.getCountrycode());
                    pe.setUser(entity); // 🔥 clave
                    return pe;
                })
                .toList();

        entity.setPhones(phoneEntities);

        return entity;
    }
}
