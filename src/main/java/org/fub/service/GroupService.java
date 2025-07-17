package org.fub.service;

import org.fub.request.CrewRequest;
import org.fub.response.CrewResponse;
import org.fub.response.UserGroupsResponse;
import org.fub.response.UserResponse;

import java.util.List;


public interface GroupService {
    CrewResponse createGroup( CrewRequest group);

    CrewResponse fetchGroup(Long groupId);

    UserGroupsResponse addUsersToGroup(List<String> userIds, Long groupId);

    List<CrewResponse> fetchAllGroups(String searchText);

    UserGroupsResponse fetchGroupsByUserId(String userId);

    List<UserResponse> getAllUsersFromTheCrew(Long crewId);
}
