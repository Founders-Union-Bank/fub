package org.fub.controller;

import lombok.AllArgsConstructor;
import org.fub.controller.API.GroupAPI;
import org.fub.request.GroupRequest;
import org.fub.response.GroupResponse;
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
    public ResponseEntity<GroupResponse> createGroup(String userId, GroupRequest group) {
        GroupResponse response = groupService.createGroup(userId,group);
        return new ResponseEntity<GroupResponse>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GroupResponse> addUsersToGroup(List<String> userIds, Long groupId) {
        GroupResponse response = groupService.addUsersToGroup(userIds,groupId);
        return new ResponseEntity<GroupResponse>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupResponse> getGroup(Long groupId) {
        GroupResponse response = groupService.fetchGroup(groupId);
        return new ResponseEntity<GroupResponse>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupResponse> removeUsersFromGroup(List<Long> userIds, Long groupId) {
        return null;
    }
}
