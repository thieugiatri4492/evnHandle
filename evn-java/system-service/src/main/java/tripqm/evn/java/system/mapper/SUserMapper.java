package tripqm.evn.java.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.payload.request.SignupRequest;
import tripqm.evn.java.system.payload.request.UserUpdateRequest;
import tripqm.evn.java.system.payload.response.UserResponse;

@Mapper(componentModel = "spring")
public interface SUserMapper {
    S_User toSUser(SignupRequest request);

    UserResponse toUserResponse(S_User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget S_User user, UserUpdateRequest request);
}
