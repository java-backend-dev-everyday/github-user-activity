package com.javadev.githubuseractivity;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class GithubAPITest {

    @BeforeEach
    void resetState() {
        GithubAPI.eventCounter.clear();
        GithubAPI.allEvents.clear();
    }

    @Test
    void testFetchRecentActivity_ValidUsername() throws Exception {
    }

    @Test
    void testFetchRecentActivity_InvalidUsername() throws Exception {
    }

    @Test
    void testParseEvent_ValidEvent() {
    }

    @Test
    void testParseEvent_IncompleteEvent() {
    }

    @Test
    void testFilterEventsByType() {
    }
}
