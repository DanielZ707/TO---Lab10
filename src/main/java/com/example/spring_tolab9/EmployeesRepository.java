package com.example.spring_tolab9;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class EmployeesRepository {
    ArrayList<EmployeeEntity> employeeEntities;

    public EmployeesRepository(ArrayList<EmployeeEntity> employeeEntities) {
        this.employeeEntities = employeeEntities;
    }

    public EmployeeEntity save(EmployeeEntity employeeEntity){
        employeeEntities.add(employeeEntity);
        return employeeEntity;
    }
}
