package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.operations;

import java.util.ArrayList;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ListTypeData;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListTypeOperations;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.AnalyzerSemantico;

public class Addition extends Operation{
    public Addition(AnalyzerSemantico table,ArrayList<ReportErrorInterpreter> listError) {
        super(table, listError);
    }

    public DataValue operationAddition(DataValue valueLeft, DataValue valueRight, Token token) {
        // Suma de dos strings: resultado es la concatenación
        if (valueLeft.getType() == ListTypeData.STRING && valueRight.getType() == ListTypeData.STRING
                // Suma de strings y integers : resultado es string
                || valueLeft.getType() == ListTypeData.STRING && valueRight.getType() == ListTypeData.INTEGER
                // Suma de integer y string : resultado es string
                || valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.STRING
                // Suma de string y decimal : resultado es string
                || valueLeft.getType() == ListTypeData.STRING && valueRight.getType() == ListTypeData.DECIMAL
                // Suma de string y char : resultado es string
                || valueLeft.getType() == ListTypeData.STRING && valueRight.getType() == ListTypeData.CHAR
                // Suma de decimal y string : resultado es string
                || valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.STRING
                // Suma de decimal y string : resultado es string
                || valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.STRING) {
            return new DataValue(valueLeft.getValue() + valueRight.getValue(), ListTypeData.STRING);
        }
        // Suma de integer y decimal: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.DECIMAL) {
            int integer = (int) Double.parseDouble(valueLeft.getValue().replace("\"", ""));
            double decimal = Double.parseDouble(valueRight.getValue().replace("\"", ""));
            return new DataValue(String.valueOf(integer + decimal), ListTypeData.DECIMAL);
        }
        // Suma de dos integers: resultado es integer
        else if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.INTEGER) {
            int result = Integer.parseInt(valueLeft.getValue()) + Integer.parseInt(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Suma de dos decimals: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.DECIMAL) {
            double result = Double.parseDouble(valueLeft.getValue()) + Double.parseDouble(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // Suma de dos chars: resultado es string (concatenación)
        else if (valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.CHAR) {
            char valueLeftChar = (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            char valueRightChar = (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 0;
            return new DataValue(String.valueOf(valueLeftChar + valueRightChar), ListTypeData.STRING);
        }
        // Suma de integer y char: resultado es integer (char convertido a ASCII)
        else if (valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.CHAR) {
            int intValue = Integer.parseInt(valueLeft.getValue());
            char charValue = (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 0;
            int result = intValue + (int) charValue;
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Suma de dos booleans: implementado como OR lógico
        else if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.BOOLEAN) {
            boolean result = Boolean.parseBoolean(valueLeft.getValue()) || Boolean.parseBoolean(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.BOOLEAN);
        }
        // Suma de decimal e integer: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.INTEGER) {
            double decimal = Double.parseDouble(valueLeft.getValue());
            int integer = Integer.parseInt(valueRight.getValue());
            return new DataValue(String.valueOf(decimal + integer), ListTypeData.DECIMAL);
        }
        // Suma de decimal e boolean: resultado es decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.BOOLEAN) {
            double decimal = Double.parseDouble(valueLeft.getValue());
            int integer = (Boolean.parseBoolean(valueRight.getValue()) ? 1 : 0);
            return new DataValue(String.valueOf(decimal + integer), ListTypeData.DECIMAL);
        }
        // Suma de char e boolean: resultado es integer
        else if (valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.BOOLEAN) {
            char charValue = (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            int integer = (Boolean.parseBoolean(valueRight.getValue()) ? 1 : 0);
            return new DataValue(String.valueOf(charValue + integer), ListTypeData.INTEGER);
        }
        // Suma de char e integer: resultado es integer (char convertido a ASCII)
        else if (valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.INTEGER) {
            char charValue = (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            int intValue = Integer.parseInt(valueRight.getValue());
            int result = (int) charValue + intValue;
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Suma de char e decimal: resultado es integer (char convertido a ASCII)
        else if (valueLeft.getType() == ListTypeData.CHAR && valueRight.getType() == ListTypeData.DECIMAL) {
            char charValue = (valueLeft.getValue().length() > 0) ? valueLeft.getValue().charAt(0) : 0;
            Double intValue = Double.parseDouble(valueRight.getValue());
            Double result = charValue + intValue;
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        }
        // Suma de boolean e integer: boolean convertido a 0 o 1, resultado es integer
        else if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.INTEGER) {
            int result = (Boolean.parseBoolean(valueLeft.getValue()) ? 1 : 0) + Integer.parseInt(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Suma de boolean e decimal : boolean convertido a 0 o 1, resultado es integer
        else if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.DECIMAL) {
            double result = (Boolean.parseBoolean(valueLeft.getValue()) ? 1 : 0)
                    + Double.parseDouble(valueRight.getValue());
            return new DataValue(String.valueOf(result), ListTypeData.DECIMAL);
        } else if (
        // Suma de integer e boolean: boolean convertido a 0 o 1, resultado es integer
        valueLeft.getType() == ListTypeData.INTEGER && valueRight.getType() == ListTypeData.BOOLEAN) {
            int result = Integer.parseInt(valueLeft.getValue()) + (Boolean.parseBoolean(valueRight.getValue()) ? 1 : 0);
            return new DataValue(String.valueOf(result), ListTypeData.INTEGER);
        }
        // Suma de decimal y char: char convertido a ASCII, resultado es decimal
        else if (valueLeft.getType() == ListTypeData.DECIMAL && valueRight.getType() == ListTypeData.CHAR) {
            double decimal = Double.parseDouble(valueLeft.getValue());
            char charValue = (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 0;
            return new DataValue(String.valueOf(decimal + (int) charValue), ListTypeData.DECIMAL);
        }
        // Suma de boolean y char: char convertido a ASCII, resultado es decimal
        else if (valueLeft.getType() == ListTypeData.BOOLEAN && valueRight.getType() == ListTypeData.CHAR) {
            int decimal = (Boolean.parseBoolean(valueLeft.getValue()) ? 1 : 0);
            char charValue = (valueRight.getValue().length() > 0) ? valueRight.getValue().charAt(0) : 0;
            return new DataValue(String.valueOf(decimal + (int) charValue), ListTypeData.INTEGER);
        }
        // Para cualquier otra combinación no especificada, reportar error
        else {
            this.reportError(valueLeft, valueRight,token,ListTypeOperations.ADDITION);
            return new DataValue("", ListTypeData.NULL);
        }
    }
}
