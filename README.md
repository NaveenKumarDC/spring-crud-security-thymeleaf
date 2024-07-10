# spring-crud-security-thymeleaf
CRUD application with integrated Spring security




### Project Details
* By default Naveen/test123 user can perform CRUD operation on Employee entity.
* TODO
  * Create a new Admin USER and Perform CRUD operation on Employee entity

## Overview

 This tutorial will focus on Login with Spring Security and provide a option for users to login with default users created by loading the default users into PosgreSQL 15 using the data.sql. 
 
And providing UI screen for login using the default users pre-loaded and then visualise employee list scree that supports CRUD operations to add an Employee, Update, and Delete.


## What are we going to Achieve as part of this tutorial:

* Create a Spring boot application to create a crud operation for an entity called Employee using spring JPA for default CRUD Operation
* Allow only ADMIN users to created/update/delete employees Achieved using the Spring Security 6.1.0+
* Allow users with USER role to access Employee Achieved using the Spring Security 6.1.0+

## Spring Security 

There are three ways to enable Spring security to restrict accessing of any Rest Endpoints
** InMemoryUserDetails Manager

** One can set the default users with pre-defined roles

** JDBC DataSource integration

***Provide data source where the Spring security create required User and Authorities table in the configured database.

Custom User And Roles table integration

Sometimes it is required to use the Custom user and roles table, in this scenario how can we use the Spring security to enable these users and roles tables to used for authentication and authorization

## Spring ThymeLeaf Integration

### Custom login page for logging in to application

### Employee Directory is loaded as Default Page on successfull login

### CRUD operations supported

#### Create Employee

#### Update Employee

#### Delete Employee

# Project creation steps

## Springboot application Creation

### Creation of Spring boot application from the Spring initializer

Follow below steps to create Spring boot application with required dependencies added using the Spring initialiser

* Install IntelliJ Ultimate Early Access Program (EAP)

*  Install PostGreSQL15

* Create a user and password or use the default user (postgres) and password (postgres)

* Create a DataBase (crud_application)

* Goto IntelliJEAP -> File -> New project -> Spring Boot

* Add below dependencies

  * Spring web

  * Spring security

  * PostgresQL Driver

  * Spring Thymeleaf

## Spring Boot POM.xml

Below is the POM xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.1</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.learning</groupId>
    <artifactId>spring-crud-jpa-psql-security-thymeleaf-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-thymeleaf-demo</name>
    <description>spring-thymeleaf-demo</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-config</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

## Enable Spring Security

The Maven Dependencies

When working with Spring Boot, the spring-boot-starter-security starter will automatically include all dependencies, such as spring-security-core, spring-security-web, and spring-security-config among others:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### Spring security provides three mechanisms to enable security for all the APIs that are exposed from the micro services

To Enable Spring Security create a class and annotate @Configuration and implement "filterChain" method to provide the login page and which API patterns needs to restricted with what roles.

There are Three mechanisms you can enable user Authentication and Authorization

* InMemoryUserDetails

```java
@Bean
public InMemoryUserDetailsManager userDetailsManager() {

    UserDetails admin = User.builder()
            .username("admin")
            .password("{noop}admin")
            .roles("EMPLOYEE", "MANAGER", "ADMIN")
            .build();

    return new InMemoryUserDetailsManager(admin);
}
```

JDBC Source
```java
  @Bean
      public UserDetailsManager userDetailsManager(DataSource dataSource) {
    
        return new JdbcUserDetailsManager(dataSource);
      }

```
Custom UserDetails Implementation
```java
@Bean
      public UserDetailsManager userDetailsManager(DataSource dataSource) {
    
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    
        // define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from user_details where user_id=?");
    
        // define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, user_role from user_roles where user_id=?");
    
        return jdbcUserDetailsManager;
      }
```

Example Class with Custom user and roles tables integration to Enable Spring Security

```java

package com.learning.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class AppSecurityConfig {

@Bean
public UserDetailsManager userDetailsManager(DataSource dataSource) {

    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

    // define query to retrieve a user by username
    jdbcUserDetailsManager.setUsersByUsernameQuery(
            "select user_id, pw, active from user_details where user_id=?");

    // define query to retrieve the authorities/roles by username
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
            "select user_id, user_role from user_roles where user_id=?");

    return jdbcUserDetailsManager;
}

//Security Filter chain enforces the user authorities against the API uri matchers
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(configurer ->
                    configurer
                            .requestMatchers("/").hasRole("EMPLOYEE")
                            .requestMatchers("/manager/**").hasRole("MANAGER")
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
            )
            .formLogin(form ->
                    form
                            .loginPage("/custom-login")
                            .loginProcessingUrl("/authenticateTheUser")
                            .defaultSuccessUrl("/v1/employees/list")
                            .permitAll()

            ).logout(logout -> logout.permitAll()
            ).exceptionHandling(configurer ->
                    configurer.accessDeniedPage("/access-denied")
            );

    return http.build();
}
}
```

### Create user_details and user_roles, and employee tables in the configured data base.

By having below properties in application.properties hibernate will load all the schemq.sql DDL's and data.sql data into to database given in the property "spring.datasource.url"

application.properties

#### PostgreSQL
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<databasename>
spring.datasource.username=<UserName>
spring.datasource.password=<Password>

