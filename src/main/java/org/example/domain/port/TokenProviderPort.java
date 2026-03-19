
package org.example.domain.port;

public interface TokenProviderPort {
    String generateToken(String email);
}
