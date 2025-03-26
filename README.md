# FlowMart

A robust platform for managing and automating e-commerce processes and workflows

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.10/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.10/maven-plugin/build-image.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.3.10/reference/using/devtools.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.3.10/specification/configuration-metadata/annotation-processor.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.3.10/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.3.10/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.3.10/reference/messaging/kafka.html)
* [Validation](https://docs.spring.io/spring-boot/3.3.10/reference/io/validation.html)
* [Quartz Scheduler](https://docs.spring.io/spring-boot/3.3.10/reference/io/quartz.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.3.10/reference/actuator/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Development

A guide to building resilient, distributed apps with Spring Boot and Temporal’s orchestration.

[Spring Boot with Temporal: Building Resillient Workflow Applications](https://vrnsky.medium.com/spring-boot-with-temporal-building-resillient-workflow-applications-bda17ac08662)

```shell
$ curl -X POST http://localhost:8080/orders/1/process
```