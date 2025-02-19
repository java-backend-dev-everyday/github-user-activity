package com.javadev.githubuseractivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GithubAPI {

    public static final Map<String, Integer> eventCounter = new HashMap<>();
    private static final List<JSONObject> allEvents = new ArrayList<>();

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
                    allEvents.add(event);
                    parseEvent(event);
                }
                printEventStatistics();

                Scanner scanner = new Scanner(System.in);
                while(true){
                    System.out.println("\nEnter an event type ('PushEvent', 'CreateEvent', etc.) to filter.");
                    System.out.println("Type 'exit' to stop:");

                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting...");
                        break;
                    }
                    filterEventsByType(input);
                }
                scanner.close();
            } else {
                System.out.println("GET request failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseEvent(JSONObject event) {
        String createdAtRaw = event.getString("created_at");
        LocalDateTime createdAt = LocalDateTime.parse(createdAtRaw.replace("Z", ""));
        String formattedDate = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String eventType = event.getString("type");

        eventCounter.put(eventType, eventCounter.getOrDefault(eventType, 0) + 1);

        JSONObject repo = event.getJSONObject("repo");
        String repoName = repo.getString("name");

        switch (eventType) {
            case "PushEvent": {
                JSONObject payload = event.getJSONObject("payload");
                int commitCount = payload.getJSONArray("commits").length();
                System.out.println("[" + formattedDate + "] Pushed " + commitCount + " commits to repo " + repoName);
                break;
            }

            case "CreateEvent": {
                String refType = event.getJSONObject("payload").getString("ref_type");
                if (refType.equals("repository")) {
                    System.out.println("[" + formattedDate + "] Created repository: " + repoName);
                } else if (refType.equals("branch")) {
                    String branchName = event.getJSONObject("payload").getString("ref");
                    System.out.println("[" + formattedDate + "] Created branch " + branchName + " in repo " + repoName);
                }
                break;
            }

            case "DeleteEvent": {
                String refType = event.getJSONObject("payload").getString("ref_type");
                String ref = event.getJSONObject("payload").getString("ref");
                System.out.println("[" + formattedDate + "] Deleted " + refType + ": " + ref + " in repo " + repoName);
                break;
            }

            case "ForkEvent": {
                System.out.println("[" + formattedDate + "] Forked repository " + repoName);
                break;
            }

            case "IssuesEvent": {
                String action = event.getJSONObject("payload").getString("action");
                System.out.println("[" + formattedDate + "] Issue " + action + " in repo " + repoName);
                break;
            }

            case "IssueCommentEvent": {
                String action = event.getJSONObject("payload").getString("action");
                System.out.println("[" + formattedDate + "] Comment " + action + " on an issue in repo " + repoName);
                break;
            }

            case "PullRequestEvent": {
                String action = event.getJSONObject("payload").getString("action");
                System.out.println("[" + formattedDate + "] Pull request " + action + " in repo " + repoName);
                break;
            }

            case "PullRequestReviewEvent": {
                String action = event.getJSONObject("payload").getString("action");
                System.out.println("[" + formattedDate + "] Review " + action + " on a pull request in repo " + repoName);
                break;
            }

            case "PullRequestReviewCommentEvent": {
                System.out.println("[" + formattedDate + "] Review comment added to pull request in repo " + repoName);
                break;
            }

            case "ReleaseEvent": {
                String releaseName = event.getJSONObject("payload").getJSONObject("release").getString("name");
                System.out.println("[" + formattedDate + "] Published release: " + releaseName + " in repo " + repoName);
                break;
            }

            case "WatchEvent": {
                System.out.println("[" + formattedDate + "] Starred repository: " + repoName);
                break;
            }

            case "GollumEvent": {
                System.out.println("[" + formattedDate + "] Updated wiki pages in repo " + repoName);
                break;
            }

            case "PublicEvent": {
                System.out.println("[" + formattedDate + "] Made repository public: " + repoName);
                break;
            }

            case "CommitCommentEvent": {
                String comment = event.getJSONObject("payload").getJSONObject("comment").getString("body");
                System.out.println("[" + formattedDate + "] Commented on a commit in repo " + repoName + ": " + comment);
                break;
            }

            case "DeploymentEvent": {
                System.out.println("[" + formattedDate + "] Deployment created in repo " + repoName);
                break;
            }

            case "DeploymentStatusEvent": {
                String state = event.getJSONObject("payload").getJSONObject("deployment_status").getString("state");
                System.out.println("[" + formattedDate + "] Deployment status changed to " + state + " in repo " + repoName);
                break;
            }

            default:
                System.out.println("[" + formattedDate + "] Event of type " + eventType + " in repo " + repoName);
                break;
        }
    }

    public static void printEventStatistics() {
        System.out.println("\n=== Event Statistics ===");
        int index = 1;
        for (Map.Entry<String, Integer> entry : eventCounter.entrySet()) {
            System.out.println("Event Type: " + entry.getKey() + " | Count: " + entry.getValue());
            index++;
        }
        System.out.println("=========================");
    }

    public static void filterEventsByType(String eventType) {
        System.out.println("Filtering events by type: " + eventType);

        for (JSONObject event : allEvents) {
            if (event.getString("type").equalsIgnoreCase(eventType)) {
                parseEvent(event);
            }
        }
    }
}
