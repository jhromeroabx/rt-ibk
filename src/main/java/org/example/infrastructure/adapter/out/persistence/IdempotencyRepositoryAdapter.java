package org.example.infrastructure.adapter.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.domain.port.IdempotencyPort;
import org.example.users.api.model.UserCreatedResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdempotencyRepositoryAdapter implements IdempotencyPort {

    private final IdempotencyRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<UserCreatedResponse> find(String requestId) {
        return repository.findById(requestId)
                .map(entity -> {
                    try {
                        return objectMapper.readValue(
                                entity.getResponseBody(),
                                UserCreatedResponse.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Error deserializando respuesta", e);
                    }
                });
    }

    @Override
    public void save(String requestId, UserCreatedResponse response) {
        try {
            repository.save(
                    IdempotencyKey.builder()
                            .requestId(requestId)
                            .responseBody(objectMapper.writeValueAsString(response))
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error serializando respuesta", e);
        }
    }
}