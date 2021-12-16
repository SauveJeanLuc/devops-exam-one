package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CityUtilTest {
    @Autowired
    private ICityRepository cityRepository;

    @SpyBean
    private CityService cityService;

    @Mock
    private ICityRepository cityRepositoryMock;

    @Test
    public void noCityWithWeatherMoreThan40() {
        assertEquals(false, cityRepository.existsByWeatherGreaterThan(40));
    }

    @Test
    public void noCityWithWeatherLessThan10() {
        assertEquals(false, cityRepository.existsByWeatherLessThan(10));
    }

    @Test
    public void mandatoryCitiesContained() {
        assertEquals(true, cityRepository.existsByName("Musanze"));
        assertEquals(true, cityRepository.existsByName("Kigali"));
    }

    @Test
    public void testSpying(){
        assertThat(this.cityService.getAll().get(1).getName()).isEqualTo("Musanze");
    }

    @Test
    public void testMocking(){
        when(cityRepositoryMock.findAll()).thenReturn(Arrays.asList(new City(1L, "Nyamirambo", 10), new City(2L, "Musanze", 13)));

        assertThat(this.cityService.getAll().get(1).getName()).isEqualTo("Musanze");
    }
}
