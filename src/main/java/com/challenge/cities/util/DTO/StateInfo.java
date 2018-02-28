package com.challenge.cities.util.DTO;

public class StateInfo {

    private String uf;
    private Integer cities;

    public StateInfo() {
    }

    public StateInfo(String uf, Integer cities) {
        this.uf = uf;
        this.cities = cities;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getCities() {
        return cities;
    }

    public void setCities(Integer cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateInfo stateInfo = (StateInfo) o;

        return uf != null ? uf.equals(stateInfo.uf) : stateInfo.uf == null;
    }

    @Override
    public int hashCode() {
        return uf != null ? uf.hashCode() : 0;
    }
}
