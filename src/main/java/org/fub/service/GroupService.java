package org.fub.service;

import org.fub.request.GroupRequest;
import org.fub.response.GroupResponse;

import java.util.List;


public interface GroupService {
    GroupResponse createGroup(String userId, GroupRequest group);

    GroupResponse fetchGroup(Long groupId);

    GroupResponse addUsersToGroup(List<String> userIds, Long groupId);
}
