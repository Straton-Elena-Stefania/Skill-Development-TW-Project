# UserApi

All URIs are relative to *http://localhost:8088/skivi_war_exploded*

Method | HTTP request | Description
------------- | ------------- | -------------
[**usersIdDelete**](UserApi.md#usersIdDelete) | **DELETE** /users/{id} | 


<a name="usersIdDelete"></a>
# **usersIdDelete**
> usersIdDelete(id)



Sterge user-ul aflat la acel id.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8088/skivi_war_exploded");
    
    // Configure API key authorization: cookieAuth
    ApiKeyAuth cookieAuth = (ApiKeyAuth) defaultClient.getAuthentication("cookieAuth");
    cookieAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //cookieAuth.setApiKeyPrefix("Token");

    UserApi apiInstance = new UserApi(defaultClient);
    Integer id = 56; // Integer | Id utilizator.
    try {
      apiInstance.usersIdDelete(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#usersIdDelete");
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

null (empty response body)

### Authorization

[cookieAuth](../README.md#cookieAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Utilizatorul a fost sters cu succes. |  -  |

