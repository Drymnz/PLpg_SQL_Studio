package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis;

import java.util.ArrayList;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.DataValue;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ListTypeData;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.operations.ListTypeOperations;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.operations.*;

public abstract class OperationAnalyzer {
    private Addition addition;
    private Sustraction sustraction;
    private Multiplication multiplication;
    private Division division;

    // Falta implementar operaciones
    private AndOperation and;
    private NotOperation not;
    private OrOperation or;
    private GreaterThanEqual greaterThanEqual;
    private GreaterThan greaterThan;
    private LessThanEqual lessThanEqual;
    private LessThan lessThan;
    private NotTheSame notThenSame;
    private SameAs sameAs;

    public final static String ERROR_CANNOT_OPERATE = "ERROR NO SE PUEDE OPERAR";

    public OperationAnalyzer(AnalyzerSemantico table, ArrayList<ReportErrorInterpreter> listError) {
        this.addition = new Addition(table, listError);
        this.sustraction = new Sustraction(table, listError);
        this.multiplication = new Multiplication(table, listError);
        this.division = new Division(table, listError);
        this.and = new AndOperation(table, listError);
        this.not = new NotOperation(table, listError);
        this.or = new OrOperation(table, listError);
        this.greaterThanEqual = new GreaterThanEqual(table, listError);
        this.greaterThan = new GreaterThan(table, listError);
        this.lessThanEqual = new LessThanEqual(table, listError);
        this.lessThan = new LessThan(table, listError);
        this.notThenSame = new NotTheSame(table, listError);
        this.sameAs = new SameAs(table, listError);
    }

    public DataValue operations(DataValue valueLeft, DataValue valueRight, ListTypeOperations typeOperation,
            Token token) {
        try {
            switch (typeOperation) {
                case ListTypeOperations.MULTIPLICATION:
                    return this.multiplication.operationMultiplication(valueLeft, valueRight, token);
                case ListTypeOperations.DIVISION:
                    return this.division.operationDivision(valueLeft, valueRight, token);
                case ListTypeOperations.SUBTRACTION:
                    return this.sustraction.operationSubtraction(valueLeft, valueRight, token);
                case ListTypeOperations.ADDITION:
                    return this.addition.operationAddition(valueLeft, valueRight, token);
                case ListTypeOperations.AND:
                    return this.and.operationAnd(valueLeft, valueRight, token);
                case ListTypeOperations.NOT:
                    return this.not.operationNot(valueLeft, valueRight, token);
                case ListTypeOperations.OR:
                    return this.or.operationOr(valueLeft, valueRight, token);
                case ListTypeOperations.GREATER_THAN_EQUAL:
                    return this.greaterThanEqual.operationGreaterThanEqual(valueLeft, valueRight, token);
                case ListTypeOperations.GREATER_THAN:
                    return this.greaterThan.operationGreaterThan(valueLeft, valueRight, token);
                case ListTypeOperations.LESS_THAN_EQUAL:
                    return this.lessThanEqual.operationLessThanEqual(valueLeft, valueRight, token);
                case ListTypeOperations.LESS_THAN:
                    return this.lessThan.operationLessThan(valueLeft, valueRight, token);
                case ListTypeOperations.NOT_THE_SAME:
                    return this.notThenSame.operationNotTheSame(valueLeft, valueRight, token);
                case ListTypeOperations.SAME_AS:
                    return this.sameAs.operationSameAs(valueLeft, valueRight, token);
                default:
                    return new DataValue("", ListTypeData.NULL);
            }
        } catch (NumberFormatException e) {
            return new DataValue("", ListTypeData.NULL);
        }
        catch (StringIndexOutOfBoundsException e) {
            return new DataValue("", ListTypeData.NULL);
        }
        catch (NullPointerException e) {
            return new DataValue("", ListTypeData.NULL);
        }
    }
}
