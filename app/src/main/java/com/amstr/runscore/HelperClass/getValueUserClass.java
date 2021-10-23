package com.amstr.runscore.HelperClass;

public class getValueUserClass {
    String name,username,password,point,achievements,totalStep,distance,calories;

    public getValueUserClass(String name, String username, String password, String point, String achievements, String totalStep, String distance, String calories) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.point = point;
        this.achievements = achievements;
        this.totalStep = totalStep;
        this.distance = distance;
        this.calories = calories;
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

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getTotalStep() {
        return totalStep;
    }

    public void setTotalStep(String totalStep) {
        this.totalStep = totalStep;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
