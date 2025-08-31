package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ErrorTypeInTheInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ListTypeData;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListsDefaultFunctionOperations;

public class DefaultFunctions {
    private ArrayList<ReportErrorInterpreter> listError;
    private final String THE_PARAMETER_IS_INCORRECT = "Es incorrecto el parametro en : ";

    public DefaultFunctions(ArrayList<ReportErrorInterpreter> listError) {
        this.listError = listError;
    }

    public DataValue defaultFunction(DataValue parametro, ListsDefaultFunctionOperations type, Token token) {
        switch (type) {
            case ASC:
                return this.functionASC(parametro, token);
            case DESC:
                return this.functionDESC(parametro, token);
            case LETPAR_NUM:
                return this.functionLETPAR_NUM(parametro, token);
            case LETIMPAR_NUM:
                return this.functionLETIMPAR_NUM(parametro, token);
            case REVERSE:
                return this.functionREVERSE(parametro, token);
            case CARACTER_ALEATORIO:
                return this.functionCARACTER_ALEATORIO();
            case NUM_ALEATORIO:
                return this.functionNUM_ALEATORIO();
            case ALERT_INFO://ejecucion
                return null;
            case EXIT://break
                return null;
            case REDIRECT://Cambiar la pagina
                return null;
            case INSERT://
                return null;
            default:
                return null;
        }
    }

    private DataValue functionASC(DataValue parametro, Token token) {
        if (parametro == null || parametro.getType() != ListTypeData.STRING) {
            parameterError(token, ListsDefaultFunctionOperations.ASC);
            return null;
        }
        // Convertir la cadena a un array de caracteres
        char[] caracteres = parametro.getValue().replaceAll("\"", "").toCharArray();

        // Ordenar el array de caracteres
        java.util.Arrays.sort(caracteres);

        // Convertir el array de caracteres de vuelta a String y retornar
        return new DataValue(new String(caracteres), ListTypeData.STRING);
    }

    private DataValue functionDESC(DataValue parametro, Token token) {
        if (parametro == null || parametro.getType() != ListTypeData.STRING) {
            parameterError(token, ListsDefaultFunctionOperations.DESC);
            return null;
        }
        // Convertir la cadena a un array de Character
        Character[] caracteres = parametro.getValue().replaceAll("\"", "").chars().mapToObj(ch -> (char) ch).toArray(Character[]::new);

        // Ordenar el array de Character en orden descendente
        Arrays.sort(caracteres, Collections.reverseOrder());

        // Convertir el array de Character de vuelta a String y retornar
        return new DataValue(Arrays.stream(caracteres)
                .map(String::valueOf)
                .reduce("", String::concat), ListTypeData.STRING);
    }

    private DataValue functionLETPAR_NUM(DataValue parametro, Token token) {
        if (parametro == null || parametro.getType() != ListTypeData.STRING) {
            parameterError(token, ListsDefaultFunctionOperations.LETPAR_NUM);
            return null;
        }
        String palabra = parametro.getValue().replaceAll("\"", "");
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palabra.length(); i++) {
            char caracter = palabra.charAt(i);
            if (i % 2 == 0) {
                // Posición par (0, 2, 4, ...), codificar a ASCII
                resultado.append((int) caracter);
            } else {
                // Posición impar, mantener el caracter original
                resultado.append(caracter);
            }
        }
        return new DataValue(resultado.toString(), ListTypeData.STRING);
    }

    private DataValue functionLETIMPAR_NUM(DataValue parametro, Token token) {
        if (parametro == null || parametro.getType() != ListTypeData.STRING) {
            parameterError(token, ListsDefaultFunctionOperations.LETIMPAR_NUM);
            return null;
        }
        String palabra = parametro.getValue().replaceAll("\"", "");
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palabra.length(); i++) {
            char caracter = palabra.charAt(i);
            if (i % 2 != 0) {
                // Posición impar (1, 3, 5, ...), codificar a ASCII
                resultado.append((int) caracter);
            } else {
                // Posición par, mantener el caracter original
                resultado.append(caracter);
            }
        }

        return new DataValue(resultado.toString(), ListTypeData.STRING);
    }

    private DataValue functionREVERSE(DataValue parametro, Token token) {
        if (parametro == null || parametro.getType() != ListTypeData.STRING) {
            parameterError(token, ListsDefaultFunctionOperations.REVERSE);
            return null;
        }
        String palabra = parametro.getValue().replaceAll("\"", "");
        //Facil para invertir palabras, y con sume menos memoria
        StringBuilder resultado = new StringBuilder(palabra).reverse();
        return new DataValue(resultado.toString(), ListTypeData.STRING);
    }

    private DataValue functionCARACTER_ALEATORIO() {
        Random random = new Random();
        char randomChar;

        // Decidir si el carácter será mayúscula o minúscula
        if (random.nextBoolean()) {
            // Carácter minúscula entre 'a' y 'z'
            randomChar = (char) ('a' + random.nextInt(26));
        } else {
            // Carácter mayúscula entre 'A' y 'Z'
            randomChar = (char) ('A' + random.nextInt(26));
        }
        // Retornar el carácter como una cadena
        return new DataValue(String.valueOf(randomChar), ListTypeData.STRING);
    }

    private DataValue functionNUM_ALEATORIO() {
        Random random = new Random();
        // Retornar un número aleatorio entre 0 y 9
        return new DataValue(String.valueOf(random.nextInt(10)), ListTypeData.INTEGER);
    }

    //Error de parametros
    private void parameterError(Token token, ListsDefaultFunctionOperations type) {
        this.listError.add(new ReportErrorInterpreter(ErrorTypeInTheInterpreter.SEMANTIC, token,
                this.THE_PARAMETER_IS_INCORRECT + type
        ));
    }
}