```

####  create and drop table, good for testing, production set to none or comment it
````properties
spring.jpa.hibernate.ddl-auto=none
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
````

#### Create Schema files under /resources/schema.sql

   ```sql
 -- Table structure for table user_details
    CREATE TABLE IF NOT EXISTS user_details
    (
        user_id varchar(50) NOT NULL,
        pw      char(68)    NOT NULL,
        active  int         NOT NULL,
        PRIMARY KEY (user_id)
    );
    
    -- Table structure for table user_roles
    CREATE TABLE IF NOT EXISTS user_roles
    (
        user_id varchar(50) NOT NULL,
        user_role    varchar(50) NOT NULL,
        CONSTRAINT user_roles_idx_1 UNIQUE (user_id, user_role),
        CONSTRAINT user_roles_user_id_fk FOREIGN KEY (user_id) REFERENCES user_details (user_id)
    );
    
    CREATE TABLE IF NOT EXISTS employee
    (
        id         bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
        first_name varchar(45) DEFAULT NULL,
        last_name  varchar(45) DEFAULT NULL,
        email      varchar(45) DEFAULT NULL,
        CONSTRAINT employee_email_unique_idx UNIQUE (email),
        PRIMARY KEY (id)
    );

```
#### Create default data that needs to loaded while starting The application under /resources/data.sql
```sql

--Load pre-required data into user_details
EINSERT INTO user_details
VALUES ('Naveen', '{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je', 1),
('Sriraman', '{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je', 1),
('Vijay', '{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je', 1) ON CONFLICT (user_id) DO NOTHING ;

--Load pre-required data into user_roles
INSERT INTO user_roles
VALUES ('Sriraman', 'ROLE_EMPLOYEE'),
('Vijay', 'ROLE_EMPLOYEE'),
('Vijay', 'ROLE_MANAGER'),
('Naveen', 'ROLE_EMPLOYEE'),
('Naveen', 'ROLE_MANAGER'),
('Naveen', 'ROLE_ADMIN') ON CONFLICT (user_id, user_role) DO NOTHING ;

--Load Default employees data into employee table
INSERT INTO employee
VALUES (1, 'Naveen', 'Kumar D C', 'naveen@gmail.com'),
(2, 'Sriraman', 'Parthasarathi', 'sriramab@gmail.com'),
(3, 'Vijay', 'Perlakota', 'vijay@gmail.com') ON CONFLICT (email) DO NOTHING ;
```

### Create Employee Data Model to perform CRUD
* Create Employee Entity
```java
package com.learning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

@Entity
@Table(name="employee")
public class Employee {

    // define fields
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;


    // define constructors
    public Employee() {

    }

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // define getter/setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // define toString
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

* Create Employee Repository

```java

package com.learning.dao;

import com.learning.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // that's it ... no need to write any code
}

```

*  Create Employee Service
```java
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

```
*  Create Employee Service Implementation
```java
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
```
* User login Screen
```java
package com.learning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping("/custom-login")
    public String loginPage() {

      /*  return "plain-login";
        return "custom-login-v1";*/
        return "login/custom-login-v2";
    }

    // add request mapping for /access-denied

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "login/access-denied";
    }
}

```
Above example from the security filter Chain

````java AppSecurityConfig
 @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(configurer ->
                    configurer
                            .requestMatchers("/").hasRole("EMPLOYEE")
                            .requestMatchers("/manager/**").hasRole("MANAGER")
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
            )
            .formLogin(form ->
                    form
                            .loginPage("/custom-login")
                            .loginProcessingUrl("/authenticateTheUser")
                            .defaultSuccessUrl("/v1/employees/list")
                            .permitAll()

            ).logout(logout -> logout.permitAll()
            ).exceptionHandling(configurer ->
                    configurer.accessDeniedPage("/access-denied")
            );

    return http.build();
  }
````
Spring security maps the    loginPage("/custom-login") by default when http://localhost:8080 is accessed.

* @GetMapping("/custom-login") maps to Rest endpoint
  * This method is invoked by Spring security for authentication and authorization
  
    ``` @Controller
    @GetMapping("/custom-login")
    public String loginPage() {

      /*  return "plain-login";
        return "custom-login-v1";*/
        return "login/custom-login-v2";
    }
    ```
  * login/custom-login-v2.html file is presented to user
  * On entering the user name and password
    * On successful authorization
    * Redirection to /v1/employees/list
    ```java AppSecurityConfig->filterChain Method
              defaultSuccessUrl("/v1/employees/list")
    ```
    * /v1/
  *

*  Create Employee List Form using Spring ThymeLeaf - check HTML files under directory resource/templates

*  Show all existing Employees -> check `/resources/templates/list-employees.html`

*  Option to add new employee(On successful addition of new employee refresh to Show all existing employees page
   * refer check `/resources/templates/list-employees.html`

*  Option to update new employee(On successful addition of new employee refresh to Show all existing employees page
   * refer check `/resources/templates/list-employees.html` 

*  Option to delete new employee(On successful addition of new employee refresh to Show all existing employees page

#### Run locally - Springboot application
Goto ``SpringThymeLeafCrudApplication`` run as SpringBoot application.

After successful application startup

* Go to any browser
  * hit the URL `http://localhost:8080`
    * Navigates to Login page   
      * User name : Naveen Password: test123
  * Default Employee Data screen is presented
    * Add new Employee - click button Add Employee 
    * Update Employee - Click button Update
    * Delete Employee - Click button Delete
    
#### TODO:

* Add Page for Listing Existing users and provide options to perform CRUD operation
  * Provide option for Adding a new user
  * Updating existing users
  * Deleting existing users

* Docker Creation

* Docker Execution

* Docket Debugging

* Enable GraphqlQl Endpoint
