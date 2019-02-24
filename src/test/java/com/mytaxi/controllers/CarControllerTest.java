package com.mytaxi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.TestDataFactory;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.exceptions.ConstraintsViolationException;
import com.mytaxi.exceptions.EntityNotFoundException;
import com.mytaxi.services.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;

    @Test
    public void shouldReturnCarDetailsForGivenCarId() throws Exception {
        Car car = TestDataFactory.getCar(1l);

        Mockito.when(service.find(car.getId())).thenReturn(car);

        mvc.perform(get("/v1/cars/" + car.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", is(car.getLicensePlate())));
    }

    @Test
    public void shouldReturnStatusCodeNotFoundForNonExistingCarId() throws Exception {

        Mockito.when(service.find(5)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get("/v1/cars/5")).andExpect(status().is(404));
    }

    @Test
    public void shouldCreateAndReturnGivenCar() throws Exception {
        Car car = TestDataFactory.getCar(1l);

        Mockito.when(service.createOrUpdate(car)).thenReturn(car);

        mvc.perform(post("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.licensePlate", is(car.getLicensePlate())));
    }

    @Test
    public void shouldReturnStatusCodeForConflictWhenConstraintViolationForGivenCar() throws Exception {
        Car car = TestDataFactory.getCar(1l);
        Mockito.when(service.createOrUpdate(Mockito.any(Car.class))).thenThrow(ConstraintsViolationException.class);

        mvc.perform(post("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(car)))
                .andExpect(status().is(409));
    }

    @Test
    public void shouldUpdateAndReturnGivenCar() throws Exception {
        Car car = TestDataFactory.getCar(1l);

        Mockito.when(service.createOrUpdate(car)).thenReturn(car);

        mvc.perform(put("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", is(car.getLicensePlate())));
    }

    @Test
    public void shouldDeleteACarForAGivenCarId() throws Exception {
        Car car = TestDataFactory.getCar(1l);
        Mockito.doNothing().when(service).delete(car.getId());

        mvc.perform(delete("/v1/cars/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusCodeNotFoundForNonExistingCarIdWhenDelete() throws Exception {

        Mockito.doThrow(EntityNotFoundException.class).when(service).delete(5);

        mvc.perform(delete("/v1/cars/"+5)).andExpect(status().is(404));
    }

}
