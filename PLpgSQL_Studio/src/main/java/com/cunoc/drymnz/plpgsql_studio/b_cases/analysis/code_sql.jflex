/*primer seccion: codigo de usuario*/

package usac.cunoc.interpretefiguras.logic.analysis;

import java.util.ArrayList;
import java_cup.runtime.Symbol;
import usac.cunoc.interpretefiguras.logic.objectAnalysis.Token;
import usac.cunoc.interpretefiguras.logic.reports.ReportErrorInterpreter;
import usac.cunoc.interpretefiguras.logic.reports.TypeIntreprete;


%%
/*segunda seccion: configuracion*/
%class Lexema
%line
%column
%cup
%public
%unicode

%{
/*CODE*/
    private ArrayList<ReportErrorInterpreter> listError = new ArrayList();
  
    private void print() {
        //System.out.println("\n<" + yytext() + "><Linea\"" + (yyline + 1) + "\">" + "<Columna\"" + (yycolumn+1) + "\">");
    }
    private void addError(){
        TypeIntreprete type = TypeIntreprete.LEXICON;
        Token toke = new Token(yyline + 1, yycolumn + 1, yytext());
        String description = ListErrorAnalyzer.LEXEMA_ONE.getDescription();
        this.listError.add(new ReportErrorInterpreter(type, toke, description));
    }
    public ArrayList<ReportErrorInterpreter> getListError() {
        return this.listError;
    }
/*CODE*/
%}

ALPHABET = [a-zA-Z]|"ñ"|"Ñ"
DIGIT = [0-9]
WHOLE = {DIGIT}+
DECIMAL = {WHOLE}[.]{WHOLE}
REAL_NUMEBERS = {DECIMAL} | {WHOLE}
ID = ({ALPHABET}|{REAL_NUMEBERS}|[_])+ 

espacio =[\n|\r|\t|\f|\b|\s| ]+

%%

/*tercer seccion: reglase lexicas*/
/*INGNORAR*/
{espacio}           {print();}
","                 {
                   print(); return new Symbol(sym.COMA,yyline,yycolumn, (yytext()));
                    }
/*SIMBOLOS ARIMETICOS*/
"+"                 {
                   print(); return new Symbol(sym.SUMAR,yyline,yycolumn, (yytext()));
                    }
"-"                 {
                   print(); return new Symbol(sym.RESTAR,yyline,yycolumn, (yytext()));
                    }
"/"                 {
                   print(); return new Symbol(sym.DIVIDIR,yyline,yycolumn, (yytext()));
                    }
"*"                 {
                   print(); return new Symbol(sym.MULTIPLICAR,yyline,yycolumn, (yytext()));
                    }
/*SIMBOLOS DE AGRUPACION*/
"("                 {
                   print(); return new Symbol(sym.PARENTESIS_A,yyline,yycolumn,yytext());
                    }
")"                 {
                   print(); return new Symbol(sym.PARENTESIS_C,yyline,yycolumn,yytext());
                    }
/*PALABRAS CLAVES*/
//COLOR
"azul"              {
                   print(); return new Symbol(sym.BLUE ,yyline,yycolumn,yytext());                    
                    }
"rojo"              {
                   print(); return new Symbol( sym.RED ,yyline,yycolumn,yytext());                    
                    }
"amarillo"          {
                   print(); return new Symbol( sym.YELLOW ,yyline,yycolumn,yytext());                    
                    }
"verde"             {
                   print(); return new Symbol( sym.GREEN ,yyline,yycolumn,yytext());                    
                    }
"negro"             {
                   print(); return new Symbol( sym.BLACK ,yyline,yycolumn,yytext());                    
                    }
"celeste"           {
                   print(); return new Symbol( sym.CYAN ,yyline,yycolumn,yytext());                    
                    }
"fusia"           {
                   print(); return new Symbol( sym.FUCHSIA ,yyline,yycolumn,yytext());                    
                    }
"lila"           {
                   print(); return new Symbol( sym.LAVENDER ,yyline,yycolumn,yytext());                    
                    }
"corinto"           {
                   print(); return new Symbol( sym.MAROON ,yyline,yycolumn,yytext());                    
                    }
//FIGURAS BASICAS
"circulo"           {
                   print(); return new Symbol( sym.CIRCLE ,yyline,yycolumn,yytext());                    
                    }
"linea"             {
                   print(); return new Symbol( sym.LINE ,yyline,yycolumn,yytext());                    
                    }
"poligono"          {
                   print(); return new Symbol( sym.POLYGON ,yyline,yycolumn,yytext());                    
                    }
"rectangulo"        {
                   print(); return new Symbol( sym.RECTANGLE ,yyline,yycolumn,yytext());                    
                    }
"cuadrado"          {
                   print(); return new Symbol( sym.SQUARE ,yyline,yycolumn,yytext());                    
                    }
//OTRAS
"graficar"          {
                   print(); return new Symbol( sym.GRAFICAR ,yyline,yycolumn,yytext());                    
                    }
"animar"            {
                   print(); return new Symbol( sym.ANIMATION ,yyline,yycolumn,yytext());                    
                    }
"anterior"          {
                   print(); return new Symbol( sym.PREVIOUS ,yyline,yycolumn,yytext());                    
                    }
"objeto"            {
                   print(); return new Symbol( sym.OBJECT ,yyline,yycolumn,yytext());                    
                    }
"curva"            {
                   print(); return new Symbol( sym.CURVE ,yyline,yycolumn,yytext());                    
                    }
{REAL_NUMEBERS}     {
                   print(); return new Symbol( sym.REAL_NUMEBERS ,yyline,yycolumn,yytext());                    
                    }
{ID}                {
                   print(); return new Symbol( sym.ID ,yyline,yycolumn,yytext());                    
                    }

/*ERROR LEXICO*/
.                   {
                    //MANEJAR EL ERROR LEXICO
                        print();
                        addError();
                    }

