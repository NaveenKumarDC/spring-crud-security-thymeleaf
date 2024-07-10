--Load pre-required data into user_details
INSERT INTO user_details
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


