package rw.ac.rca.termOneExam.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private ICityRepository cityRepositoryMock;

    @InjectMocks
    private CityService cityService;

    @Test
    public void getById_testSuccess() {
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new City(6L, "Gasogi", 23)));

        assertEquals("Gasogi", cityService.getById(6L).get().getName());
    }

    @Test
    public void getById_testNotFound() {
        when(cityRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(false, cityService.getById(7L).isPresent());
    }

    @Test
    public void getAll_testSuccess() {
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(new City(1L, "Nyamirambo", 10), new City(2L, "Nyabyondo", 13)));

        assertEquals("Nyabyondo", cityService.getAll().get(1).getName());
        assertEquals(13, cityService.getAll().get(1).getWeather());
        assertEquals((13*(9/5))+32, cityService.getAll().get(1).getFahrenheit());
    }

    @Test
    public void getAll_testFailure() {
        when(cityRepositoryMock.findAll()).thenReturn(null);

        assertEquals(null, cityService.getAll());
    }

    @Test
    public void existsByName_testSuccess() {
        when(cityRepositoryMock.existsByName("Musanze")).thenReturn(true);

        assertEquals(true, cityService.existsByName("Musanze"));
    }

    @Test
    public void existsByName_testFailure() {
        when(cityRepositoryMock.existsByName("Gasogi")).thenReturn(false);

        assertEquals(false, cityService.existsByName("Gasogi"));
    }

    @Test
    public void save_testSuccess() {
        City city = new City("Nyamirambo", 10);
        CreateCityDTO dto = new CreateCityDTO("Nyamirambo",10);

        when(cityRepositoryMock.save(any(City.class))).thenReturn(city);

        City savedCity = cityService.save(dto);

        assertEquals("Nyamirambo", savedCity.getName());

    }

    @Test
    public void save_testFailure() {
        CreateCityDTO dto = new CreateCityDTO("Nyamirambo",10);

        when(cityRepositoryMock.save(any(City.class))).thenReturn(null);

        City savedCity = cityService.save(dto);

        assertEquals(null, savedCity);

    }

}
