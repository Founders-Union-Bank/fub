package org.fub.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull
    private String firstName;
    private String lastName;
    @Email
    @NotNull
    private String email;
    @NotNull
    private long mobileNumber;
    @NotNull
    @Size(min = 8, max = 15)
    private String password;
}
