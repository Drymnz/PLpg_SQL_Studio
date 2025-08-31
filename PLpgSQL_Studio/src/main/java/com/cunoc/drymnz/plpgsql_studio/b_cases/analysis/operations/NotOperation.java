package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.operations;


import java.util.ArrayList;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ListTypeData;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListTypeOperations;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.AnalyzerSemantico;

public class NotOperation  extends Operation {

    public NotOperation(AnalyzerSemantico table, ArrayList<ReportErrorInterpreter> listError) {
        super(table, listError);
        // TODO Auto-generated constructor stub
    }

    public DataValue operationNot(DataValue valueLeft, DataValue valueRight, Token token) {
        // BOOLEAN
        if (valueRight.getType() == ListTypeData.BOOLEAN) {
            boolean right = Boolean.parseBoolean(valueRight.getValue());
            return new DataValue(String.valueOf(!right), ListTypeData.BOOLEAN);
        } else {
            this.reportError(valueLeft, valueRight, token, ListTypeOperations.NOT);
            return new DataValue("", ListTypeData.NULL);
        }
    }
}