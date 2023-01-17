package com.example.spring_tolab9;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeesController.class)
public class EmployeesTests {

    @MockBean
    private EmployeesController employeesController;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void ShouldReturnOkWhenSendGet() throws Exception {
        mvc.perform(get("/employees"))
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldReturnStatusOkWhenAnEmployeeAdded() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity("Janusz", "Nowak", LocalDate.parse("2015-12-31"));

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(employeeEntity))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnRightOneEmployeeAfterAdded() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity("Janusz", "Nowak", LocalDate.parse("2001-01-12"));
        when(employeesController.newEmployee(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        String jsonResponse = mvc
                .perform(MockMvcRequestBuilders
                        .post("/employees")
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(employeeEntity))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        EmployeeEntity responseEntity = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(jsonResponse, EmployeeEntity.class);

        Assertions.assertEquals(employeeEntity.toString(), responseEntity.toString());
    }

    @Test
    void shouldReturnRightListOfEmployees() throws Exception {
        ArrayList<EmployeeEntity> employeeEntities = new ArrayList<>();
        employeeEntities.add(new EmployeeEntity("Karolina", "Lukasik", LocalDate.parse("2001-01-12")));
        employeeEntities.add(new EmployeeEntity("Ewelina", "Twarowska", LocalDate.parse("2022-10-03")));
        employeeEntities.add(new EmployeeEntity("Szymon", "Bogucki", LocalDate.parse("2012-05-27")));
        employeeEntities.add(new EmployeeEntity("Kacper", "Jasinski", LocalDate.parse("1997-07-05")));
        when(employeesController.showEmployees())
                .thenReturn(employeeEntities);


        String jsonResponse = mvc
                .perform(MockMvcRequestBuilders
                        .get("/employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(4)))
                .andReturn().getResponse().getContentAsString();

        ArrayList returnedEmployeeEntities = new ObjectMapper().readValue(jsonResponse, ArrayList.class);

        Assertions.assertEquals(employeeEntities.toString(), returnedEmployeeEntities.toString());
    }


    @Test
    public void ShouldReturnStatusBadRequestWhenNameIsEmpty() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity("", "Nowak", LocalDate.parse("2015-12-31"));

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(employeeEntity))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ShouldReturnStatusBadRequestWhenNameIsNull() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity(null, "Nowak", LocalDate.parse("2015-12-31"));

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(employeeEntity))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ShouldReturnStatusBadRequestWhenSurNameIsEmpty() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity("Janusz", "", LocalDate.parse("2015-12-31"));

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(employeeEntity))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ShouldReturnStatusBadRequestWhenSurNameIsNull() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity("Janusz", null, LocalDate.parse("2015-12-31"));

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(employeeEntity))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void ShouldReturnStatusBadRequestWhenDateIsNull() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity("Janusz", "Nowak", null);

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(employeeEntity))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
