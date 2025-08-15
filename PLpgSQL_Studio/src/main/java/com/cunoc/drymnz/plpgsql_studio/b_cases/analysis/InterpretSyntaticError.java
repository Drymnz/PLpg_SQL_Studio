package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis;

import java_cup.runtime.Symbol;
import java_cup.runtime.lr_parser;
import java.util.Stack;

import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.sql.ParserSQL;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.sql.SymSQL;

public class InterpretSyntaticError {
    private final String ERROR_IF_THERE_IS_NO_TOKEN_TO_PARSE = "No hay nada que analizar en token";
    private final String THE_NEXT_TOKEN_WAS_NOT_EXPECTED_CHECK_THE_DOCUMENTATION = "Le falto un caracter antes de :";
    private final String INITIAL_SUGGESTION = "\n- Sugerencia: El error ocurrió despues del lexema. => \"";
    private final String CONTEXT_SUGGESTION = " <-[MT] El siguiente token fue: ";
    private final String FINAL_SUGGESTION = " \" [MT] Revise el manual tecnico ";

    private Stack stack;

    public InterpretSyntaticError(Stack stack) {
        this.stack = stack;
    }

    public String descriptionParser(lr_parser context) {
        if (this.stack != null && this.stack.get(this.stack.size() - 1) != null && this.stack.get(this.stack.size() - 1) instanceof Symbol) {
            Symbol analizer = (Symbol) this.stack.get(this.stack.size() - 1);
            if (analizer.sym == 0) {
                return this.ERROR_IF_THERE_IS_NO_TOKEN_TO_PARSE;
            } else {
                if (analizer.sym == 1) {
                    int index = (this.stack.size() - 2 > 0) ? this.stack.size() - 2 : this.stack.size() - 1;
                    analizer = (Symbol) this.stack.get(index);
                }
                return accordingToItsSymParserLogin(analizer, context);
            }
        }
        return "";
    }

    private String accordingToItsSymParserLogin(Symbol analizer, lr_parser context) {
        String lastToken = lastToken(analizer, context);
        if (lastToken.equals("EOF") || lastToken.equals("error")) {
            return this.ERROR_IF_THERE_IS_NO_TOKEN_TO_PARSE;
        } else {
            return this.THE_NEXT_TOKEN_WAS_NOT_EXPECTED_CHECK_THE_DOCUMENTATION + this.INITIAL_SUGGESTION + lastToken + this.FINAL_SUGGESTION;
        }
    }
    
    private String lastToken(Symbol analizer, lr_parser context){
        if (context instanceof ParserSQL) {
            return textErrorParserCC(analizer);
        }
        /* else if (context instanceof ParserScripting) {
            return SymScripting.terminalNames[analizer.sym];
        } */
        else
        {
            return "EOF";
        }
    }
    private String textErrorParserCC(Symbol analizer){
        return analizer.value.toString() +"\" ES-> " + SymSQL.terminalNames[analizer.sym] + this.CONTEXT_SUGGESTION +SymSQL.terminalNames[analizer.left] +", el cual no se esperaba." ;
    }
}