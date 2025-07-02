package org.fub.service;

import org.fub.request.UserRequest;
import org.fub.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserResponse createUser(UserRequest user);

    UserResponse fetchUser(String userId);

    List<UserResponse> fetchUsers();

    boolean deleteUser(String userId);

    UserResponse updateUser(String userId, UserRequest user);
}
