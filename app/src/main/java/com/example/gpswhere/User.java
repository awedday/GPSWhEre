package com.example.gpswhere;

public class User {

    private String code;
    private String email;
    private String imageUrl;
    private Boolean issharding;
    private Float lat;
    private Float lng;
    private String name;
    private String password;

    public User(String code, String email, String imageUrl, Boolean issharding, Float lat, Float lng, String name, String password) {
        this.code = code;
        this.email = email;
        this.imageUrl = imageUrl;
        this.issharding = issharding;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.password = password;
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

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
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
