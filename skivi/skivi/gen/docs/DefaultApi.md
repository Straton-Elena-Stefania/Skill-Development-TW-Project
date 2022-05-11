# DefaultApi

All URIs are relative to *http://localhost:8088/skivi_war_exploded*

Method | HTTP request | Description
------------- | ------------- | -------------
[**usersIdGet**](DefaultApi.md#usersIdGet) | **GET** /users/{id} | 
[**usersIdSkillsGet**](DefaultApi.md#usersIdSkillsGet) | **GET** /users/{id}/skills | 


<a name="usersIdGet"></a>
# **usersIdGet**
> User usersIdGet(id)



Intoarce numele si descrierea utilizatorului de la un id

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8088/skivi_war_exploded");
    
    // Configure API key authorization: cookieAuth
    ApiKeyAuth cookieAuth = (ApiKeyAuth) defaultClient.getAuthentication("cookieAuth");
    cookieAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //cookieAuth.setApiKeyPrefix("Token");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | Id utilizator.
    try {
      User result = apiInstance.usersIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#usersIdGet");
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
 **id** | **Integer**| Id utilizator. |

### Return type

[**User**](User.md)

### Authorization

[cookieAuth](../README.md#cookieAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Am returnat cu succes acel utilizator |  -  |
**403** | Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile |  -  |
**404** | Daca nu exista un client cu acest id |  -  |

<a name="usersIdSkillsGet"></a>
# **usersIdSkillsGet**
> List&lt;InlineResponse200&gt; usersIdSkillsGet(id)



Intoarce datele despre cursurile la care utilizatorul descris de id s-a inscris

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8088/skivi_war_exploded");
    
    // Configure API key authorization: cookieAuth
    ApiKeyAuth cookieAuth = (ApiKeyAuth) defaultClient.getAuthentication("cookieAuth");
    cookieAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //cookieAuth.setApiKeyPrefix("Token");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | Id utilizator.
    try {
      List<InlineResponse200> result = apiInstance.usersIdSkillsGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#usersIdSkillsGet");
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
 **id** | **Integer**| Id utilizator. |

### Return type

[**List&lt;InlineResponse200&gt;**](InlineResponse200.md)

### Authorization

[cookieAuth](../README.md#cookieAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Am returnat cu succes datele despre prezentarea acelor cursuri |  -  |
**403** | Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile |  -  |
**404** | Daca nu exista un client cu acest id |  -  |

