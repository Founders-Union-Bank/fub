package org.fub.service.impl;

import lombok.AllArgsConstructor;
import org.fub.exception.UserNotFoundException;
import org.fub.model.CrewModel;
import org.fub.model.UserModel;
import org.fub.repository.GroupRepository;
import org.fub.repository.UserRepository;
import org.fub.request.CrewRequest;
import org.fub.response.CrewResponse;
import org.fub.response.UserGroupsResponse;
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
    public CrewResponse createGroup(CrewRequest group) {
        CrewModel model = mapper.map(group, CrewModel.class);
        model.setCreatedDate(new Date());
        model.setCrewId((long) ThreadLocalRandom.current().nextInt(1000000, 10000000));

        CrewModel savedGroup = groupRepository.save(model);
        return mapper.map(savedGroup, CrewResponse.class);
    }

    @Override
    public CrewResponse fetchGroup(Long groupId) {
        CrewModel model = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group doesn't exist"));
        return mapper.map(model, CrewResponse.class);
    }

    @Override
    public UserGroupsResponse addUsersToGroup(List<String> userIds, Long groupId) {
        CrewModel model = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group doesn't exist"));
        userIds.forEach(user -> {
            UserModel userModel = userRepository.findById(user).orElseThrow(() -> new UserNotFoundException("User Doesn't exist for the id : " + user));
            userModel.addCrew(model);
            model.addUser(userModel);
        });
        model.setTotalMember(model.getUsers().size());
        groupRepository.save(model);

        return fetchGroupsByUserId(userIds.get(0));
    }

    @Override
    public List<CrewResponse> fetchAllGroups(String searchText) {
        List<CrewModel> crews = groupRepository.findByCrewNameLikeIgnoreCase(searchText);
        return crews.stream().map(crew -> {
            return mapper.map(crew, CrewResponse.class);
        }).toList();
    }

    @Override
    public UserGroupsResponse fetchGroupsByUserId(String userId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
        UserGroupsResponse response = new UserGroupsResponse();
        response.setCrews(userModel
                .getCrews()
                .stream()
                .map(crew->mapper.map(crew,CrewResponse.class))
                .toList());
        return response;
    }

    @Override
    public List<UserResponse> getAllUsersFromTheCrew(Long crewId) {
        CrewModel crew = groupRepository.findById(crewId).orElseThrow(() -> new RuntimeException("Crew Not Found"));
        return crew.getUsers().stream().map(user -> mapper.map(user, UserResponse.class)).toList();
    }
}
