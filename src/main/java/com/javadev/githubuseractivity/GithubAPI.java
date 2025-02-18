package com.javadev.githubuseractivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubAPI {
    public static void main(String[] args) {
        String username = args[0];
        System.out.println("Hello " + username + "!");
        fetchRecentActivity(username);
    }

    public static void fetchRecentActivity(String username) {
        String url = "https://api.github.com/users/" + username + "/events";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("Sending GET request to URL: " + url);
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONArray events = new JSONArray(response.toString());
                for (int i = 0; i < events.length(); i++) {
                    JSONObject event = events.getJSONObject(i);
                    System.out.println("Event: " + event.toString());
                }
            } else {
                System.out.println("GET request failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}