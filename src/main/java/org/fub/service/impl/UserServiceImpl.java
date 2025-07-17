package org.fub.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fub.exception.UserNotFoundException;
import org.fub.model.UserModel;
import org.fub.repository.UserRepository;
import org.fub.request.UserRequest;
import org.fub.response.UserResponse;
import org.fub.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest user) {
        Optional<UserModel> model = userRepository.findByEmail(user.getEmail());
        if (model.isPresent()) {
            throw new RuntimeException("User Already Exist");
        }
        UserModel users = modelMapper.map(user, UserModel.class);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setUserId(UUID.randomUUID().toString());
        users.setRoles("USER");
        users.setCreateDate(new Date());
        UserModel userModel = userRepository.save(users);
        return modelMapper.map(userModel, UserResponse.class);
    }

    @Override
    public UserResponse fetchUser(String userId) {
        Optional<UserModel> model = userRepository.findById(userId);
        if (model.isEmpty()) {
            throw new UserNotFoundException("User with user id " + userId + " not found");
        }
        return modelMapper.map(model, UserResponse.class);
    }

    @Override
    public List<UserResponse> fetchUsers() {
        List<UserModel> models = userRepository.findAll();
        return models.stream().filter(Objects::nonNull).map(user -> modelMapper.map(user, UserResponse.class)).toList();
    }

    @Override
    public boolean deleteUser(String userId) {
        Optional<UserModel> model = userRepository.findById(userId);
        if (model.isEmpty()) {
            throw new UserNotFoundException("User with user id " + userId + " not found");
        }
        model.get().setActive(false);

        UserModel userModel = userRepository.save(model.get());
        return userModel != null;
    }

    @Override
    public UserResponse updateUser(String userId, UserRequest user) {
        Optional<UserModel> model = userRepository.findById(userId);
        if (model.isEmpty()) {
            throw new UserNotFoundException("User with user id " + userId + " not found");
        }
        UserModel oldUserData = model.get();
        oldUserData.setFirstName(user.getFirstName());
        oldUserData.setLastName(user.getLastName());
        oldUserData.setMobileNumber(user.getMobileNumber());
        oldUserData.setPassword(passwordEncoder.encode(user.getPassword()));
        UserModel savedUser = userRepository.save(oldUserData);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public byte[] uploadProfile(String userId, MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] profileData = file.getBytes();
                UserModel model = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with user id " + userId + " not found"));
//                model.setProfileImage(profileData);
                userRepository.save(model);
                return profileData;
            } catch (Exception e) {
                log.info("Updating profile image to user failed",e.getMessage());
                throw new RuntimeException("Updating profile image to user failed :"+e.getMessage(),e);
            }
        }
        return new byte[0];
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel model = userRepository.findByEmail(username)
                .orElseThrow();
        return User.withUsername(model.getEmail())
                .password(model.getPassword()).roles(model.getRoles()).build();
    }
}
