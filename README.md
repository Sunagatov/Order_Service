# Order Service

A Java order-management microservice split into a reusable API module and a Spring application module.

<p>
  <a href="https://buymeacoffee.com/lucky_1uck">
    <img src="https://img.shields.io/badge/Support_the_project-FFDD00?style=for-the-badge&amp;logo=buymeacoffee&amp;logoColor=black" alt="Support the project" width="260" height="40">
  </a>
</p>

## Modules

| Module | Responsibility |
| --- | --- |
| [order_service_api](order_service_api) | Shared API contracts and client integration |
| [order_service_impl](order_service_impl) | REST controllers, persistence and service implementation |

The implementation exposes order operations under `/orders` and category reads
under `/categories`. Its stack includes Spring Boot, Spring Data JPA,
PostgreSQL, Liquibase, OpenFeign, Eureka and Springfox.

## Build prerequisites

- JDK 8 and Maven.
- PostgreSQL with a local `order` database.
- The sibling Maven artifacts referenced in the module POMs, including
  `OrderManagementSystemUtil` and `client_service_impl`, installed locally or
  available from your Maven repository.
- The service-discovery/client configuration expected by the application.

Those sibling artifacts are not included here, so a standalone clone may stop
at dependency resolution. The exact coordinates are in
[the implementation POM](order_service_impl/pom.xml).

## Build

From the repository root, once dependencies are available:

```bash
mvn clean install
```

Import the Maven reactor into your IDE and run the implementation module's
Spring application entry point. Its configured HTTP port is `8098`.
Set local datasource values with Spring configuration overrides rather than
reusing another environment's database settings.

## Where to look next

- [Implementation source](order_service_impl/src/main/java): controllers, services and entities.
- [Resources](order_service_impl/src/main/resources): application settings and Liquibase changelogs.
- [API source](order_service_api/src/main/java): reusable contracts.

## ☕ Support the project

If this project helps you learn or build, you can support its development.

<p>
  <a href="https://buymeacoffee.com/lucky_1uck">
    <img src="https://img.shields.io/badge/Support_the_project-FFDD00?style=for-the-badge&amp;logo=buymeacoffee&amp;logoColor=black" alt="Support the project" width="260" height="40">
  </a>
</p>
