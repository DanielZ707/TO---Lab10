package com.example.spring_tolab9;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EmployeeEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotNull
    private LocalDate employmentDate;

    public EmployeeEntity()
    {
        super();
    }

    public EmployeeEntity(String name, String surname, LocalDate employmentDate) {
        this.name = name;
        this.surname = surname;
        this.employmentDate = employmentDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    @Override
    public String toString() {
        return "{" +
                "name=" + name +
                ", surname=" + surname +
                ", employmentDate=" + employmentDate +
                '}';
    }
}
