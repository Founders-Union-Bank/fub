package org.fub.controller;

import lombok.AllArgsConstructor;
import org.fub.controller.API.GroupAPI;
import org.fub.request.CrewRequest;
import org.fub.response.CrewResponse;
import org.fub.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class GroupController implements GroupAPI {

    private GroupService groupService;

    @Override
    public ResponseEntity<CrewResponse> createGroup( CrewRequest group) {
        CrewResponse response = groupService.createGroup(group);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CrewResponse> addUsersToGroup(List<String> userIds, Long groupId) {
        CrewResponse response = groupService.addUsersToGroup(userIds,groupId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CrewResponse> getGroup(Long groupId) {
        CrewResponse response = groupService.fetchGroup(groupId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CrewResponse> removeUsersFromGroup(List<Long> userIds, Long groupId) {
        return null;
    }

    @Override
    public ResponseEntity<List<CrewResponse>> fetchAllGroups() {
        List<CrewResponse> responses = groupService.fetchAllGroups();
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }
}
