package org.fub.controller;

import lombok.AllArgsConstructor;
import org.fub.controller.API.UserAPI;
import org.fub.request.UserRequest;
import org.fub.response.UserResponse;
import org.fub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> createUser(UserRequest user) {
        UserResponse model = userService.createUser(user);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserResponse> fetchUser(String userId) {
        UserResponse model = userService.fetchUser(userId);
        return new ResponseEntity<>(model,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserResponse>> fetchUsers() {
        List<UserResponse> users = userService.fetchUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteUser(String userId) {
        boolean isDeleted = userService.deleteUser(userId);
        if (!isDeleted){
            return new ResponseEntity<>("User Deletion Failed",HttpStatus.OK);
        }
        return new ResponseEntity<>("User Successfully Deleted",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(String userId, UserRequest user) {
        UserResponse model = userService.updateUser(userId,user);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<byte[]> uploadUserProfile(String userId, MultipartFile file) {
       byte [] profileData = userService.uploadProfile(userId, file);
       return new ResponseEntity<byte[]>(profileData,HttpStatus.OK);
    }

}
