package org.fub.controller.API;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.OnError;
import org.fub.request.UserRequest;
import org.fub.response.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@Tag(name = "User")
public interface UserAPI {

    @Operation(summary = "Create user", description = "Create a new user ", tags = {"User"})
    @ApiResponse(responseCode = "201", description = "User Created Successfully")
    @PostMapping(value = "/users/register")
    ResponseEntity<UserResponse> createUser(
            @Parameter(in = ParameterIn.DEFAULT, description = "user body") @Valid @NotNull @RequestBody UserRequest user
    );

    @Operation(summary = "Fetch User", description = "Fetch User Returns a User",tags = {"User"})
    @ApiResponse(responseCode = "200", description = "Fetch User Successfully")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @GetMapping(value = "/users/{userId}", produces = {"application/json", "application/xml"})
    ResponseEntity<UserResponse> fetchUser(@Parameter(in = ParameterIn.PATH, description = "Id of the User") @PathVariable(name = "userId") @NotNull String userId);

    @Operation(summary = "Fetch Users", description = "Returns List of Users",tags = {"User"})
    @ApiResponse(responseCode = "200",description = "Fetch Users Success")
    @GetMapping(value ="/users",produces = {"application/json","application/xml"})
    ResponseEntity<List<UserResponse>> fetchUsers();

    @Operation(summary = "Delete User", description = "Delete a user",tags = {"User"})
    @ApiResponse(responseCode = "200",description = "User Successfully deleted")
    @DeleteMapping(value = "/users/{userId}")
    ResponseEntity<String> deleteUser(@Parameter(in = ParameterIn.PATH,description = "Id of the User") @NotNull @NotEmpty @PathVariable(name = "userId") String userId);

    @Operation(summary = "Update User", description = "Update user details",tags = {"User"})
    @ApiResponse(responseCode = "200",description = "User Updated Successfully")
    @PutMapping(value = "/users/{userId}")
    ResponseEntity<UserResponse> updateUser(
            @Parameter(in = ParameterIn.PATH,description = "Id of the User") @NotNull @NotEmpty @PathVariable(name = "userId") String userId,
            @Parameter(in = ParameterIn.DEFAULT,description = "Body of the user") @Valid @NotNull @RequestBody UserRequest user
            );


    @Hidden
    @Operation(summary = "Upload User Profile", description = "Upload user profile")
    @ApiResponse(responseCode = "200",description = "User Profile Uploaded Success")
    @PatchMapping(value = "/users/{userId}/upload-profile",produces = {MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    ResponseEntity<byte[]> uploadUserProfile(
            @Parameter(in = ParameterIn.PATH,description = "Id of the User") @NotNull @NotEmpty @PathVariable(name = "userId") String userId,
            @Parameter(description = "Profile picture of the user") @NotNull @RequestPart(value = "formData") MultipartFile file
            );
}
