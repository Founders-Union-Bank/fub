package org.fub.service.impl;

import lombok.AllArgsConstructor;
import org.fub.exception.UserNotFoundException;
import org.fub.model.GroupModel;
import org.fub.model.UserModel;
import org.fub.repository.GroupRepository;
import org.fub.repository.UserRepository;
import org.fub.request.GroupRequest;
import org.fub.response.GroupResponse;
import org.fub.response.UserResponse;
import org.fub.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private ModelMapper mapper;

    @Override
    public GroupResponse createGroup(String userId, GroupRequest group) {
        GroupModel model = mapper.map(group, GroupModel.class);
        model.setCreatedBy(userId);
        model.setCreatedDate(new Date());
        model.setGroupId((long) ThreadLocalRandom.current().nextInt(1000000, 10000000));

        GroupModel savedGroup = groupRepository.save(model);
        return mapper.map(savedGroup, GroupResponse.class);
    }

    @Override
    public GroupResponse fetchGroup(Long groupId) {
        GroupModel model = groupRepository.findById(groupId).orElseThrow(()->new RuntimeException("Group doesn't exist"));
        GroupResponse response=mapper.map(model, GroupResponse.class);
        List<UserModel> users = userRepository.findAllByGroupId(groupId);
        response.setUsers(users.stream().filter(Objects::nonNull)
                .map(user->mapper.map(user, UserResponse.class)).toList());

        return response;
    }

    @Override
    public GroupResponse addUsersToGroup(List<String> userIds, Long groupId) {
        GroupModel model = groupRepository.findById(groupId).orElseThrow(()->new RuntimeException("Group doesn't exist"));
        GroupResponse response=mapper.map(model, GroupResponse.class);

        userIds.forEach(userId -> {
            UserModel user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            user.setGroupId(groupId);
            userRepository.save(user);
        });

        List<UserModel> users = userRepository.findAllByGroupId(groupId);

        response.setUsers(users.stream().filter(Objects::nonNull)
                .map(user->mapper.map(user, UserResponse.class)).toList());

        return response;
    }
}
