package org.fub.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private long mobileNumber;
    private boolean isActive;
    private Date createDate;
    private Date lastLogin;
    private String roles;
    private byte[] profileImage;
}
