package com.ensimag.ridetrack.model;

import javax.xml.crypto.Data;
import java.util.Date;

public class Positionnement {


    private String id_deviceGroupe;
    private String id_device;
    private Date time_position;
    private float latitude;
    private float longitude;
    private Integer altitude;

    public Positionnement(float latitude, float longitude, Integer altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public Positionnement() {
    }

    public Date getTime_position() {
        return time_position;
    }

    public String getId_deviceGroupe() {
        return id_deviceGroupe;
    }

    public String getId_device(){
        return id_device;
    }

    public float getLatitude(){
        return latitude;
    }

    public float getLongitude(){
        return longitude;
    }

    public void setId_deviceGroupe(String id_deviceGroupe){ this.id_deviceGroupe = id_deviceGroupe;}
    public void setId_device(String id_device){this.id_device = id_device;}
    public void setTime_position(Date time_position){this.time_position=time_position;}
    public void setLatitude(float latitude){this.latitude=latitude;}
    public void setLongitude(float longitude){this.longitude=longitude;}
    public void setAltitude(Integer altitude){this.altitude=altitude;}

}
