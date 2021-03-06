/*
 * API pentru utilizatori Timetraveling microservice
 * Prin acest API care e RESTful, un administrator poate gestiona datele despre cursurile ce tin de calatoritul in timp si acest API este disponibil pentru aplicatia SkiVI, pentru a procura articolele si ale afisa utilizatorilor, dar si pentru gestiunea lor.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.Section;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for SectionsApi
 */
@Ignore
public class SectionsApiTest {

    private final SectionsApi api = new SectionsApi();

    
    /**
     * 
     *
     * Intoarce lista cu toate sectiunile (stratul de sus al cursurilor).
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void sectionsGetTest() throws ApiException {
        List<Section> response = api.sectionsGet();

        // TODO: test validations
    }
    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void sectionsPostTest() throws ApiException {
        Section section = null;
        api.sectionsPost(section);

        // TODO: test validations
    }
    
}
