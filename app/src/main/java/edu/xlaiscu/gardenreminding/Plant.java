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

    public Plant(String plantName, String photoPath, Date lastWater, int waterInterval) {
        this.plantName = plantName;
        this.photoPath = photoPath;
        this.lastWater = lastWater;
        this.waterInterval = waterInterval;

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, waterInterval * 24);
        nextWater = cal.getTime();
    }

}
