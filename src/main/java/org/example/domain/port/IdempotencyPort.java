package org.example.domain.port;

import org.example.users.api.model.UserCreatedResponse;

import java.util.Optional;

public interface IdempotencyPort {

    Optional<UserCreatedResponse> find(String requestId);

    void save(String requestId, UserCreatedResponse response);
}