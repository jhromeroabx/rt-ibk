package org.example.application.usecase;

import org.example.domain.port.*;
import org.example.infrastructure.config.SecurityProperties;
import org.example.users.api.model.UserCreateRequest;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class RegisterUserUseCaseTest {

    @Test
    void shouldCreateUserSuccessfully() {

        UserRepositoryPort repo = mock(UserRepositoryPort.class);
        PasswordEncoderPort encoder = mock(PasswordEncoderPort.class);
        TokenProviderPort token = mock(TokenProviderPort.class);
        SecurityProperties props = mock(SecurityProperties.class);

        when(repo.existsByEmail(any())).thenReturn(false);
        when(encoder.encode(any())).thenReturn("hashed");
        when(token.generateToken(any())).thenReturn("jwt-token");
        when(props.getPasswordRegex()).thenReturn(".*");

        RegisterUserUseCase useCase =
                new RegisterUserUseCase(repo, encoder, token, props);

        UserCreateRequest request = new UserCreateRequest();
        request.setName("Test");
        request.setEmail("test@test.com");
        request.setPassword("Abc12345");

        Mono<?> result = useCase.execute(request);

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}