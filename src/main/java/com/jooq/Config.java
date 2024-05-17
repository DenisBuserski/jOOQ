package com.jooq;

public class Config {
    private final String serverUrl = "jdbc:mysql://localhost:3306/";
    private final String url = "jdbc:mysql://localhost:3306/java_jooq";
    private final String user = "root";
    private final String password = "denis";

    public String getServerUrl() {
        return serverUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
