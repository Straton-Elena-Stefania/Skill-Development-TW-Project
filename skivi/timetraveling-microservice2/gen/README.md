# openapi-java-client

API pentru utilizatori Timetraveling microservice
- API version: 1.0.0
  - Build date: 2021-06-01T20:25:52.562025800+03:00[Europe/Athens]

Prin acest API care e RESTful, un administrator poate gestiona datele despre cursurile ce tin de calatoritul in timp si acest API este disponibil pentru aplicatia SkiVI, pentru a procura articolele si ale afisa utilizatorilor, dar si pentru gestiunea lor.


*Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech)*


## Requirements

Building the API client library requires:
1. Java 1.7+
2. Maven/Gradle

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-java-client</artifactId>
  <version>1.0.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "org.openapitools:openapi-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/openapi-java-client-1.0.0.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SectionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081/timetraveling_microservice2_war_exploded");
    
    // Configure API key authorization: cookieAuth
    ApiKeyAuth cookieAuth = (ApiKeyAuth) defaultClient.getAuthentication("cookieAuth");
    cookieAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //cookieAuth.setApiKeyPrefix("Token");

    SectionsApi apiInstance = new SectionsApi(defaultClient);
    try {
      List<Section> result = apiInstance.sectionsGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SectionsApi#sectionsGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://localhost:8081/timetraveling_microservice2_war_exploded*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*SectionsApi* | [**sectionsGet**](docs/SectionsApi.md#sectionsGet) | **GET** /sections | 
*SectionsApi* | [**sectionsPost**](docs/SectionsApi.md#sectionsPost) | **POST** /sections | 


## Documentation for Models

 - [Section](docs/Section.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### cookieAuth

- **Type**: API key
- **API key parameter name**: Authorization
- **Location**: HTTP header


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author


