package org.fub.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.fub.request.CrewRequest;
import org.fub.response.CrewResponse;
import org.fub.response.UserGroupsResponse;
import org.fub.response.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Tag(name = "Group")
public interface GroupAPI {

    @Operation(summary = "Create group", description = "Create a group to add users")
    @ApiResponse(responseCode = "201", description = "Group Created Successfully")
    @ApiResponse(responseCode = "404", description = "Group Not Found")
    @PostMapping(value = "/groups", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<CrewResponse> createGroup(
            @Parameter(in = ParameterIn.DEFAULT, description = "Group data") @NotNull @Valid @RequestBody CrewRequest group
    );

    @Operation(summary = "Add users to group", description = "Add users to group")
    @ApiResponse(responseCode = "200", description = "Users Added success")
    @ApiResponse(responseCode = "404", description = "Group not found")
    @PatchMapping(value = "/groups/{groupId}/add-user", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<UserGroupsResponse> addUsersToGroup(
            @Parameter(in = ParameterIn.QUERY, description = "User the needs to be added to the group") @NotNull @NotEmpty @RequestParam(value = "ids") List<String> userIds,
            @Parameter(in = ParameterIn.PATH, description = "Group Id") @NotNull @PathVariable(value = "groupId") Long groupId
    );

    @Operation(summary = "Fetch Group", description = "Get Group return group group details")
    @ApiResponse(responseCode = "200", description = "Group details fetched successfully")
    @ApiResponse(responseCode = "404", description = "Group Not found")
    @GetMapping(value = "/groups/{groupId}")
    ResponseEntity<CrewResponse> getGroup(
            @Parameter(in = ParameterIn.PATH, description = "Group Id") @NotNull @PathVariable(value = "groupId") Long groupId
    );

    @Operation(summary = "Remove Users from groups", description = "Remove users from group")
    @ApiResponse(responseCode = "200", description = "Users Removed from the group successfully")
    @ApiResponse(responseCode = "404", description = "Group Not found")
    @PostMapping(value = "/groups/{groupId}/remove-users")
    ResponseEntity<CrewResponse> removeUsersFromGroup(
            @Parameter(in = ParameterIn.QUERY, description = "User the needs to be added to the group") @NotNull @NotEmpty @RequestParam(value = "ids") List<Long> userIds,
            @Parameter(in = ParameterIn.PATH, description = "Group Id") @NotNull @PathVariable(value = "groupId") Long groupId
    );

    @Operation(summary = "Fetch All groups")
    @GetMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CrewResponse>> fetchAllGroups(
            @Parameter(in = ParameterIn.QUERY, description = "Search text") @NotNull @RequestParam(value = "searchText") String searchText
    );

    @Operation(summary = "Fetch Groups by user id")
    @GetMapping(value = "/groups/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Users Removed from the group successfully")
    @ApiResponse(responseCode = "404", description = "Group Not found")
    ResponseEntity<UserGroupsResponse> fetchGroupsByUser(
            @Parameter(in = ParameterIn.QUERY, description = "userId") @NotNull @RequestParam(value = "userId") String userId
    );

    @Operation(summary = "Fetch Users from the crew")
    @GetMapping(value = "/groups/{crewId}/users")
    @ApiResponse(responseCode = "200", description = "Users Removed from the group successfully")
    @ApiResponse(responseCode = "404", description = "Group Not found")
    ResponseEntity<List<UserResponse>> fetchUsersFromGroup(
            @Parameter(in = ParameterIn.PATH, description = "Crew") @NotNull @PathVariable(value = "crewId") Long crewId
    );
}
