package tripqm.evn.java.profile.service;

import java.util.List;


import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import tripqm.evn.java.profile.dtos.response.RevenueResponse;
import tripqm.evn.java.profile.entities.Revenue;
import tripqm.evn.java.profile.mapper.RevenueMapper;
import tripqm.evn.java.profile.repository.RevenueRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    RevenueRepository revenueRepository;
    RevenueMapper revenueMapper;

    public List<RevenueResponse> getAllRevenue() {
        List<Revenue> revenue = revenueRepository.findAll();
        return revenue.stream().map(revenueMapper::toRevenueResponse).toList();
    }
}
