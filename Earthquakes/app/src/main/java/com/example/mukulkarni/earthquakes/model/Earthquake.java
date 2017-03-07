package com.example.mukulkarni.earthquakes.model;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by mukulkarni on 3/6/17.
 */

public class Earthquake {

    private String datetime;
    private Integer depth;
    private Double lng;
    private String src;
    private String eqid;
    private Double magnitude;
    private Double lat;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getEqid() {
        return eqid;
    }

    public void setEqid(String eqid) {
        this.eqid = eqid;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

}
