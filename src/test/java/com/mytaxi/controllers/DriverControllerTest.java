package com.mytaxi.controllers;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.TestDataFactory;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.datatransferobjects.SearchCriteria;
import com.mytaxi.exceptions.ConstraintsViolationException;
import com.mytaxi.exceptions.EntityNotFoundException;
import com.mytaxi.services.DriverService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DriverService service;

    @Test
    public void shouldReturnDriverDetailsForGivenDriverId() throws Exception {
        Driver driver = TestDataFactory.getDriver(1l);

        Mockito.when(service.find(driver.getId())).thenReturn(driver);

        mvc.perform(get("/v1/drivers/" + driver.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(driver.getUsername())));
    }

    @Test
    public void shouldReturnStatusCodeNotFoundForNonExistingDriverId() throws Exception {

        Mockito.when(service.find(5)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get("/v1/drivers/5")).andExpect(status().is(404));
    }

    @Test
    public void shouldCreateAndReturnGivenDriver() throws Exception {
        Driver driver = TestDataFactory.getDriver(1l);

        Mockito.when(service.createOrUpdate(driver)).thenReturn(driver);

        mvc.perform(post("/v1/drivers")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(driver)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(driver.getUsername())));
    }

    @Test
    public void shouldReturnStatusCodeForConflictWhenConstraintViolationForGivenDriver() throws Exception {
        Driver driver = TestDataFactory.getDriver(1l);
        Mockito.when(service.createOrUpdate(Mockito.any(Driver.class))).thenThrow(ConstraintsViolationException.class);

        mvc.perform(post("/v1/drivers")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(driver)))
                .andExpect(status().is(409));
    }


    @Test
    public void shouldDeleteADriverForAGivenDriverId() throws Exception {
        Mockito.doNothing().when(service).delete(Mockito.any(Integer.class));

        mvc.perform(delete("/v1/drivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusCodeNotFoundForNonExistingDriverWhenDelete() throws Exception {

        Mockito.doThrow(EntityNotFoundException.class).when(service).delete(5);

        mvc.perform(delete("/v1/drivers/"+5)).andExpect(status().is(404));
    }

    @Test
    public void shouldUpdateLocationForGivenDriver() throws Exception {

        Mockito.doNothing().when(service).updateLocation(Mockito.any(Long.class), Mockito.any(Double.class),Mockito.any(Double.class));

        mvc.perform(put("/v1/drivers/1")
                .param("longitude", "40").param("latitude", "50")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSelectCarForGivenDriver() throws Exception {

        Driver driver = TestDataFactory.getDriver(1l);
        Car car = TestDataFactory.getCar(1l);
        driver.setAssignedCar(car);

        Mockito.when(service.selectCar(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(driver);

        MvcResult result = mvc.perform(post("/v1/drivers/selectCar")
                .param("driverId", "1").param("carId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains(driver.getUsername()));
    }

    @Test
    public void shouldDeSelectCarForGivenDriver() throws Exception {

        Driver driver = TestDataFactory.getDriver(1l);
        driver.setAssignedCar(null);

        Mockito.when(service.deselectCar(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(driver);

        MvcResult result = mvc.perform(post("/v1/drivers/deselectCar")
                .param("driverId", "1").param("carId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains(driver.getUsername()));
        Assert.assertFalse(responseBody.contains("assignedCar"));
    }

    @Test
    public void shouldSearchDriversForGivenSearchCriteria() throws Exception {

        Driver driver = TestDataFactory.getDriver(1l);
        SearchCriteria searchCriteria = new SearchCriteria();


        Mockito.when(service.search(Mockito.any(SearchCriteria.class))).thenReturn(Arrays.asList(driver));

        MvcResult result = mvc.perform(post("/v1/drivers/search")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(searchCriteria)))
                .andExpect(status().isOk()).andReturn();

        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains(driver.getUsername()));
    }


}
