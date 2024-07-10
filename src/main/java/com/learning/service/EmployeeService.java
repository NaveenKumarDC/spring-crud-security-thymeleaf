package com.learning.service;


import com.learning.entity.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Find all the employees
     * @return
     */
    List<Employee> findAll();

    /**
     * Find employee by ID
     * @param theId
     * @return
     */
    Employee findById(int theId);

    /**
     * Upsert employee
     * @param theEmployee
     * @return
     */
    Employee save(Employee theEmployee);

    /**
     * Delete by Employee ID.
     * @param theId
     */
    void deleteById(int theId);

    /**
     * Get Employee by Email
     * @param email
     * @return
     */
    Employee getByEmail(String email);

    /**
     * Get all the employees ordered by First Name
     * @param limit
     * @param offset
     * @return
     */
    List<Employee> getEmployeesByName(long limit , long offset);

}
