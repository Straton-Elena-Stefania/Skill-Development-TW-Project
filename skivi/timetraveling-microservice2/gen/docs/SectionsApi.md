# SectionsApi

All URIs are relative to *http://localhost:8081/timetraveling_microservice2_war_exploded*

Method | HTTP request | Description
------------- | ------------- | -------------
[**sectionsGet**](SectionsApi.md#sectionsGet) | **GET** /sections | 
[**sectionsPost**](SectionsApi.md#sectionsPost) | **POST** /sections | 


<a name="sectionsGet"></a>
# **sectionsGet**
> List&lt;Section&gt; sectionsGet()



Intoarce lista cu toate sectiunile (stratul de sus al cursurilor).

### Example
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

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Section&gt;**](Section.md)

### Authorization

[cookieAuth](../README.md#cookieAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Am returnat cu succes toate sectiunile disponibile. |  -  |
**403** | Daca clientul ce a depus cererea nu are dreptul de administrator sau nu este inregistrat la acest skill, lista tuturor sectiunilor nu este disponibila. |  -  |

<a name="sectionsPost"></a>
# **sectionsPost**
> sectionsPost(section)



### Example
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
    Section section = new Section(); // Section | 
    try {
      apiInstance.sectionsPost(section);
    } catch (ApiException e) {
      System.err.println("Exception when calling SectionsApi#sectionsPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **section** | [**Section**](Section.md)|  |

### Return type

null (empty response body)

### Authorization

[cookieAuth](../README.md#cookieAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Cererea de creare sectiune a fost efectuata cu succes |  -  |

