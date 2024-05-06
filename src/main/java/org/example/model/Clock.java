package org.example.model;

import java.time.LocalTime;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {

    private LocalTime rightNow;
    private Timer timer;
    private boolean locked;
    private JTextField jtextField;
    
    public Clock(JTextField jTextField) {
        this.jtextField = jTextField;
        rightNow = LocalTime.now();
        locked = false;
        timer = new Timer();
        startClock();
    }
    
    public void startClock() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!locked) {
                    initClock();
                    updateJTextField();
                }
            }            
        }, 0, 1000);
    }
    
    public void initClock() {
        rightNow = rightNow.plusSeconds(1);
    }
    
    public void updateJTextField() {
        String timeFormat = String.format("%02d:%02d:%02d", rightNow.getHour(), rightNow.getMinute(), rightNow.getSecond());
        jtextField.setText(timeFormat);
    }
    
    public String getRightNow() {
        String timeFormat = String.format("%02d:%02d:%02d", rightNow.getHour(), rightNow.getMinute(), rightNow.getSecond());
        return timeFormat;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public void unlockTimer() {
        locked = false;
    }
    
    public void lockTimer() {
        locked = true;
    }
    
    public void setHour(int hour){
        if(hourValid(hour)){
            this.rightNow = this.rightNow.withHour(hour);
        }
    }
    
    public void setMinutes(int minute){
        if(minuteValid(minute)){
            this.rightNow = this.rightNow.withMinute(minute);
        }
    }
    
    public boolean hourValid(int hour){
        if(hour >= 0 && hour < 24){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean minuteValid(int minute){
        if(minute >= 0 && minute < 60){
            return true; 
        } else {
            return false;
        }
    }
    
    public void setNewClock(){
        String textFielString = jtextField.getText();
        String[] divisions = textFielString.split(":");
        if(divisions.length >= 2){
            try {
                int hour = Integer.parseInt(divisions[0]);
                int minute = Integer.parseInt(divisions[1]);
                int second = 0;
                int miliSecond = 0;
                
                if(divisions.length > 2){
                    String[] secondAndMiliSeconds = divisions[2].split("\\.");
                    second = Integer.parseInt(secondAndMiliSeconds[0]);
                }
                setHour(hour);
                setMinutes(minute);
            } catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        } else {
            System.out.println("No es valida la hora");
        }
    }
    
    
}
