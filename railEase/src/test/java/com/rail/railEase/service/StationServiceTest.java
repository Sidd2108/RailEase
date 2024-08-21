package com.rail.railEase.service;

import com.rail.railEase.dto.StationDto;
import com.rail.railEase.exception.EntryExistsException;
import com.rail.railEase.model.Station;
import com.rail.railEase.model.Users;
import com.rail.railEase.repository.StationRepo;
import com.rail.railEase.repository.UsersRepo;
import com.rail.railEase.service.Station.StationServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.rail.railEase.constant.Constants.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {StationRepo.class})
public class StationServiceTest {

    @Mock
    private StationRepo stationRepo;

    @Mock
    private UsersRepo usersRepo;
    @InjectMocks
    private StationServiceImpl stationService;

    @Test
    @Order(1)

    public void testCreateStation() throws EntryExistsException {
        Station station = new Station(1,"Vashi",LocalDateTime.now(),'H', 'H', 25.0);
        Optional<StationDto> stationDto = Optional.of(new StationDto(1, "Vashi", 25.0));
        when(stationRepo.existsById(stationDto.get().getStationId())).thenReturn(false);
        when(stationRepo.save(station)).thenReturn(station);

        int result = stationService.createStation(stationDto);

        assertThat(result).isEqualTo(SUCCESS);
    }
}
