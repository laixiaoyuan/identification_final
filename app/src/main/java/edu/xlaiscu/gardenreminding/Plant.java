package edu.xlaiscu.gardenreminding;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lexie on 5/21/16.
 */
public class Plant {
    String plantName;
    String photoPath;
    Date lastWater;
    int waterInterval;
    Date nextWater;

    public Plant() {

    }

    public Plant(int id, String plantName, String photoPath, Date date, int waterInterval, Date lastWater) {
        this.plantName = plantName;
        this.photoPath = photoPath;
        this.lastWater = lastWater;
        this.waterInterval = waterInterval;
//        this.date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, waterInterval * 24);
        nextWater = cal.getTime();
    }

}
