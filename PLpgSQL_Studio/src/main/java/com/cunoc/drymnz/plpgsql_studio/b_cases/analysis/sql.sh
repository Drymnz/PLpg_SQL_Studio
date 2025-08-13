#!/bin/bash

# Script para compilar JFlex y CUP con salida en carpeta sql
# Los JAR están en el directorio actual
# La salida se genera en la carpeta sql/

echo "Compilando JFlex..."
java -jar java_jflex.jar -d sql code_sql.jflex

echo "Compilando CUP..."
java -jar java_cup.jar -destdir sql -interface -parser Parser code_sql.cup

echo "Compilación completada. Archivos generados en carpeta sql/"
