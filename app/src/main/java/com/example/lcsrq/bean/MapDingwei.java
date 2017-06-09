package com.example.lcsrq.bean;

import java.io.Serializable;

/**
 * Created by 苏毅 on 2017/5/16.
 */

public class MapDingwei implements Serializable{

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLag() {
        return Lag;
    }

    public void setLag(Double lag) {
        Lag = lag;
    }

    private Double lat;
    private Double Lag;

}
