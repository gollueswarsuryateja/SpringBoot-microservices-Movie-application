package com.softwarebhayya.tmdb.controller;

import com.softwarebhayya.tmdb.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/ratings/inception");
        webRequest = new ServletWebRequest(request);
    }

    @Test
    void handleNotFoundExceptionReturnsNotFoundResponse() {
        NotFoundException exception = new NotFoundException("Movie not found with name inception");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleNotFoundException(exception, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Not Found", response.getBody().get("error"));
        assertEquals("Movie not found with name inception", response.getBody().get("message"));
        assertEquals("/ratings/inception", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void handleRuntimeExceptionReturnsInternalServerErrorResponse() {
        RuntimeException exception = new RuntimeException("Unexpected runtime error");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleRuntimeException(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("Unexpected runtime error", response.getBody().get("message"));
        assertEquals("/ratings/inception", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    void handleGlobalExceptionReturnsInternalServerErrorResponse() {
        Exception exception = new Exception("General exception");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGlobalException(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("General exception", response.getBody().get("message"));
        assertEquals("/ratings/inception", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }
}

