package tripqm.evn.java.profile.mapper;

import org.mapstruct.Mapper;

import tripqm.evn.java.profile.dtos.response.RevenueResponse;
import tripqm.evn.java.profile.entities.Revenue;

@Mapper(componentModel = "spring")
public interface RevenueMapper {
    RevenueResponse toRevenueResponse(Revenue revenueEntity);
}
