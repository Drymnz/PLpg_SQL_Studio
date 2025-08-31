package com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer;

public class DataValueDebbuge extends DataValue{
    private boolean modo;
    private String procedure;
    private String id;
    private int line;
    private int executionNumber;

    public DataValueDebbuge(String value, ListTypeData type, boolean modo, String procedure, String id, int line,
            int executionNumber) {
        super(value, type);
        this.modo = modo;
        this.procedure = procedure;
        this.id = id;
        this.line = line;
        this.executionNumber = executionNumber;
    }

    public boolean isModo() {
        return modo;
    }

    public void setModo(boolean modo) {
        this.modo = modo;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getExecutionNumber() {
        return executionNumber;
    }

    public void setExecutionNumber(int executionNumber) {
        this.executionNumber = executionNumber;
    }
}
