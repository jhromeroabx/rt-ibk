
package org.example.domain.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super("El correo ya registrado");
    }
}
