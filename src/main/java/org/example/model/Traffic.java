/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.model;

/**
 *
 * @author enmer
 */
public class Traffic {
    int initHour;
    int endHour;
    int trafficProbability;

    public Traffic(int initHour, int endHour, int trafficProbability) {
        this.initHour = initHour;
        this.endHour = endHour;
        this.trafficProbability = trafficProbability;
    }

    public int getInitHour() {
        return initHour;
    }

    public void setInitHour(int initHour) {
        this.initHour = initHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getTrafficProbability() {
        return trafficProbability;
    }

    public void setTrafficProbability(int trafficProbability) {
        this.trafficProbability = trafficProbability;
    }

    
    
}
