package com.example.gpswhere;

public class User {

    private String code;
    private String email;
    private String imageUrl;
    private Boolean issharding;
    private Double lat;
    private Double lng;
    private String name;
    private String password;

    private  String userId;

    public User(String code, String email, String imageUrl, Boolean issharding, Double lat, Double lng, String name, String password,String userId) {
        this.code = code;
        this.email = email;
        this.imageUrl = imageUrl;
        this.issharding = issharding;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.password = password;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIssharding() {
        return issharding;
    }

    public void setIssharding(Boolean issharding) {
        this.issharding = issharding;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
