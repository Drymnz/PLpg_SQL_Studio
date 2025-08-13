/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer;

/**
 *
 * @author drymnz
 */
public abstract class AnalyzerBase {

    // Método que ejecuta el análisis. Implementación delegada a la subclase.
    public void analyzer() {
        try {
            executeParse();
        } catch (Error e) {
            System.err.println("Error capturado: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Excepción capturada: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Métodos abstractos que las subclases deben implementar.
    protected abstract void executeParse() throws Exception;

    public abstract boolean isError();

    //public abstract ArrayList<ReportErrorInterpreter> getListError();
}
