package com.example.uburn2;

import java.util.Date;

public class Weight implements Comparable<Weight> {

    private int _id;
    private double weight;
    private Date date;

    public Weight() {

    }
    public Weight(int id, double weightIn, Date dateIn) {
        this._id = id;
        this.weight = weightIn;
        this.date = dateIn;
    }

    public Weight(double weightIn, Date dateIn) {
        this.weight = weightIn;
        this.date = dateIn;
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

    public void setWeightDate(Date dateIn) {
        this.date = dateIn;
    }

    public Date getWeightDate() {
        return this.date;
    }

    @Override
    public int compareTo(Weight o) {
        if (this.getWeight() > o.getWeight()) {
            return 1;
        } else if (this.getWeight() < o.getWeight()) {
            return -1;
        }
        return 0;
    }

}
