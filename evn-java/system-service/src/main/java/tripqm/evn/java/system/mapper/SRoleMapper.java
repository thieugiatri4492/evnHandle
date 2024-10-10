package tripqm.evn.java.system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tripqm.evn.java.system.domain.S_Role;
import tripqm.evn.java.system.payload.request.RoleRequest;
import tripqm.evn.java.system.payload.response.RoleResponse;

@Mapper(componentModel = "spring")
public interface SRoleMapper {
    @Mapping(target = "permissions", ignore = true)
    S_Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(S_Role role);
}
