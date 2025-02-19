package com.javadev.githubuseractivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/*
TODO:
- Add a feature to filter activity by event type (e.g., PushEvent, CreateEvent, etc.).
- Display the fetched activity in a more structured format for better readability.
- Implement caching for fetched data to improve performance and reduce repeated API calls.
- Explore other endpoints of the GitHub API to:
  - Fetch additional information about the user (e.g., profile details, followers, etc.).
  - Retrieve details about the user's repositories (e.g., stars, forks, etc.).
*/


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
                    parseEvent(event);
                }
            } else {
                System.out.println("GET request failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseEvent(JSONObject event)
    {
        String eventType = event.getString("type");
        JSONObject repo = event.getJSONObject("repo");
        String repoName = repo.getString("name");
        switch (eventType) {
            case "PushEvent":
                JSONObject payload = event.getJSONObject("payload");
                int commitCount = payload.getJSONArray("commits").length(); // Liczba commitów
                System.out.println("Pushed " + commitCount + " commits to repo " + repoName);
                break;

            case "CreateEvent":
                String refType = event.getJSONObject("payload").getString("ref_type");
                if (refType.equals("repository")) {
                    System.out.println("Created repository: " + repoName);
                } else if (refType.equals("branch")) {
                    String branchName = event.getJSONObject("payload").getString("ref");
                    System.out.println("Created branch " + branchName + " in repo " + repoName);
                }
                break;

            case "IssuesEvent":
                String action = event.getJSONObject("payload").getString("action");
                if (action.equals("opened")) {
                    System.out.println("Created issue in repo " + repoName);
                } else if (action.equals("closed")) {
                    System.out.println("Closed issue in repo " + repoName);
                }
                break;

            default:
                System.out.println("Event of type " + eventType + " in repo " + repoName);
                break;
        }
    }
}
