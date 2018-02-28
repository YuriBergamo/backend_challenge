package com.challenge.cities.database.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
@Document(collection = "cities")
public class CityDomain {

    @Id
    private String ibgeId;
    private String uf;
    private String name;
    private Boolean capital;
    private Double lon;
    private Double lat;
    private String noAccents;
    private String alternativeNames;
    private String microRegion;
    private String mesoRegion;

    public CityDomain(){

    }

    public String getIbgeId() {
        return ibgeId;
    }

    public void setIbgeId(String ibgeId) {
        this.ibgeId = ibgeId;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCapital() {
        return capital;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getNoAccents() {
        return noAccents;
    }

    public void setNoAccents(String noAccents) {
        this.noAccents = noAccents;
    }

    public String getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public String getMicroRegion() {
        return microRegion;
    }

    public void setMicroRegion(String microRegion) {
        this.microRegion = microRegion;
    }

    public String getMesoRegion() {
        return mesoRegion;
    }

    public void setMesoRegion(String mesoRegion) {
        this.mesoRegion = mesoRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityDomain that = (CityDomain) o;

        return ibgeId.equals(that.ibgeId);
    }

    @Override
    public int hashCode() {
        return ibgeId.hashCode();
    }

}
