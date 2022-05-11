# UsersApi

All URIs are relative to *http://localhost:8088/skivi_war_exploded*

Method | HTTP request | Description
------------- | ------------- | -------------
[**usersGet**](UsersApi.md#usersGet) | **GET** /users | 
[**usersIdPatch**](UsersApi.md#usersIdPatch) | **PATCH** /users/{id} | 
[**usersIdPut**](UsersApi.md#usersIdPut) | **PUT** /users/{id} | 
[**usersPost**](UsersApi.md#usersPost) | **POST** /users | 


<a name="usersGet"></a>
# **usersGet**
> List&lt;User&gt; usersGet()



Intoarce lista cu numele si descrierea tuturor utilizatorilor

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8088/skivi_war_exploded");
    
    // Configure API key authorization: cookieAuth
    ApiKeyAuth cookieAuth = (ApiKeyAuth) defaultClient.getAuthentication("cookieAuth");
    cookieAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //cookieAuth.setApiKeyPrefix("Token");

    UsersApi apiInstance = new UsersApi(defaultClient);
    try {
      List<User> result = apiInstance.usersGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#usersGet");
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

[**List&lt;User&gt;**](User.md)

### Authorization

[cookieAuth](../README.md#cookieAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Am returnat cu succes toti utilizatorii din aplicatie |  -  |
**403** | Daca clientul ce a depus cererea nu are dreptul de administrator, lista utilizatorilor nu este disponibila |  -  |

<a name="usersIdPatch"></a>
# **usersIdPatch**
> usersIdPatch(id, user)



Actualizeaza user-ul aflat la acel id partial conform a ceea ce este descris in corpul cererii in format JSON.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8088/skivi_war_exploded");
    
    // Configure API key authorization: cookieAuth
    ApiKeyAuth cookieAuth = (ApiKeyAuth) defaultClient.getAuthentication("cookieAuth");
    cookieAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //cookieAuth.setApiKeyPrefix("Token");

    UsersApi apiInstance = new UsersApi(defaultClient);
    Integer id = 56; // Integer | Id utilizator.
    User user = new User(); // User | 
    try {
      apiInstance.usersIdPatch(id, user);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#usersIdPatch");
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
 **user** | [**User**](User.md)|  |

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
**200** | A fost actualizat partial userul cu succes. |  -  |

<a name="usersIdPut"></a>
# **usersIdPut**
> usersIdPut(id, user)



O suprascriere totala a utilizatorului aflat la acel id folosindu-se de ceea ce este in corpul cererii (un User in JSON)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8088/skivi_war_exploded");
    
    // Configure API key authorization: cookieAuth
    ApiKeyAuth cookieAuth = (ApiKeyAuth) defaultClient.getAuthentication("cookieAuth");
    cookieAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //cookieAuth.setApiKeyPrefix("Token");

    UsersApi apiInstance = new UsersApi(defaultClient);
    Integer id = 56; // Integer | Id utilizator.
    User user = new User(); // User | 
    try {
      apiInstance.usersIdPut(id, user);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#usersIdPut");
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
 **user** | [**User**](User.md)|  |

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
**200** | A fost suprascris utilizatorul cu succes. |  -  |

<a name="usersPost"></a>
# **usersPost**
> usersPost(user)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8088/skivi_war_exploded");
    
    // Configure API key authorization: cookieAuth
    ApiKeyAuth cookieAuth = (ApiKeyAuth) defaultClient.getAuthentication("cookieAuth");
    cookieAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //cookieAuth.setApiKeyPrefix("Token");

    UsersApi apiInstance = new UsersApi(defaultClient);
    User user = new User(); // User | 
    try {
      apiInstance.usersPost(user);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#usersPost");
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
 **user** | [**User**](User.md)|  |

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
**201** | Cererea de creare utilizator a fost efectuata cu succes |  -  |

