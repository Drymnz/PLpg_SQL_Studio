#!/bin/bash


echo "Limpiear carpeta sql"
rm -r sql/* 

echo "Compilando JFlex..."
java -jar java_jflex.jar -d sql code_sql.jflex

echo "Compilando CUP..."
java -jar java_cup.jar -destdir sql -interface -parser ParserSQL -symbols SymSQL code_sql.cup

echo "Compilación completada. Archivos generados en carpeta sql/"
