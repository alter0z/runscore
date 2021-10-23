package com.amstr.runscore.HomeAdapter;

public class getValueClass {
    String point,totalStep,calories;

    public getValueClass(){

    }

    public getValueClass(String point, String totalStep, String calories) {
        this.point = point;
        this.totalStep = totalStep;
        this.calories = calories;
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
}
