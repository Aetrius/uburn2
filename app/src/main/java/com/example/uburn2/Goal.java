package com.example.uburn2;

import java.util.Date;

public class Goal {

    private int _id;
    private double weight;
    private double goalWeight;
    private String goalDate;

    public Goal() {

    }
    public Goal(int id, double weightIn, double goalWeightIn, String goalDateIn) {
        this._id = id;
        this.weight = weightIn;
        this.goalWeight = goalWeightIn;
        this.goalDate = goalDateIn;
    }

    public Goal(double weightIn, double goalWeightIn, String goalDateIn) {
        this.weight = weightIn;
        this.goalWeight = goalWeightIn;
        this.goalDate = goalDateIn;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
    }

    public double getGoalWeight() {
        return this.goalWeight;
    }

    public void setGoalDate(String goalDate) {
        this.goalDate = goalDate;
    }

    public String getGoalDate() {
        return this.goalDate;
    }
}
