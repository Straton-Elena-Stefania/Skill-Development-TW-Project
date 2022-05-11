/*
 * API pentru utilizatori SkiVI
 * Prin acest API care e RESTful, un administrator sau un utilizator logat poate obtine si gestiona datele personale si ale cursurilor disponibile.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiCallback;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.Configuration;
import org.openapitools.client.Pair;
import org.openapitools.client.ProgressRequestBody;
import org.openapitools.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.openapitools.client.model.InlineResponse200;
import org.openapitools.client.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultApi {
    private ApiClient localVarApiClient;

    public DefaultApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DefaultApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for usersIdGet
     * @param id Id utilizator. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Am returnat cu succes acel utilizator </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Daca nu exista un client cu acest id </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call usersIdGetCall(Integer id, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/users/{id}"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();
        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "cookieAuth" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call usersIdGetValidateBeforeCall(Integer id, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling usersIdGet(Async)");
        }
        

        okhttp3.Call localVarCall = usersIdGetCall(id, _callback);
        return localVarCall;

    }

    /**
     * 
     * Intoarce numele si descrierea utilizatorului de la un id
     * @param id Id utilizator. (required)
     * @return User
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Am returnat cu succes acel utilizator </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Daca nu exista un client cu acest id </td><td>  -  </td></tr>
     </table>
     */
    public User usersIdGet(Integer id) throws ApiException {
        ApiResponse<User> localVarResp = usersIdGetWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * 
     * Intoarce numele si descrierea utilizatorului de la un id
     * @param id Id utilizator. (required)
     * @return ApiResponse&lt;User&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Am returnat cu succes acel utilizator </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Daca nu exista un client cu acest id </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<User> usersIdGetWithHttpInfo(Integer id) throws ApiException {
        okhttp3.Call localVarCall = usersIdGetValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<User>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * Intoarce numele si descrierea utilizatorului de la un id
     * @param id Id utilizator. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Am returnat cu succes acel utilizator </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Daca nu exista un client cu acest id </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call usersIdGetAsync(Integer id, final ApiCallback<User> _callback) throws ApiException {

        okhttp3.Call localVarCall = usersIdGetValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<User>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for usersIdSkillsGet
     * @param id Id utilizator. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Am returnat cu succes datele despre prezentarea acelor cursuri </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Daca nu exista un client cu acest id </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call usersIdSkillsGetCall(Integer id, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/users/{id}/skills"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();
        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "cookieAuth" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call usersIdSkillsGetValidateBeforeCall(Integer id, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling usersIdSkillsGet(Async)");
        }
        

        okhttp3.Call localVarCall = usersIdSkillsGetCall(id, _callback);
        return localVarCall;

    }

    /**
     * 
     * Intoarce datele despre cursurile la care utilizatorul descris de id s-a inscris
     * @param id Id utilizator. (required)
     * @return List&lt;InlineResponse200&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Am returnat cu succes datele despre prezentarea acelor cursuri </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Daca nu exista un client cu acest id </td><td>  -  </td></tr>
     </table>
     */
    public List<InlineResponse200> usersIdSkillsGet(Integer id) throws ApiException {
        ApiResponse<List<InlineResponse200>> localVarResp = usersIdSkillsGetWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * 
     * Intoarce datele despre cursurile la care utilizatorul descris de id s-a inscris
     * @param id Id utilizator. (required)
     * @return ApiResponse&lt;List&lt;InlineResponse200&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Am returnat cu succes datele despre prezentarea acelor cursuri </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Daca nu exista un client cu acest id </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<InlineResponse200>> usersIdSkillsGetWithHttpInfo(Integer id) throws ApiException {
        okhttp3.Call localVarCall = usersIdSkillsGetValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<List<InlineResponse200>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * Intoarce datele despre cursurile la care utilizatorul descris de id s-a inscris
     * @param id Id utilizator. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Am returnat cu succes datele despre prezentarea acelor cursuri </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Daca clientul ce a depus cererea nu are dreptul de administrator, sau nu este insusi utilizatorul cerut, datele nu sunt disponibile </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Daca nu exista un client cu acest id </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call usersIdSkillsGetAsync(Integer id, final ApiCallback<List<InlineResponse200>> _callback) throws ApiException {

        okhttp3.Call localVarCall = usersIdSkillsGetValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<List<InlineResponse200>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
