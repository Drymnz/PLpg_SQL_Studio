package com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer;

public class DataValue {
    private String value;
    private ListTypeData type;

    public DataValue(String value, ListTypeData type) {
        this.value = value;
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(ListTypeData type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public ListTypeData getType() {
        return type;
    }
}
