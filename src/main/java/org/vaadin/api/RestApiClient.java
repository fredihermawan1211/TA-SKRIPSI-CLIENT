package org.vaadin.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.vaadin.RequestDto.KomunitasRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class RestApiClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RestApiClient() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public String sendGetRequest(String url, String bearerToken) throws IOException {
        HttpGet request = new HttpGet(url);

        // Set header content type to JSON
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        // Set bearer token
        if (bearerToken != null) {
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);
        }
        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();

        if (responseEntity != null) {
            return EntityUtils.toString(responseEntity);
        }

        return null;
    }

    public String sendPostRequest(String url, RequestDtoPOJO requestDtoPOJO, String bearerToken) throws IOException {
        HttpPost request = new HttpPost(url);

        // Set header content type to JSON
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        // Set bearer token
        if (bearerToken != null) {
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);
        }

        // Convert Mahasiswa object to JSON string
        String requestBody = getValueRequestPojo(requestDtoPOJO);

        // Set request body
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();

        if (responseEntity != null) {
            return EntityUtils.toString(responseEntity);
        }

        return null;
    }
    
    public String sendPutRequest(String url, RequestDtoPOJO requestDtoPOJO, String bearerToken) throws IOException {
        HttpPut request = new HttpPut(url);

        // Set header content type to JSON
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        // Set bearer token
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);

        // Convert Mahasiswa object to JSON string
        String requestBody = getValueRequestPojo(requestDtoPOJO);

        // Set request body
        StringEntity entity = new StringEntity(requestBody);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();

        if (responseEntity != null) {
            return EntityUtils.toString(responseEntity);
        }

        return null;
    }

    public String sendDeleteRequest(String url, String bearerToken) throws IOException {
        HttpDelete request = new HttpDelete(url);

        // Set header content type to JSON
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        // Set bearer token
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();

        if (responseEntity != null) {
            return EntityUtils.toString(responseEntity);
        }

        return null;
    }

    private String getValueRequestPojo(RequestDtoPOJO requestDtoPOJO) throws JsonProcessingException {

        if (requestDtoPOJO.getKomunitasRequest() != null) {
            return objectMapper.writeValueAsString(requestDtoPOJO.getKomunitasRequest());
        } else if (requestDtoPOJO.getAnggotaRequest() != null) {
            return objectMapper.writeValueAsString(requestDtoPOJO.getAnggotaRequest());
        } else if (requestDtoPOJO.getKolamRequest() != null) {
            return objectMapper.writeValueAsString(requestDtoPOJO.getKolamRequest());
        } else if (requestDtoPOJO.getJadwalRequest() != null) {
            return objectMapper.writeValueAsString(requestDtoPOJO.getJadwalRequest());
        } else if (requestDtoPOJO.getLoginRequest() != null) {
            return objectMapper.writeValueAsString(requestDtoPOJO.getLoginRequest());
        } else if (requestDtoPOJO.getAuthorizationRequest() != null) {
            return objectMapper.writeValueAsString(requestDtoPOJO.getAuthorizationRequest());
        } else if (requestDtoPOJO.getSignUpRequest() != null) {
            return objectMapper.writeValueAsString(requestDtoPOJO.getSignUpRequest());
        } else {
            return null;
        }      
    }
    

}
