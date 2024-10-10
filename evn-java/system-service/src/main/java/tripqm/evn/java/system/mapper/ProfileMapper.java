package tripqm.evn.java.system.mapper;

import org.mapstruct.Mapper;

import tripqm.evn.java.system.payload.request.ProfileCreationRequest;
import tripqm.evn.java.system.payload.request.SignupRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(SignupRequest user);
}
