package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.operations;


import java.util.ArrayList;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ListTypeData;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListTypeOperations;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.AnalyzerSemantico;

public class Multiplication extends Operation{

    public Multiplication(AnalyzerSemantico table,ArrayList<ReportErrorInterpreter> listError) {
        super(table, listError);
    }

    public DataValue operationMultiplication(DataValue valueLeft, DataValue valueRight, Token token) {
        // Multiplicación de dos integers: resultado es integer
        if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.INTEGER) {
            int result = Integer.parseInt(valueLeft.getValue()) * Integer.parseInt(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Multiplicación de integer y decimal: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.DECIMAL
                || valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.INTEGER) {
            double result = Double.parseDouble(valueLeft.getValue()) * Double.parseDouble(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // Multiplicación de dos decimals: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.DECIMAL) {
            double result = Double.parseDouble(valueLeft.getValue()) * Double.parseDouble(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // Multiplicación de integer y char: resultado es integer (char convertido a
        // ASCII)
        else if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.CHAR
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.INTEGER
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.CHAR) {
            int intValue = (valueLeft.getType() == ListTypeData.INTEGER)
                    ? Integer.parseInt(valueLeft.getValue())
                    : (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            int charValue = (valueRight.getType() == ListTypeData.CHAR)
                    ? (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 0
                    : Integer.parseInt(valueRight.getValue());
            int result = intValue * charValue;
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Multiplicación de decimal y char: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.CHAR
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.DECIMAL) {
            double decimalValue = (valueLeft.getType() == ListTypeData.DECIMAL)
                    ? Double.parseDouble(valueLeft.getValue())
                    : (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            double charValue = (valueRight.getType() == ListTypeData.CHAR)
                    ? (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 0
                    : Double.parseDouble(valueRight.getValue());
            double result = decimalValue * charValue;
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // Multiplicación de boolean y char: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.CHAR
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.BOOLEAN) {
            double decimalValue = (valueLeft.getType() == ListTypeData.BOOLEAN)
                    ? (Boolean.parseBoolean(valueLeft.getValue()) ? 1 : 0)
                    : (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            double charValue = (valueRight.getType() == ListTypeData.CHAR)
                    ? (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 0
                    : (Boolean.parseBoolean(valueRight.getValue()) ? 1 : 0);
            double result = decimalValue * charValue;
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Multiplicación de dos booleans: implementado como AND lógico
        else if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.BOOLEAN) {
            boolean result = Boolean.parseBoolean(valueLeft.getValue()) && Boolean.parseBoolean(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.BOOLEAN);
        }
        // Multiplicación de integer y boolean: boolean convertido a 0 o 1, resultado es
        // integer
        else if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.BOOLEAN
                || valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.INTEGER) {
            int result = (valueLeft.getType() == ListTypeData.INTEGER)
                    ? Integer.parseInt(valueLeft.getValue()) * (Boolean.parseBoolean(valueRight.getValue()) ? 1 : 0)
                    : (Boolean.parseBoolean(valueLeft.getValue()) ? 1 : 0) * Integer.parseInt(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Multiplicación de decimal y boolean: boolean convertido a 0 o 1, resultado es
        // decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.BOOLEAN
                || valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.DECIMAL) {
            double result = (valueLeft.getType() == ListTypeData.DECIMAL)
                    ? Double.parseDouble(valueLeft.getValue()) * (Boolean.parseBoolean(valueRight.getValue()) ? 1 : 0)
                    : (Boolean.parseBoolean(valueLeft.getValue()) ? 1 : 0) * Double.parseDouble(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // Para cualquier otra combinación (incluyendo operaciones con string), reportar
        // error
        else {
            this.reportError(valueLeft, valueRight,token,ListTypeOperations.MULTIPLICATION);
            return new DataValue("", ListTypeData.NULL);
        }
    }

}