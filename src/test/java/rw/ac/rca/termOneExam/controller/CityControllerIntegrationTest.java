package rw.ac.rca.termOneExam.controller;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.utils.APICustomResponse;


import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getById_testSuccess() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/id/104", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getById_testNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/id/900", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAll_testSuccess() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);
        System.out.println(response);
        JSONAssert.assertEquals("[{\"id\":101,\"name\":\"Kigali\",\"weather\":24.0,\"fahrenheit\":0.0},{\"id\":102,\"name\":\"Musanze\",\"weather\":18.0,\"fahrenheit\":0.0},{\"id\":103,\"name\":\"Rubavu\",\"weather\":20.0,\"fahrenheit\":0.0},{\"id\":104,\"name\":\"Nyagatare\",\"weather\":28.0,\"fahrenheit\":0.0}]", response, true);
    }

    @Test
    public void createItem_Success() {

        CreateCityDTO city=new CreateCityDTO("Rwamagana",30);


        ResponseEntity<City> response = this.restTemplate.postForEntity("/api/cities/add", city, City.class);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Rwamagana",response.getBody().getName());
    }

    @Test
    public void createItem_AlreadyExistsFailure(){

        CreateCityDTO city=new CreateCityDTO("Musanze",30);

        ResponseEntity<APICustomResponse> response = this.restTemplate.postForEntity("/api/cities/add", city, APICustomResponse .class);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(false, response.getBody().isStatus());
        assertEquals("City name " + city.getName() + " is registered already", response.getBody().getMessage());
    }
}
