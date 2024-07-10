DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

--
-- Table structure for table users
--

CREATE TABLE users (
                         username varchar(50) NOT NULL,
    password varchar(68) NOT NULL,
    enabled int NOT NULL,
    PRIMARY KEY (username)
    );

--
-- Inserting data for table users
--

INSERT INTO users
VALUES
    ('john','{noop}test123',1),
    ('mary','{noop}test123',1),
    ('susan','{noop}test123',1),
    ('john1','{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je',1),
    ('mary1','{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je',1),
    ('susan1','{bcrypt}$2a$10$JCsP1PBI7m6JTKdwjUzDwea5NFxsysq8VFcpb.hRsG74ltHku/0je',1);


--
-- Table structure for table authorities
--

CREATE TABLE authorities (
                               username varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,
    CONSTRAINT authorities_idx_1 UNIQUE (username,authority),
    CONSTRAINT authorities_ibfk_1 FOREIGN KEY (username) REFERENCES users (username)
    );

--
-- Inserting data for table authorities
--

INSERT INTO authorities
VALUES
    ('john','ROLE_EMPLOYEE'),
    ('mary','ROLE_EMPLOYEE'),
    ('mary','ROLE_MANAGER'),
    ('susan','ROLE_EMPLOYEE'),
    ('susan','ROLE_MANAGER'),
    ('susan','ROLE_ADMIN'),
    ('john1','ROLE_EMPLOYEE'),
    ('mary1','ROLE_EMPLOYEE'),
    ('susan1','ROLE_ADMIN');


