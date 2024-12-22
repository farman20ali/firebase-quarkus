package org.firebase.dto;

import java.util.List;

public class NotificationRequest {
    private String title;
    private String body;
    private String token;
    private String userId;
    private List<String> tokenList;
    private String topic;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    @Override
    public String toString() {
        return "NotificationRequest [title=" + title + ", body=" + body + ", token=" + token + ", userId=" + userId
                + ", tokenList=" + tokenList + ", topic=" + topic + "]";
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public List<String> getTokenList() {
        return tokenList;
    }
    public void setTokenList(List<String> tokenList) {
        this.tokenList = tokenList;
    }
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
