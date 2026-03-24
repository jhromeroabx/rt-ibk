package org.example.application.service;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.example.domain.port.IdempotencyPort;
import org.example.users.api.model.UserCreatedResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdempotencyService {
    private final IdempotencyPort idempotencyPort;
    public Optional<UserCreatedResponse> find(String requestId) {
        return idempotencyPort.find(requestId);
    }
    public void save(String requestId, UserCreatedResponse response) {
        idempotencyPort.save(requestId, response);
    }
}