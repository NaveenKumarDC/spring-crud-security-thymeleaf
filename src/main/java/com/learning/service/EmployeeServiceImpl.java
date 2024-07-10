package com.learning.service;

import com.learning.dao.EmployeeRepository;
import com.learning.entity.Employee;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import org.springframework.util.CollectionUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll(Sort.sort(Employee.class));
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepository.findById(theId);

        Employee theEmployee = null;

        if (result.isPresent()) {
            theEmployee = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }

        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {
        Employee existingEmployee = getByEmail(theEmployee.getEmail());
        if(Objects.nonNull(existingEmployee)) {
            existingEmployee.setId(existingEmployee.getId());
            if(!existingEmployee.getFirstName().equals(theEmployee.getFirstName())) {
                existingEmployee.setFirstName(theEmployee.getFirstName());
            }
            if(!existingEmployee.getLastName().equals(theEmployee.getLastName())) {
                existingEmployee.setLastName(theEmployee.getLastName());
            }
            if(!existingEmployee.getEmail().equals(theEmployee.getEmail())) {
                existingEmployee.setEmail(theEmployee.getEmail());
            }
            return employeeRepository.save(existingEmployee);
        } else {
            return employeeRepository.save(theEmployee);
        }
    }

    @Override
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }

    @Override
    public Employee getByEmail(String email) {
        //Type query
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where e.email = :email",
                Employee.class);
        query.setParameter("email", email);
        List<Employee> employeeList = query.getResultList();
        return !CollectionUtils.isEmpty(employeeList) ? employeeList.get(0) : null;
    }

    @Override
    public List<Employee> getEmployeesByName(long limit, long offset) {
        //Type query
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e ORDER BY e.firstName ASC LIMIT :limit OFFSET :offset ",
                Employee.class);
        if(limit < 0 ) {
            limit = 50;
        }
        if(offset < 0 ) {
            offset = 0;
        }
        query.setParameter("limit", limit);
        query.setParameter("offset", offset);
        List<Employee> employeeList = query.getResultList();
        return !CollectionUtils.isEmpty(employeeList) ? employeeList : new ArrayList<>();
    }
}






