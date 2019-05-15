# Spring Boot : Complete guide from development to deployment

https://www.udemy.com/spring-boot-complete-guide-from-development-to-deployment/
  * [1. Introduction](#1-introduction)
    + [Spring Boot Overview](#spring-boot-overview)
  * [2. Spring Boot - REST API](#2-spring-boot---rest-api)
  * [3. Properties](#3-properties)
    + [Default Properties file](#default-properties-file)
    + [Environment specific properties file](#environment-specific-properties-file)
    + [Using YAML instead of properties](#using-yaml-instead-of-properties)
  * [4. Embedded server Tomcat and properties configuration](#4-embedded-server-tomcat-and-properties-configuration)
  * [5. Logging](#5-logging)
    + [Configure Logback for Logging](#configure-logback-for-logging)
    + [Configure Log4j for Logging](#configure-log4j-for-logging)
  * [6. Spring Data JPA](#6-spring-data-jpa)
    + [What is Spring Data JAP?](#what-is-spring-data-jap-)
    + [Build Entity Objects for Demo Application: JpaRepository](#build-entity-objects-for-demo-application--jparepository)
    + [Making CRUD operations with repository and mapped to service](#making-crud-operations-with-repository-and-mapped-to-service)
    + [Create restEndpoint for CRUD service to test](#create-restendpoint-for-crud-service-to-test)
  * [7. View](#7-view)
    + [Setup web application using Thymeleaf](#setup-web-application-using-thymeleaf)
    + [Configure Bootstrap 4 and create static pages for demo application](#configure-bootstrap-4-and-create-static-pages-for-demo-application)
    + [Mapped the service to de Controller for data passage to View](#mapped-the-service-to-de-controller-for-data-passage-to-view)
    + [Assignment 1: change the static reference of Dashboard Page](#assignment-1--change-the-static-reference-of-dashboard-page)
  * [8. Security](#8-security)
  * [9. Actuator](#9-actuator)
  * [10. Development](#10-development)
    + [Deploy as jar on Unix platform as service.](#deploy-as-jar-on-unix-platform-as-service)

## 1. Introduction
### Spring Boot Overview
* Why Spring Boot.
    * While working with Spring Framework, you might have notice below pain points.
        * Provide all the compatible libraries for the specific Spring version and configure them.
        * Majority of the time we configure the DataSource, EntityManagerFactory, TransactionManager, Spring MVC beans, 
        like ViewResolver, MessageSource etc in the same way most of the times.
    * Spring boot automate that.
* Spring Boot is a framework on the top of spring which ease the bootstrapping and development of new Spring application.
* Provide default code and annotation configuration to quick start.
* It build production grade application ie. available for development asap.
* It follow "Opinionated Defaults Configuration" approach to avoid lot of boilerplate code and configuration to improve
Development.

* Advantage
    * It is very easy to integrate Spring Boot Application with its Spring Ecosystem like Spring JDBC, Spring ORM, 
    Spring Data, Spring Security etc
    * Provides Embedded HTTP servers like Tomcat, Jetty etc to develop and test our web application very easily.
    * Provides lot of plugins to develop and test Spring Boot applications very easily using build tools like Maven and Gradle.
    * Provides lot of plugins to work with embedded and in-memory databases very easily.

* Limitation
    * Converting existing Spring Framework application into Spring Boot is bit time consuming process.
    * Since Spring Boot follow opinionated development approach, so do the things in different way will be difficult and frustrating.

* Spring initializr: https://start.spring.io/

* The sample application build from free version of bootstrap 4 theme named SB-Admin, please follow link for same.  
https://startbootstrap.com/templates/sb-admin/               

## 2. Spring Boot - REST API
* Init **Spring Boot** project and add web dependency. 
```java
package com.adiaz.restendpointget.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class RestEndpoints {

    @GetMapping("/course")
    public Course getEndpoint(
            @RequestParam(defaultValue = "Spring Boot", required = false) String name,
            @RequestParam(defaultValue = "2", required = false) Integer chapterCount) {
        return new Course(name, chapterCount);
    }

    @PostMapping("/register/course")
    public String saveCourse(@RequestBody Course course) {
        return String.format("Your course named %s with %s chapters saved successfully", course.getName(), course.getChapterCount());
    }
}
```              
## 3. Properties
### Default Properties file
* **application.properties** file it is autodetected, its placed in "src/main/resources".
* Spring Boot specified various common default properties inside application.properties to support Logging, AOP, Identify, Hibernate, JPA, JMX, email, etc.
* we don't need to specify all the default properties in all the cases. We can specify them only **on-demand**.
* This is how spring reduce XML based configurations and changing them to simple properties.
* Declare the properties
````properties
server.port=9090
default.course.name=SAP HANA
default.course.chapterCount=66
````
* Use the properties.
```java
@Value("${default.course.name}")
private String nameDefault;

@Value("${default.course.chapterCount}")
private Integer chapterCountDefault;
```
### Environment specific properties file
* There's is a build-in mechanism in Spring Boot for targeting different environments with respect to properties.
* We can simply define the "application-<env_name>.properties" and reference it while running the Spring Boot app.
* This environment properties file will take precedence over the default property file where there is collision.
* Create **application-qa.properties**:
````properties
server.port=9091
default.course.name=SAP HANA & QA
````
* Run application with the new profile:
> mvn spring-boot:run -Dspring-boot.run.profiles=dev

* Hierarchical Properties
    * We can use "@ConfigurationProperties" annotation where properties are grouped together.
    * We can make these properties as Type-safe configuration properties.
    * We can inject these properties anywhere and avoid cumbersome process of injecting through @Value annotation.
    * "@ConfigurationProperties" supports both .properties and .yml files.
* Properties:
```properties
course.name=SAP HANA
course.chapterCount=55
course.author=Antonio Diaz Arroyo
course.rating=9
```
* Declare config:
```java
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("course")
@Data
public class CourseConfiguration {
    private String name;
    private Integer chapterCount;
    private Integer rating;
    private String author;
}
```
* Wire config and use in controller:
```java
@RestController
public class RestEndpointsController {

    @Autowired
    private CourseConfiguration courseConfiguration;

    @GetMapping("/config")
    public Map<String, Object> config(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", courseConfiguration.getName());
        map.put("chapterCount", courseConfiguration.getChapterCount());
        map.put("author", courseConfiguration.getAuthor());
        map.put("rating", courseConfiguration.getRating());
        return map;
    }
}
```
### Using YAML instead of properties
* The Spring Application class automatically supports YAML as alternative to properties whenever you have **SnakeYAML** 
library in your classpath.
* YAML is particularly good for hierarchical property storage.
* Spring Framework provides two convenient classes that can be used to load YAML documents.
    * **YamlPropertiesFactoryBean** loads YAML as Properties.
    * **YamlMapFactoryBean** loads YAML as a Map.
* YAML files cannot be loaded by using the @PropertySource annotation. So, in the case that you need to load values that way, 
you need to use a properties file. 
* Replace application.properties by **application.yml**: 
```yaml
spring:
  profiles:
    active: qa

---
spring:
  profiles: qa

server:
  port: 9191

default:
  course:
    name: "The Art of Software Testing"
    chapterCount: 2

course:
  name: "Expert One-on-One J2EE Design and Development"
  chapterCount: 55
  author: Rob Johnson
  rating: 9

---
spring:
  profiles: dev

server:
  port: 9090

default:
  course:
    name: "Design Patterns: Elements of Reusable Object-Oriented Software"
    chapterCount: 2

course:
  name: "Expert One-on-One J2EE Design and Development"
  chapterCount: 55
  author: Rob Johnson
  rating: 9

```
## 4. Embedded server Tomcat and properties configuration
* Advantage:
    * Easy to user
    * Microservice architecture compatible
    * Standalone application work as unit
    * Quicly testable
   
* Generate war file
    * In Spring Initializr create project, add Web dependency.
    * Import project to intellij
    * Add dependency
    ```xml
        <!-- -->
        <packaging>war</packaging>
        <!-- add dependency -->
        <dependencies>	
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-tomcat</artifactId>
                <scope>provided</scope>
            </dependency>
        </dependencies>      
    ```              
    * Spring: extends **SpringBootServletInitializer** and override **configure**
    ```java
    @SpringBootApplication
    public class EndpointYamlApplication extends SpringBootServletInitializer {
    
        public static void main(String[] args) {
            SpringApplication.run(EndpointYamlApplication.class, args);
        }
    
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return super.configure(builder);
        }
    }     
    ```      
    * Maven
    > mvn clean package
    

## 5. Logging
### Configure Logback for Logging
* Option 1: Add to application.properties
```properties
logging.level.org.springframework.web = DEBUG
#com.adiaz is my package 
logging.level.com.adiaz = INFO
logging.file = Logbacklogging.log
# Logging pattern for the console
logging.pattern.console = %d %-5level %logger : %msg%n
# Logging pattern for file
logging.pattern.file = %d %-5level [%thread] %logger : %msg%n
```
* Option 2: create **logback.xml** in resources folder.
 
### Configure Log4j for Logging
* Add Log4j dependency to pom.xml:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>
</dependencies>
```
* Add log4j2.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="INFO">
    <Appenders>
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout
                pattern="[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n" />
        </Console>
        <File name="file" fileName="logs/Log4jLogging.log">
            <PatternLayout
                pattern="[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n" />        
        </File>
    </Appenders>
    <Loggers>
        <Root level="debug" additivity="false">
            <AppenderRef ref="console" />
        </Root>
    </Loggers>
</Configuration>
```
## 6. Spring Data JPA
### What is Spring Data JAP?
![JPA](images/jpa_01.jpg)
* Java Persistence API is Java specification for accessing, persisting and managing data between java objects 
and database.
* ORM let you map your entity classes into relation mapping, so once you connect to database, ORM framework will do the 
query, transaction and mapping.
* JPA is specification to use ORM.
* **Spring Data JPA** is a separate project within the Spring Data ecosystem and lets you connect whit different persistence 
kind of store, both SQL and noSQL with much more easier approach. 
![JPA](images/jpa_02.jpg)
* Spring Data JPA-Features
    * Sophisticated support to build repositories based on Spring and JPA.
    * Support for **Querydsl** predicates thus type-safe JPA queries.
    * Transparent auditing of domain class.
    * Pagination support, dynamic query execution, ability to integrate custom data access code.
    * Validation of **@Query** annotated queries at bootstrap time.
    * Support for XML based entity mapping.
    * JavaConfig based repository configuration by introducing **@EnableJpaRespositories**.

### Build Entity Objects for Demo Application: JpaRepository
* Initialize Spring Boot project with web and JPA.
* Add **Hibernate** and **Mysql** dependencies or **M2** and **devtools**:
```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>        
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>    
```
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
* application.properties (not necessary for m2):
```properties
spring.datastore.url=jdbc:mysql://localhost:3306/ecomDashApp
spring.datastore.username=root
spring.datastore.password=password
spring.datastore.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```
* Create super class entity for primary key:
```java
package com.adiaz.ecomdashboard.entities;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class KeyEntity {

    @Id
    @Column(name="ID", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer id;
}

```
* Create entity class:
    * Schema doesn't work on m2.
```java
package com.adiaz.ecomdashboard.entities;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="COMPANY_REVENUE")
@Data
public class CompanyRevenue extends KeyEntity {
    @Column(name="REVENUE_MONTH", nullable=false)
    private String month;
    @Column(name="REVENUE", nullable=false)
    private double revenue;
    @Column(name="EXPENSE")
    private double expense;
    @Column(name="MARGINS")
    private double margins;
}
```

* Add **EntityScan** annotation to main Spring  boot.
```java
package com.adiaz;
import ...
@SpringBootApplication
@EntityScan(basePackages = "com.adiaz.entities")
public EcomdashboardApplication {
    public static void main(String[] args) {SpringApplication.run(EcomdashboardApplication.class)}
}
```

* Create Repository:
```java
package com.adiaz.ecomdashboard.respositories;

import com.adiaz.ecomdashboard.entities.CompanyRevenue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "companyRevenueRepository")
public interface CompanyRevenueRepository extends JpaRepository <CompanyRevenue, Long> {}
```

* Enable JPA repositories, add annotation **@EnableJpaRspositories** to main:
```java
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.adiaz.ecomdashboard.respositories")
public class EcomdashboardApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcomdashboardApplication.class, args);
	}
}

```
### Making CRUD operations with repository and mapped to service
* Create service interface.
```java
package com.adiaz.ecomdashboard.services;

import com.adiaz.ecomdashboard.entities.CompanyRevenue;
import java.util.List;

public interface DashboardService {
    List<CompanyRevenue> getTodayRevenueDash();
}
```
* Create service implementation.
```java
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CompanyRevenueRepository companyRevenueRepository;

    @Autowired
    public List<CompanyRevenue> getTodayRevenueDash() {
        return companyRevenueRepository.findAll();
    }
}

```
* Repository: write custom method
 ```java
 @Repository
 public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
     List<ProductCategory> findByBestCategory(boolean bestCategory);
 }
 ```

### Create restEndpoint for CRUD service to test
* Add methods to service interface and implementation.
* **Service interface**
```java
public interface DashboardService {
    void addEmployInformation(EmployInformation employInformation);
    EmployInformation updateEmployInformation(EmployInformation employInformation);
    void deleteEmployInformationById(Long id);
    EmployInformation findEmployInformationById(Long id);
}

```
* **Service implementation**
```java
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private EmployInformationRepository employInformationRepository;

    public List<EmployInformation> getEmployees() {
        return employInformationRepository.findAll();
    }

    @Override
    public void addEmployInformation(EmployInformation employInformation) {
        employInformationRepository.save(employInformation);
    }

    @Override
    public EmployInformation updateEmployInformation(EmployInformation employInformation) {
        return employInformationRepository.save(employInformation);
    }

    @Override
    public void deleteEmployInformationById(Long id) {
        employInformationRepository.deleteById(id);
    }
    @Override
    public EmployInformation findEmployInformationById(Long id) {
        return employInformationRepository.findById(id).orElse(null);
    }
}
```
## 7. View
### Setup web application using Thymeleaf
* Add Dependencies to pom
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>    
    </dependency>
    <dependency>
        <groupId>org.thymeleaf.extras</groupId>
        <artifactId>thymeleaf-extras-springsecurity4</artifactId>
    </dependency>    
</dependencies>    
```
* Add properties
```properties
spring.thymeleaf.cache=false
spring.thymeleaf.enabled=true
spring.thymeleaf.check-template=true
spring.thymeleaf.check-template-location=true
spring.thymeleaf.content-type=text/html
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.mode=HTML
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.sufix=.html
```
* Add index.html to **/resources/templates** folder
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>e-commerce dashboard demo app</title>
</head>
<body>
    <h1>demo app</h1>
</body>
</html>
```
### Configure Bootstrap 4 and create static pages for demo application
* Download static content from here: https://startbootstrap.com/templates/sb-admin/.
* Copy folders (css, js, scss, vendor) to src/main/resources/static/.
* Copy index.html and place it on **index.html**. 

### Mapped the service to de Controller for data passage to View
* Add new method to service to retrieve a map:
```java
@Override
public Map<String, Object> getTodayRevenueDashMap() {
    Map<String, Object> companyRevenueMap = new HashMap<>();
    List<CompanyRevenue> all = companyRevenueRepository.findAll();
    List<String> label = new ArrayList<>();
    List<String> revenue = new ArrayList<>();
    all.forEach(companyRevenue -> {
        label.add(companyRevenue.getMonth());
        revenue.add(Double.toString(companyRevenue.getRevenue()));
    });
    companyRevenueMap.put("crLabels", label.toString());
    companyRevenueMap.put("crRevenue", revenue.toString());
    companyRevenueMap.put("totalExpense", "totalExpense value");
    companyRevenueMap.put("totalMargin", "totalMargin value");
    companyRevenueMap.put("totalRevenue", "totalRevenue value");
    return companyRevenueMap;
}
```
* Create Controller and use the model to send information to the view.
```java
@GetMapping({"", "/home"})
public String gotToIndex(Model model) {
    model.addAttribute("cr", dashboardService.getTodayRevenueDashMap());
    return "index";
}
```

* Use the attributes from the model in the view:
```html
<input id="labels" type="hidden" th:value="${cr.crLabels}">
<input id="revenues" type="hidden" th:value="${cr.crRevenue}">
```
### Assignment 1: change the static reference of Dashboard Page
## 8. Security
* Add security dependence to **pom.xml**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity4</artifactId>
</dependency>
```
* Create login form.
    * Add Thymeleaf Spring Security namespace
    * Create inputs for username and password 
```html
<!DOCTYPE html>
<html lang="en"
        xmlns:th="http://www.thymeleaf.org"
        xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<body class="bg-dark">
    <div class="container">
        <div class="card card-login mx-auto mt-5">
            <div class="card-header">Login</div>
            <div class="card-body">
                <form th:action="@{/login}" method="post">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control" id="username" name="username" aria-describedby="emailHelp" placeholder="Enter username" autocomplete="off">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                    </div>
                    <div th:if="${param.error}" class="alert alert-danger">
                        Invalid username or password.
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
            <span sec:autorize="isAutenticated()" style=""></span>
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
```
* Create **SecurityConfig** class: 
    * Add annotation **@Configuration** and **EnableWebSecurity**
```java
package com.adiaz.ecomdashboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginAccessDeniedHandler loginAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/**")
                .hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(loginSuccessHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(loginAccessDeniedHandler)
                .and().csrf().disable();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}1234").roles("USER")
                .and()
                .withUser("admin").password("{noop}1234").roles(("ADMIN"));
    }
}
```
* Create login success handler:
```java
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException {
        System.out.println("Login Successful");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect("/dashboard");
    }
}
```
* In the html you want to protect add:
```html
<span sec:autorize="isAutenticated()" style=""></span>
```
## 9. Actuator
![JPA](images/actuator_02.jpg)
* Actuator brings production-ready features to our application without having to actually implement these features ourselves.
* Actuator exposes devops information about the running application - health, metrics, info, logs, env, jvm, etc.
* It uses HTTP endpoints or JMX beans to enable us to interact with it.

![JPA](images/actuator_01.jpg)

* Import dependencies:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
* Expose all endpoints, it's necessary to add security.
```properties
management.endpoints.web.exposure.include=*
``` 
## 10. Development
### Deploy as jar on Unix platform as service.

