package app.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import app.model.Car;
import app.model.Category;
import app.service.CarService;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean CarService service;

    @Test
    void getAllCars_returnsList() throws Exception {
        Car car = new Car(2026, "Porsche", "911 GT3", Category.SPORTSCAR, false);
        when(service.find(null, null)).thenReturn(List.of(car));

        mockMvc.perform(get("/cars"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].make").value("Porsche"));
    }

    @Test
    void createCar_invalidInput_returns400() throws Exception {
        mockMvc.perform(post("/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"year\":1000,\"make\":\"\",\"model\":\"x\",\"category\":\"SUPERCAR\",\"forSale\":true}"))
        .andExpect(status().isBadRequest());
    }
}
