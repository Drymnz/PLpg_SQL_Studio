package com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.sql;

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
%class LexemaSQL
%unicode
%cup
%char
%state DATA_COLLECTION
%eofval{
  return new java_cup.runtime.Symbol(SymSQL.EOF);
%eofval}

%{
    /*START-CODE*/

    private ArrayList<ReportErrorInterpreter> listError = new ArrayList();
    private String dataCollected = "";

    public LexemaSQL(String in) {
        this.zzReader = new StringReader(in);
    }
      
    private void print(String token) {
       // System.out.println(token+ " < " + yytext() + " > <Linea\"" + (yyline + 1) + "\">" + "<Columna\"" + (yycolumn+1) + "\">");
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
DIGIT = [0-9]
WHOLE = {DIGIT}+
DECIMAL = {WHOLE}[.]{WHOLE}
REAL_NUMEBERS = {DECIMAL}|{WHOLE}
IDENTIFICADOR = [a-zA-Z0-9_]+
ALIAS = [a-z]
CHARS = "'" ~"'"
COMMENT_SIMPLE = "--" ~"\n"
COMMENT_MULTILINE = "/*" ~"*/"


%%
<YYINITIAL> {
{espacio}       {}

{COMMENT_SIMPLE} {}
{COMMENT_MULTILINE} {}


"DECLARE"      {print("DECLARE");    return new Symbol(SymSQL.DECLARE   , yyline, yycolumn, yytext());}

// DDL (Data Definition Language) - Definición de Datos
"CONSTRAINT"   {print("CONSTRAINT"); return new Symbol(SymSQL.CONSTRAINT, yyline, yycolumn, yytext());}
"REFERENCES"   {print("REFERENCES"); return new Symbol(SymSQL.REFERENCES, yyline, yycolumn, yytext());}
"FOREIGN"      {print("FOREIGN");    return new Symbol(SymSQL.FOREIGN   , yyline, yycolumn, yytext());}
"PRIMARY"      {print("PRIMARY");    return new Symbol(SymSQL.PRIMARY   , yyline, yycolumn, yytext());}
"CREATE"       {print("CREATE");     return new Symbol(SymSQL.CREATE    , yyline, yycolumn, yytext());}
"SCHEMA"       {print("SCHEMA");     return new Symbol(SymSQL.SCHEMA    , yyline, yycolumn, yytext());}
"TABLE"        {print("TABLE");      return new Symbol(SymSQL.TABLE     , yyline, yycolumn, yytext());}
"ALTER"        {print("ALTER");      return new Symbol(SymSQL.ALTER     , yyline, yycolumn, yytext());}
"COLUMN"       {print("COLUMN");     return new Symbol(SymSQL.COLUMN    , yyline, yycolumn, yytext());}
"DROP"         {print("DROP");       return new Symbol(SymSQL.DROP      , yyline, yycolumn, yytext());}
"KEY"          {print("KEY");        return new Symbol(SymSQL.KEY       , yyline, yycolumn, yytext());}
"ADD"          {print("ADD");        return new Symbol(SymSQL.ADD       , yyline, yycolumn, yytext());}
"USE"          {print("USE");        return new Symbol(SymSQL.USE       , yyline, yycolumn, yytext());}
"MODIFY"       {print("MODIFY");      return new Symbol(SymSQL.MODIFY    , yyline, yycolumn, yytext());}

// Tipos de datos (los más largos primero)
"VARCHAR"      {print("VARCHAR");    return new Symbol(SymSQL.VARCHAR   , yyline, yycolumn, yytext());}
"INTEGER"      {print("INTEGER");    return new Symbol(SymSQL.INTEGER   , yyline, yycolumn, yytext());}
"BOOLEAN"      {print("BOOLEAN");    return new Symbol(SymSQL.BOOLEAN   , yyline, yycolumn, yytext());}
"DECIMAL"      {print("DECIMAL");    return new Symbol(SymSQL.DECIMAL   , yyline, yycolumn, yytext());}
"DATE"         {print("DATE");       return new Symbol(SymSQL.DATE      , yyline, yycolumn, yytext());}
"INT"          {print("INT");        return new Symbol(SymSQL.INT       , yyline, yycolumn, yytext());}

// DML (Data Manipulation Language) - Manipulación de Datos
"LEFT"         {print("LEFT");       return new Symbol(SymSQL.LEFT      , yyline, yycolumn, yytext());}
"JOIN"         {print("JOIN");       return new Symbol(SymSQL.JOIN      , yyline, yycolumn, yytext());}
"SELECT"       {print("SELECT");     return new Symbol(SymSQL.SELECT    , yyline, yycolumn, yytext());}
"INSERT"       {print("INSERT");     return new Symbol(SymSQL.INSERT    , yyline, yycolumn, yytext());}
"UPDATE"       {print("UPDATE");     return new Symbol(SymSQL.UPDATE    , yyline, yycolumn, yytext());}
"DELETE"       {print("DELETE");     return new Symbol(SymSQL.DELETE    , yyline, yycolumn, yytext());}
"VALUES"       {print("VALUES");     return new Symbol(SymSQL.VALUES    , yyline, yycolumn, yytext());}
"WHERE"        {print("WHERE");      return new Symbol(SymSQL.WHERE     , yyline, yycolumn, yytext());}
"FROM"         {print("FROM");       return new Symbol(SymSQL.FROM      , yyline, yycolumn, yytext());}
"INTO"         {print("INTO");       return new Symbol(SymSQL.INTO      , yyline, yycolumn, yytext());}
"SET"          {print("SET");        return new Symbol(SymSQL.SET       , yyline, yycolumn, yytext());}

// DCL (Data Control Language) - Control de Datos
"CREATE"       {print("CREATE");     return new Symbol(SymSQL.CREATE    , yyline, yycolumn, yytext());}
"REVOKE"       {print("REVOKE");     return new Symbol(SymSQL.REVOKE    , yyline, yycolumn, yytext());}
"GRANT"        {print("GRANT");      return new Symbol(SymSQL.GRANT     , yyline, yycolumn, yytext());}
"USER"         {print("USER");       return new Symbol(SymSQL.USER      , yyline, yycolumn, yytext());}
"FROM"         {print("FROM");       return new Symbol(SymSQL.FROM      , yyline, yycolumn, yytext());}
"TO"           {print("TO");         return new Symbol(SymSQL.TO        , yyline, yycolumn, yytext());}
"ON"           {print("ON");         return new Symbol(SymSQL.ON        , yyline, yycolumn, yytext());}

// Restricciones y modificadores
"NOT"          {print("NOT");        return new Symbol(SymSQL.NOT       , yyline, yycolumn, yytext());}
"NULL"         {print("NULL");       return new Symbol(SymSQL.NULL      , yyline, yycolumn, yytext());}
"DEFAULT"      {print("DEFAULT");    return new Symbol(SymSQL.DEFAULT   , yyline, yycolumn, yytext());}


// Operadores lógicos
"AND"          {print("AND");        return new Symbol(SymSQL.AND       , yyline, yycolumn, yytext());}
"OR"           {print("OR");         return new Symbol(SymSQL.OR        , yyline, yycolumn, yytext());}
"TRUE"         {print("TRUE");       return new Symbol(SymSQL.TRUE        , yyline, yycolumn, yytext());}
"FALSE"        {print("FALSE");      return new Symbol(SymSQL.FALSE        , yyline, yycolumn, yytext());}


// FINALIZACION 
";"            {print(";");         return new Symbol(SymSQL.PERIOD_AND_AS, yyline, yycolumn, yytext());}
"("            {print("(");         return new Symbol(SymSQL.OPEN_P       , yyline, yycolumn, yytext());}
")"            {print(")");         return new Symbol(SymSQL.CLOSE_P      , yyline, yycolumn, yytext());}
","            {print(",");         return new Symbol(SymSQL.COMMA        , yyline, yycolumn, yytext());}
"."            {print(".");         return new Symbol(SymSQL.POINT        , yyline, yycolumn, yytext());}
"="            {print("=");         return new Symbol(SymSQL.EQUAL        , yyline, yycolumn, yytext());}
"*"            {print("*");         return new Symbol(SymSQL.ASTERISK     , yyline, yycolumn, yytext());}




"{_-=>"         {
                    yybegin(DATA_COLLECTION);
                    print("{_-=>"); 
                    return new Symbol(SymSQL.START_HARVESTING ,yyline,yycolumn,yytext());
                }

{ALIAS}        {print("ALIAS");          return new Symbol(SymSQL.ALIAS        , yyline, yycolumn, yytext());}
{REAL_NUMEBERS} {print("REAL_NUMEBERS"); return new Symbol(SymSQL.NUMEBERS     , yyline, yycolumn, yytext());}
{IDENTIFICADOR} {print("IDENTIFICADOR"); return new Symbol(SymSQL.IDENTIFICADOR, yyline, yycolumn, yytext());}
{CHARS}         {print("CHARS");         return new Symbol(SymSQL.CHARS         , yyline, yycolumn, yytext());}

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
                    return new Symbol(SymSQL.OUT_HARVESTING ,yyline,yycolumn,yytext());
                }
[^]     { dataCollected += yytext();}
.       { dataCollected += yytext();}
}