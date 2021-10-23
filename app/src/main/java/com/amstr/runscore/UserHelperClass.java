package com.amstr.runscore;

public class UserHelperClass {
    String name,username,password,point,totalStep,achievements,calories,distance;

    public UserHelperClass() {

    }

    public UserHelperClass(String name, String username, String password, String point, String totalStep, String achievements, String calories, String distance) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.point = point;
        this.totalStep = totalStep;
        this.calories = calories;
        this.distance = distance;
        this.achievements = achievements;
        // this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTotalStep() {
        return totalStep;
    }

    public void setTotalStep(String totalStep) {
        this.totalStep = totalStep;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }
}
