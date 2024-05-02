package tripqm.evn.java.system.mapper;

import org.mapstruct.Mapper;
import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.payload.request.SignupRequest;
import tripqm.evn.java.system.payload.response.UserResponse;

@Mapper(componentModel = "spring")
public interface SUserMapper {
    S_User toSUser(SignupRequest request);
    UserResponse toUserResponse(S_User user);
}
