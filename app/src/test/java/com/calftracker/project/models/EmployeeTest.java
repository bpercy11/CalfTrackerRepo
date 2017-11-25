package com.calftracker.project.models;

import org.junit.Test;

public class EmployeeTest {
    @Test
    public void testEmployee() {
        String name = "Dan";
        Employee employee = new Employee(name);
        assert(employee.getName().equals(name));

        name = "Jose";
        employee.setName(name);
        String newName = employee.getName();
        assert(newName.equals(name));
    }
}