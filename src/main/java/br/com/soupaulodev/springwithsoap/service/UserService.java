package br.com.soupaulodev.springwithsoap.service;

import br.com.soupaulodev.springwithsoap.entity.UserEntity;
import br.com.soupaulodev.springwithsoap.enums.OperationStatus;
import br.com.soupaulodev.springwithsoap.generated.*;
import br.com.soupaulodev.springwithsoap.mapper.UserMapper;
import br.com.soupaulodev.springwithsoap.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CreateUserResponse createUser(CreateUserRequest request) {
        userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail())
                .ifPresent((user) -> {
                    logger.warn("Can't create new user. Username or email already exists");
                    throw new RuntimeException("Username or Email already exists");
                });

        UserEntity userEntity = userMapper.createRequestToEntity(request);
        userEntity.setId(UUID.randomUUID());
        userEntity.setPassword(""); // TODO Encode password

        userRepository.save(userEntity);

        CreateUserResponse response = new CreateUserResponse();
        response.setUserId(userEntity.getId().toString());
        response.setOperationStatus(OperationStatus.SUCESS.toString());
        logger.info("New user created with id: {}", userEntity.getId());

        return response;
    }

    public GetUserInfoResponse getUserInfo(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User with id {} not found", userId);
                    return new RuntimeException("User not found");
                });


        User user = userMapper.entityToTypeObj(userEntity);

        GetUserInfoResponse response = userMapper.typeObjToGetUserInfoResponse(user);
        response.setOperationStatus(OperationStatus.SUCESS.toString());
        logger.info("User with id {} found successfully", userId);

        return response;
    }

    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        if (request.getEmail() == null || request.getUsername() == null
                || request.getPassword() == null || request.getActive() == null) {
            logger.warn("All fields are null. Can't update");
            throw new IllegalArgumentException("All fields are null");
        }

        UserEntity userEntity = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> {
                    logger.warn("User with id {} not found to update", request.getUserId());
                    return new RuntimeException("User not found");
                });

        userEntity.setEmail(request.getEmail() != null ? request.getEmail() : userEntity.getEmail());
        userEntity.setUsername(request.getUsername() != null ? request.getUsername() : userEntity.getUsername());
        userEntity.setPassword(request.getPassword() != null ? request.getPassword() : userEntity.getPassword());
        userEntity.setActive(request.getActive() != null
                ? Boolean.parseBoolean(request.getActive().toLowerCase())
                : userEntity.isActive());

        userRepository.save(userEntity);

        UpdateUserResponse response = new UpdateUserResponse();
        response.setOperationStatus("SUCESS");

        logger.info("User with id {} updated", userEntity.getId());
        return response;
    }

    public DeleteUserResponse deleteUser(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User with id {} not found", userId);
                    return new RuntimeException("User not found");
                });

        userRepository.delete(userEntity);

        DeleteUserResponse response = new DeleteUserResponse();
        response.setOperationStatus(OperationStatus.SUCESS.toString());

        logger.info("User with id {} deleted", userEntity.getId());
        return response;
    }
}
