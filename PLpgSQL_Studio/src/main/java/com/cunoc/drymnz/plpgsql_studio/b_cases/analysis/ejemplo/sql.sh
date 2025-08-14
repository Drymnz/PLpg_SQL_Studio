#!/bin/bash

clear

nameLexema='LexemaEjemplo'
nameClassCup='ParserEjemplo'
nameSymCup='SymEjemplo'

rm -f "$nameLexema.java" 

java -jar ../java_jflex.jar code.jflex 

rm -f "$nameClassCup.java" 
rm -f "$nameSymCup.java" 

java -jar ../java_cup.jar -parser $nameClassCup -symbols $nameSymCup code.cup