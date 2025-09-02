# amaya-swagger [![amaya-swagger](https://img.shields.io/maven-central/v/io.github.amayaframework/amaya-swagger?color=blue)](https://repo1.maven.org/maven2/io/github/amayaframework/amaya-swagger)

The Amaya Framework module that integrates Swagger/OpenAPI document serving and UI support.

## Getting Started

To install it, you will need:

* Java 11+
* Maven/Gradle
* [amaya-core](https://github.com/AmayaFramework/amaya-core) **3.4.0+**

### Features

* Serving OpenAPI documents (JSON / YAML) with configurable roots
* Automatic content negotiation and compression support
* Integration with any `openui`-compatible UI module (e.g. Swagger UI or custom)
* Configurable root path for UI and exposed documents
* Simple fluent API for setup

## Installing

### Gradle dependency

```groovy
dependencies {
    implementation group: 'io.github.amayaframework', name: 'amaya-core', version: '3.4.0'
    implementation group: 'io.github.amayaframework', name: 'amaya-swagger', version: '2.0.1'
    // choose any OpenUI implementation:
    implementation group: 'io.github.amayaframework', name: 'swagger-ui-bundle', version: '2.0.0'
}
```

### Maven dependency

```xml
<dependencies>
    <dependency>
        <groupId>io.github.amayaframework</groupId>
        <artifactId>amaya-core</artifactId>
        <version>3.4.0</version>
    </dependency>
    <dependency>
        <groupId>io.github.amayaframework</groupId>
        <artifactId>amaya-swagger</artifactId>
        <version>2.0.1</version>
    </dependency>
    <!-- choose any OpenUI implementation -->
    <dependency>
        <groupId>io.github.amayaframework</groupId>
        <artifactId>open-ui-bundle</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>
```

## Example

```java
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.swaggerui.SwaggerUiFactory;

public class Main {
    public static void main(String[] args) throws Throwable {
        var app = WebBuilders.create()
                .withServerFactory(/*your server factory*/)
                .configureApplication(Swagger.configurer(cfg -> cfg
                        .uiFactory(new SwaggerUiFactory())
                        .root("/swagger")
                        .addDocument(Sources.of("https://petstore.swagger.io/v2/swagger.json", "Petstore")))
                )
                .build();
        app.bind(8080);
        app.run();
    }
}
```

Run and open http://localhost:8080/swagger to see Swagger UI with the Petstore API.

## Built With

* [Gradle](https://gradle.org) – build & dependency management
* [amaya-core](https://github.com/AmayaFramework/amaya-core) – base Amaya modules
* [openui](openapi-ui-bundle) – pluggable UI layer

## Authors

* **[RomanQed](https://github.com/RomanQed)** - *Main work*

See also the list of [contributors](https://github.com/AmayaFramework/amaya-swagger/contributors)
who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details
