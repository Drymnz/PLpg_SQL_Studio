package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValueDebbuge;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ErrorTypeInTheInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ListTypeData;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListTypeOperations;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListsDefaultFunctionOperations;

public class AnalyzerSemantico {
    private Map<String, DataValue> tablaSimbolos;
    private ArrayList<DataValueDebbuge> listDebbuge;
    private ArrayList<ReportErrorInterpreter> listError;
    private final String REPEATED_VARIABLE_ID = "Ya existe una variable con este nombre :";
    private final String THAT_VARIABLE_DOES_NOT_EXIST = "No existe la varible :";
    private OperationAnalyzer operationAnalyzer;
    private DefaultFunctions functionDefault;
    private boolean if_instruc = true;


    public AnalyzerSemantico() {
        this.listError = new ArrayList();
        this.listDebbuge = new ArrayList();
        this.tablaSimbolos = new HashMap<>();
        this.operationAnalyzer = new OperationAnalyzer(this,this.listError); 
        this.functionDefault = new DefaultFunctions(this.listError);
    }

    // Registrar una nueva variable en la tabla de símbolos
    public void registerVariables(ArrayList<String> listID, DataValue value, Token token,boolean mode,String procedure,int executionNumber) {
        for (String id_element : listID) {
            this.registerVariable(id_element, value, token, mode, procedure, executionNumber);
        }
    }

    //Registar el dato
    public void registerVariable(String id, DataValue value, Token token,boolean mode,String procedure,int executionNumber) {
        if (this.if_instruc && tablaSimbolos.containsKey(id)) {
            this.repeatedId(id, token);
        } else {
            this.listDebbuge.add(new DataValueDebbuge(value.getValue(), value.getType(), mode, procedure, id, token.getLine(), executionNumber));
            tablaSimbolos.put(id, value);
        }
    }

    //Recupera el valor del dato
    public DataValue retrieveDataVariableOrFunction(String id,Token token){
        if (this.if_instruc && tablaSimbolos.containsKey(id)) {
            return tablaSimbolos.get(id);
        } else {
            DataValue returnarDataValue = this.metodoJs(id);
            if(returnarDataValue != null) return returnarDataValue;
            errorThereisVariable(id, token);
            return  null;
        }
    }

    private DataValue metodoJs(String id){
        if (id.equals("getElemenById")) {
            return new DataValue("", ListTypeData.VOID);
        }
        return  null;
    }

    //Reasignacion de valor 
    public void assignNewData(String id,DataValue dope,Token token){
        DataValue tableValueID = this.retrieveDataVariableOrFunction(id, token);
        if (this.if_instruc && tableValueID != null) {
            tableValueID.setValue(dope.getValue());
            tableValueID.setType(dope.getType());
            this.assignNewDataDebbuge(tableValueID, id, token);
        }
    }

    private void assignNewDataDebbuge(DataValue data,String id,Token token){
        DataValueDebbuge useData = this.searByIdDebbuge(id);
        if (useData != null) {
            this.listDebbuge.add(new DataValueDebbuge(data.getValue(), data.getType(), useData.isModo(), useData.getProcedure() , id, token.getLine(), useData.getExecutionNumber()));
        }
    }

    private DataValueDebbuge searByIdDebbuge(String id){
        for (DataValueDebbuge element : this.listDebbuge) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }

    //Realiza la funcion y returna el resultado
    public DataValue getFunctionResult(DataValue parametro, ListsDefaultFunctionOperations type,Token token){
        return  this.functionDefault.defaultFunction(parametro, type, token);
    }

    //Realiza las operaciones
    public DataValue operationsDatas(DataValue valueLeft, DataValue valueRight, ListTypeOperations typeOperation, Token token) {
        DataValue returnar = this.operationAnalyzer.operations(valueLeft, valueRight, typeOperation,token);
        return returnar;
    }

     // Error si no existe la variable
     private void errorThereisVariable(String id,Token token){
        listError.add(new ReportErrorInterpreter(ErrorTypeInTheInterpreter.SEMANTIC, token, THAT_VARIABLE_DOES_NOT_EXIST + id));
    }

    // Error de id repetido
    private void repeatedId(String id, Token token) {
        listError.add(new ReportErrorInterpreter(ErrorTypeInTheInterpreter.SEMANTIC, token, REPEATED_VARIABLE_ID + id));
    }

    public Map<String, DataValue> getTablaSimbolos() {
        return tablaSimbolos;
    }

    public ArrayList<ReportErrorInterpreter> getListError() {
        return listError;
    }

    public ArrayList<DataValueDebbuge> getListDebbuge() {
        return listDebbuge;
    }

    public void ifOperation(  DataValue operation ,Token token){
        if (operation.getType() == ListTypeData.BOOLEAN) {
            try {
                this.if_instruc = Boolean.parseBoolean(operation.getValue());
            } catch (Exception e) {
                this.if_instruc = true;
                this.listError.add( new ReportErrorInterpreter(ErrorTypeInTheInterpreter.SEMANTIC, token, "No es boolean la condiciion de if ") );
            }
        } else {
            this.listError.add( new ReportErrorInterpreter(ErrorTypeInTheInterpreter.SEMANTIC, token, "No es boolean la condiciion de if ") );
        }
    }

    public void andIf(boolean useIf){
        this.if_instruc  = useIf;
    }

    public boolean getIf_instruc(){
        return this.if_instruc;
    }
}
