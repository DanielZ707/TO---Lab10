package com.example.spring_tolab9;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Service
public class EmployeesController {
    EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesController(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @PostMapping("/employees")
    public EmployeeEntity newEmployee(@RequestBody @Valid EmployeeEntity employeeEntity) {
        if(employeeEntity.getName().isEmpty() || employeeEntity.getSurname().isEmpty() || employeeEntity.getEmploymentDate() == null) {
            return null;
        }
        if(employeesRepository.employeeEntities.contains(employeeEntity)) {
            return null;
        }
        return employeesRepository.save(employeeEntity);
    }

    @GetMapping("/employees")
    public ArrayList<EmployeeEntity> showEmployees() {
        return employeesRepository.employeeEntities;
    }

    public EmployeesRepository getEmployeesRepository() {
        return employeesRepository;
    }

    public void setEmployeesRepository(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }
}
