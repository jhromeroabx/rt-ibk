
package org.example.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import org.example.application.service.IdempotencyService;
import org.example.application.usecase.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.example.users.api.model.UserCreateRequest;
import org.example.users.api.model.UserCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.example.users.api.ApiApi;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
public class UsersController implements ApiApi {

    private final RegisterUserUseCase registerUserUseCase;
    private final IdempotencyService idempotencyService;

    @Override
    public Mono<ResponseEntity<UserCreatedResponse>> createUser(
            @Valid @RequestBody Mono<UserCreateRequest> requestMono,
            @RequestHeader(value = "X-Request-Id") String requestId,
            final ServerWebExchange exchange) {

        if (requestId != null) {
            Optional<UserCreatedResponse> cached = idempotencyService.find(requestId);

            if (cached.isPresent()) {
                return Mono.just(
                        ResponseEntity
                                .ok()
                                .header("X-Idempotent-Replay", "true")
                                .body(cached.get())
                );
            }
        }

        return requestMono
                .flatMap(registerUserUseCase::execute)
                .map(response -> {

                    if (requestId != null) {
                        idempotencyService.save(requestId, response);
                    }

                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }
}