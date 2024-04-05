Title:
    
    Bug Tracker


Description:

A bug tracking application built with:

    Here I used Spring 6x and Spring Boot 3x related technologies.
    
    Spring Boot: Rapid application development framework.
    Hibernate & Spring Data JPA: Object-relational mapping (ORM) for data persistence.
    Spring Security: User authentication and authorization.
    Bootstrap: Responsive front-end framework.
    Thymeleaf: Server-side templating engine.
    JUnit & Mockito: Unit Testing, Integration Testing.
    PostgreSQL: To handle sql related database schema.
    Tomcat Server: To run the application on the server.
    etc: other required technologies.


Features:

    List of functionalities:
        CRUD functionalities for Bug and User entities.
        Create Model classes, reposity layer, service layer, controller layers for Bug and Users.
        Assign bugs to developers.
        Track bug status (Open, In Progress, Fixed, etc.).
        Search and filter bugs.
        Authentication & Authorization.
        User roles and permissions.
        Testing of Service layer, Controller layer and security layer in the same application.
        Secure the application from external common threat, csrf attacks.


How to run:

        Download the application, 
        build them in local machine,
        run the application and as though I use spring boot, it will automcatically start the application on the tomcat server.
        though this application needs required data for User and Bug table, to run the PostgreSQL database schema and execute them on the database server. So installation of PostgreSQL version: 42.7.2 is necessary.
        then, completing all of above tasks, use server port 8081 on the browser, and it will represent you a login page for user authentication. Then do the required traditional steps necessary to login/signin into a web application.
        In case of any error, suitable error message will be represented to the user.
        
