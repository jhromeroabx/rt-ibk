
package org.example.infrastructure.adapter.in.web;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
}
