package org.fub.service;

import org.fub.request.CrewRequest;
import org.fub.response.CrewResponse;

import java.util.List;


public interface GroupService {
    CrewResponse createGroup( CrewRequest group);

    CrewResponse fetchGroup(Long groupId);

    CrewResponse addUsersToGroup(List<String> userIds, Long groupId);

    List<CrewResponse> fetchAllGroups();
}
