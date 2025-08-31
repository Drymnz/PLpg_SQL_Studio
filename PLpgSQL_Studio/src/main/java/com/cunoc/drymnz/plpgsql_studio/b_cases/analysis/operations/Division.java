package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.operations;


import java.util.ArrayList;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ListTypeData;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListTypeOperations;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.AnalyzerSemantico;

public class Division extends Operation{

    public Division(AnalyzerSemantico table,ArrayList<ReportErrorInterpreter> listError) {
        super(table, listError);
    }

    public DataValue operationDivision(DataValue valueLeft, DataValue valueRight, Token token) {
        // Verificar división por cero
        if (isZero(valueRight)) {
            // "División por cero (boolean false)"
            this.reportError(valueLeft, valueRight,token, ListTypeOperations.DIVISION);
            return new DataValue("", ListTypeData.NULL);
        }

        // División de dos integers: resultado es decimal
        if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.INTEGER) {
            double result = (double) Integer.parseInt(valueLeft.getValue()) / Integer.parseInt(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // División de integer y decimal o decimal y integer: resultado es decimal
        else if ((valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.DECIMAL)
                || (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.INTEGER)) {
            double result = Double.parseDouble(valueLeft.getValue()) / Double.parseDouble(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // División de dos decimals: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.DECIMAL) {
            double result = Double.parseDouble(valueLeft.getValue()) / Double.parseDouble(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // División de integer y char: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.CHAR
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.INTEGER) {
            double intValue = (valueLeft.getType() == ListTypeData.INTEGER)
                    ? Integer.parseInt(valueLeft.getValue())
                    : (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            double charValue = (valueRight.getType() == ListTypeData.CHAR)
                    ? (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 1
                    : Integer.parseInt(valueRight.getValue());
            double result = intValue / charValue;
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // División de decimal y char: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.CHAR
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.DECIMAL
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.CHAR
                ) {
            double decimalValue = (valueLeft.getType() == ListTypeData.DECIMAL)
                    ? Double.parseDouble(valueLeft.getValue())
                    : (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            double charValue = (valueRight.getType() == ListTypeData.CHAR)
                    ? (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 1
                    : Double.parseDouble(valueRight.getValue());
            if(charValue == 0)return null;
            double result = decimalValue / charValue;
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // División de integer y boolean: boolean convertido a 0 o 1, resultado es
        // integer
        else if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.BOOLEAN
                || valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.BOOLEAN) {
            if (Boolean.parseBoolean(valueRight.getValue())) {
                return new DataValue(valueLeft.getValue(),
                        (valueLeft.getType() == ListTypeData.INTEGER) ? ListTypeData.INTEGER : ListTypeData.DECIMAL);
            } else {
                // "División por cero (boolean false)"
                this.reportError(valueLeft, valueRight,token,ListTypeOperations.DIVISION);
                return null;
            }
        }
        // División de boolean y decimal: boolean convertido a 0 o 1, resultado es
        // decimal
        else if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.DECIMAL
                || valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.INTEGER) {
            double result = (Boolean.parseBoolean(valueLeft.getValue()) ? 1 : 0)
                    / Double.parseDouble(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // División de integer y char: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.CHAR
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.BOOLEAN) {
            double intValue = (valueLeft.getType() == ListTypeData.BOOLEAN)
                    ? (Boolean.parseBoolean(valueLeft.getValue()) ? 1 : 0)
                    : (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;

            double charValue = (valueRight.getType() == ListTypeData.CHAR)
                    ? (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 1
                    : (Boolean.parseBoolean(valueRight.getValue()) ? 1 : 0);
            if (charValue == 0)
                return null;
            double result = intValue / charValue;
            return new DataValue(String.valueOf(result),(valueRight.getType() == ListTypeData.CHAR)? ListTypeData.DECIMAL : ListTypeData.INTEGER);
        }
        // Para cualquier otra combinación (incluyendo operaciones con string), reportar
        // error
        else {
            this.reportError(valueLeft, valueRight,token, ListTypeOperations.DIVISION);
            return new DataValue("", ListTypeData.NULL);
        }
    }

    // Método auxiliar para verificar si un valor es cero
    private boolean isZero(DataValue value) {
        switch (value.getType()) {
            case INTEGER:
                return Integer.parseInt(value.getValue()) == 0;
            case DECIMAL:
                return Double.parseDouble(value.getValue()) == 0.0;
            case CHAR:
                return (value.getValue().length() > 0 ? value.getValue().charAt(0) == '0' : true);
            case BOOLEAN:
                return !Boolean.parseBoolean(value.getValue());
            default:
                return false;
        }
    }

}