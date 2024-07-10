package com.learning.controller;

import java.util.List;
import java.util.Objects;


import com.learning.entity.Employee;
import com.learning.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Naveen Kumar D C
 * Date: 08/07/24
 */
@Controller
@RequestMapping("/v1/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    // add mapping for "/list"

    @GetMapping("/list")
    public String listEmployees(Model theModel) {

        long limit = Objects.nonNull(theModel.getAttribute("limit")) ? Long.valueOf(String.valueOf(theModel.getAttribute("limit"))) : 50;
        long offSet = Objects.nonNull(theModel.getAttribute("offSet")) ? Long.valueOf(String.valueOf(theModel.getAttribute("offSet"))) : 0;
        // get the employees from db
        List<Employee> theEmployees = employeeService.getEmployeesByName(limit, offSet);

        // add to the spring model
        theModel.addAttribute("employees", theEmployees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId,
                                    Model theModel) {

        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        // set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

        // save the employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submissions
        return "redirect:/v1/employees/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {

        // delete the employee
        employeeService.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/v1/employees/list";

    }
}









