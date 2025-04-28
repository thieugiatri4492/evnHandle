package tripqm.evn.java.power.mapper;

import org.mapstruct.Mapper;

import tripqm.evn.java.power.dto.response.PowerSellDataResponse;
import tripqm.evn.java.power.entity.PowerSellData;

@Mapper(componentModel = "spring")
public interface PowerDataMapper {
    PowerSellDataResponse toPowerSellDataResponse(PowerSellData psData);
}
