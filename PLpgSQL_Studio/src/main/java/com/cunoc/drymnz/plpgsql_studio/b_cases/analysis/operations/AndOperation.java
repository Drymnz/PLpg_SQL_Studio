package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.operations;


import java.util.ArrayList;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ListTypeData;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListTypeOperations;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.AnalyzerSemantico;

public class AndOperation extends Operation {

    public AndOperation(AnalyzerSemantico table, ArrayList<ReportErrorInterpreter> listError) {
        super(table, listError);
        // TODO Auto-generated constructor stub
    }

    public DataValue operationAnd(DataValue valueLeft, DataValue valueRight, Token token) {
        // ============= BOOLEAN con otros tipos =============
        // BOOLEAN y BOOLEAN
        if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.BOOLEAN) {
            boolean left = Boolean.parseBoolean(valueLeft.getValue());
            boolean right = Boolean.parseBoolean(valueRight.getValue());
            return new DataValue(String.valueOf(left && right), ListTypeData.BOOLEAN);
        }
        else {
            this.reportError(valueLeft, valueRight,token,ListTypeOperations.AND);
            return new DataValue("", ListTypeData.NULL);
        }
    }

}