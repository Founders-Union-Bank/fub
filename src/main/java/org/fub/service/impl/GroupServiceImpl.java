package org.fub.service.impl;

import lombok.AllArgsConstructor;
import org.fub.exception.UserNotFoundException;
import org.fub.model.CrewModel;
import org.fub.model.UserModel;
import org.fub.repository.GroupRepository;
import org.fub.repository.UserRepository;
import org.fub.request.CrewRequest;
import org.fub.response.CrewResponse;
import org.fub.response.UserResponse;
import org.fub.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
        CrewModel model = groupRepository.findById(groupId).orElseThrow(()->new RuntimeException("Group doesn't exist"));
        CrewResponse response=mapper.map(model, CrewResponse.class);
//        List<UserModel> users = userRepository.findAllByGroupId(groupId);
//        response.setUsers(users.stream().filter(Objects::nonNull)
//                .map(user->mapper.map(user, UserResponse.class)).toList());

        return response;
    }

    @Override
    public CrewResponse addUsersToGroup(List<String> userIds, Long groupId) {
        CrewModel model = groupRepository.findById(groupId).orElseThrow(()->new RuntimeException("Group doesn't exist"));
        CrewResponse response=mapper.map(model, CrewResponse.class);

        userIds.forEach(user->{
           UserModel userModel= userRepository.findById(user).orElseThrow(()->new UserNotFoundException("User Doesn't exist for the id : "+user));
           model.addUser(userModel);

           response.addUser(mapper.map(userModel,UserResponse.class));
        });

        groupRepository.save(model);

        return response;
    }

    @Override
    public List<CrewResponse> fetchAllGroups() {
        List<CrewModel> crews =groupRepository.findAll();
        return crews.stream().map(crew->{
            return mapper.map(crew,CrewResponse.class);
        }).toList();
    }
}
