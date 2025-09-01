package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.operations;


import java.util.ArrayList;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ErrorTypeInTheInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListTypeOperations;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.AnalyzerSemantico;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.OperationAnalyzer;

public class Operation {
    private AnalyzerSemantico table;
    private ArrayList<ReportErrorInterpreter> listError;
    private final String SEPARATOR = " <=> ";

    public Operation(AnalyzerSemantico table, ArrayList<ReportErrorInterpreter> listError) {
        this.table = table;
        this.listError = listError;
    }

    protected void reportError(DataValue valueLeft, DataValue valueRight, Token token, ListTypeOperations type) {
        this.listError.add(new ReportErrorInterpreter(ErrorTypeInTheInterpreter.SEMANTIC, token,
                OperationAnalyzer.ERROR_CANNOT_OPERATE + this.errorDescription(valueLeft, valueRight, type)));
    }

    private String errorDescription(DataValue valueLeft, DataValue valueRight, ListTypeOperations type) {
        return this.SEPARATOR + valueLeft.getValue() + this.SEPARATOR + valueRight.getValue() + this.SEPARATOR + type;
    }

    public AnalyzerSemantico getTable() {
        return table;
    }

    public ArrayList<ReportErrorInterpreter> getListError() {
        return listError;
    }

    public String getSEPARATOR() {
        return SEPARATOR;
    }
}
