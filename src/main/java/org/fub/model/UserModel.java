package org.fub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    private String userId;
    @NotNull
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Email
    @Column(name = "userEmail", unique = true, nullable = false)
    @NotNull
    private String email;
    @NotNull
    @Column(name = "mobileNumber")
    private long mobileNumber;
    @NotNull
    @Column(name = "password")
    private String password;
    @Column(name = "isActive")
    private boolean isActive = true;
    @CreatedDate
    @Column(name = "createdDate")
    private Date createDate;
    @Past
    @Column(name = "lastLogin")
    private Date lastLogin;
    @Column(name = "roles")
    private String roles;
}
