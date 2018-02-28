package com.challenge.cities.util.DTO;

public class ColumnInfo {

    private String column;
    private Integer records;

    public ColumnInfo(){

    }

    public ColumnInfo(String column, Integer records) {
        this.column = column;
        this.records = records;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnInfo that = (ColumnInfo) o;

        return column != null ? column.equals(that.column) : that.column == null;
    }

    @Override
    public int hashCode() {
        return column != null ? column.hashCode() : 0;
    }
}
