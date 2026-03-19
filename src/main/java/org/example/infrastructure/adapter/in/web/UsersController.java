
package org.example.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import org.example.application.usecase.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.example.users.api.model.UserCreateRequest;
import org.example.users.api.model.UserCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.example.users.api.ApiApi;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Validated
public class UsersController implements ApiApi {

    private final RegisterUserUseCase registerUserUseCase;

    @Override
    public Mono<ResponseEntity<UserCreatedResponse>> createUser(
            @Valid @RequestBody Mono<UserCreateRequest> requestMono,
            @RequestHeader(value = "X-Request-Id", required = false) String xRequestId,
            final ServerWebExchange exchange) {

        return requestMono
                .flatMap(registerUserUseCase::execute)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }
}