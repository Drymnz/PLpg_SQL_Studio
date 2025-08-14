package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.ejemplo;

import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.io.StringReader;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ErrorTypeInTheInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.Token;

%%
/*segunda seccion: configuracion*/

%line
%column
%public
%class LexemaEjemplo
%unicode
%cup
%char
%state DATA_COLLECTION
%eofval{
  return new java_cup.runtime.Symbol(SymEjemplo.EOF);
%eofval}

%{
    /*START-CODE*/

/*segunda seccion: configuracion*/
    private ArrayList<ReportErrorInterpreter> listError = new ArrayList();
    private String dataCollected = "";

    public LexemaEjemplo(String in) {
        this.zzReader = new StringReader(in);
    }
      
    private void print(String token) {
        //System.out.println(token+ " < " + yytext() + " > <Linea\"" + (yyline + 1) + "\">" + "<Columna\"" + (yycolumn+1) + "\">");
    }

    private void addError(){
        print("error");
        ErrorTypeInTheInterpreter type = ErrorTypeInTheInterpreter.LEXICON;
        Token toke = new Token(yyline + 1, yycolumn + 1, yytext());
        this.listError.add(new ReportErrorInterpreter(type, toke, ""));
    }

    public ArrayList<ReportErrorInterpreter> getListError() {
        return this.listError;
    }
    
    public String getDataCollected(){
        return this.dataCollected;
    }

    public void setDataCollected(String newdataCollected){
        this.dataCollected = newdataCollected;
    }

    /*FINAL-CODE*/
%}

//DATA = "{_-=>" ~"<=-_}"

espacio =[\n|\r|\t|\f|\b|\s| ]+

%%
<YYINITIAL> {
{espacio}       {}
"CapTcha"       {print("C@pTch@"   ); return new Symbol(SymEjemplo.CapTcha ,yyline,yycolumn,yytext());}
"{_-=>"         {
                    yybegin(DATA_COLLECTION);
                    print("{_-=>"); 
                    return new Symbol(SymEjemplo.START_HARVESTING ,yyline,yycolumn,yytext());
                }
/*ERROR LEXICO*/
[^]                     {
                        //MANEJAR EL ERROR LEXICO
                        print("ERROR");
                        addError();
                        }
}

<DATA_COLLECTION>{
"<=-_}"         {
                    yybegin(YYINITIAL);
                    print("<=-_}"); 
                    return new Symbol(SymEjemplo.OUT_HARVESTING ,yyline,yycolumn,yytext());
                }
[^]     { dataCollected += yytext();}
.       { dataCollected += yytext();}
}