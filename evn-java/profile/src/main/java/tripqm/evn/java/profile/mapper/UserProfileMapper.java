package tripqm.evn.java.profile.mapper;

import org.mapstruct.Mapper;

import tripqm.evn.java.profile.dtos.request.ProfileCreationRequest;
import tripqm.evn.java.profile.dtos.response.UserProfileResponse;
import tripqm.evn.java.profile.entities.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
