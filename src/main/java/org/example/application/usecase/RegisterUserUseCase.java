
package org.example.application.usecase;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.exception.DuplicateEmailException;
import org.example.domain.exception.InvalidPasswordException;
import org.example.domain.model.User;
import org.example.domain.port.PasswordEncoderPort;
import org.example.domain.port.TokenProviderPort;
import org.example.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.example.infrastructure.config.SecurityProperties;
import org.example.users.api.model.UserCreateRequest;
import org.example.users.api.model.UserCreatedResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;
import java.time.ZoneOffset;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenProviderPort tokenProvider;
    private final SecurityProperties securityProperties;

    public Mono<UserCreatedResponse> execute(UserCreateRequest request) {
        log.info("Validando si el email ya existe: {}", request.getEmail());
        return Mono.fromCallable(() -> userRepository.existsByEmail(request.getEmail()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Intento de registro con email duplicado: {}", request.getEmail());
                        return Mono.error(new DuplicateEmailException());
                    }

                    return Mono.fromCallable(() -> createUser(request))
                            .subscribeOn(Schedulers.boundedElastic());
                });
    }

    private UserCreatedResponse createUser(UserCreateRequest request) {

        validatePassword(request.getPassword());

        String passwordHash = passwordEncoder.encode(request.getPassword());

        String token = tokenProvider.generateToken(request.getEmail());

        User user = User.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordHash)
                .phones(mapPhones(request.getPhones()))
                .created(Instant.now())
                .modified(Instant.now())
                .lastLogin(Instant.now())
                .token(token)
                .active(true)
                .build();

        userRepository.save(user);
        log.info("Usuario creado correctamente: {}", user.getEmail());
        return mapToResponse(user);
    }

    private List<org.example.domain.model.Phone> mapPhones(List<org.example.users.api.model.PhoneRequest> phones) {
        if (phones == null) return List.of();

        return phones.stream()
                .map(p -> org.example.domain.model.Phone.builder()
                        .number(p.getNumber())
                        .citycode(p.getCitycode())
                        .countrycode(p.getCountrycode())
                        .build())
                .toList();
    }

    private UserCreatedResponse mapToResponse(User user) {

        UserCreatedResponse response = new UserCreatedResponse();

        response.setId(UUID.fromString(user.getId().toString()));
        response.setCreated(OffsetDateTime.ofInstant(user.getCreated(), ZoneOffset.UTC));
        response.setModified(OffsetDateTime.ofInstant(user.getModified(), ZoneOffset.UTC));
        response.setLastLogin(OffsetDateTime.ofInstant(user.getLastLogin(), ZoneOffset.UTC));
        response.setToken(user.getToken());
        response.setIsactive(user.getActive());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        response.setPhones(
                user.getPhones().stream()
                        .map(p -> {
                            org.example.users.api.model.PhoneResponse pr =
                                    new org.example.users.api.model.PhoneResponse();
                            pr.setNumber(p.getNumber());
                            pr.setCitycode(p.getCitycode());
                            pr.setCountrycode(p.getCountrycode());
                            return pr;
                        })
                        .toList()
        );

        return response;
    }

    private void validatePassword(String password) {
        String regex = securityProperties.getPasswordRegex();

        if (!password.matches(regex)) {
            throw new InvalidPasswordException();
        }
    }
}