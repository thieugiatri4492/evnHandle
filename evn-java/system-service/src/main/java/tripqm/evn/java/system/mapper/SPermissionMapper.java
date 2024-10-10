package tripqm.evn.java.system.mapper;

import org.mapstruct.Mapper;

import tripqm.evn.java.system.domain.S_Permission;
import tripqm.evn.java.system.payload.request.PermissionRequest;
import tripqm.evn.java.system.payload.response.PermissionResponse;

@Mapper(componentModel = "spring")
public interface SPermissionMapper {
    S_Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(S_Permission permission);
}
