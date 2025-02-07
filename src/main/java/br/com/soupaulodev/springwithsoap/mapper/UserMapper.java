package br.com.soupaulodev.springwithsoap.mapper;

import br.com.soupaulodev.springwithsoap.entity.UserEntity;
import br.com.soupaulodev.springwithsoap.generated.CreateUserRequest;
import br.com.soupaulodev.springwithsoap.generated.GetUserInfoResponse;
import br.com.soupaulodev.springwithsoap.generated.User;
import br.com.soupaulodev.springwithsoap.util.DateTimeUtil;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity createRequestToEntity(CreateUserRequest request) {
        UserEntity entity = new UserEntity();
        entity.setEmail(request.getEmail());
        entity.setUsername(request.getUsername());
        entity.setActive(true);
        return entity;
    }

    public User entityToTypeObj(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId().toString());
        user.setEmail(entity.getEmail());
        user.setUsername(entity.getUsername());
        user.setCreatedAt(DateTimeUtil.toXMLGregorianCalendar(entity.getCreatedAt()));
        user.setUpdatedAt(DateTimeUtil.toXMLGregorianCalendar(entity.getUpdatedAt()));

        return user;
    }

    public GetUserInfoResponse typeObjToGetUserInfoResponse(User user) {
        GetUserInfoResponse response = new GetUserInfoResponse();
        response.setUser(user);
        return response;
    }
}
