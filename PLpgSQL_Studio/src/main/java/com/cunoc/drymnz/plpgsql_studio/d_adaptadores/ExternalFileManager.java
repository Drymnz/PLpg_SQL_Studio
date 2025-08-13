/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.drymnz.plpgsql_studio.d_adaptadores;

import com.cunoc.drymnz.plpgsql_studio.b_cases.StoreFile;
import com.cunoc.drymnz.plpgsql_studio.b_cases.UploadFile;
import com.cunoc.drymnz.plpgsql_studio.d_drivers.InicioJFrameView;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author drymnz
 */
public class ExternalFileManager {

    //almacenamiento Archivo De Texto
    public void storingFileOfText(File file, String textToSave, InicioJFrameView view) {
        this.saven(file, textToSave);
    }
    
    public void openFileOfText(File file, String textToSave, InicioJFrameView view){
         //Cargar archivo de texto
        this.verify(file, textToSave, view);
        file = selectFile("Abrier");
        if (file != null) {
            view.getTextArea().setText(new UploadFile().loadTextFile(file));
        }
    }
    
    private void verify(File file, String textToSave, InicioJFrameView view){
        boolean fileLoad = (file != null);
        //true tienes un archivo cargado
        boolean areaTextIsBlank = textToSave.isEmpty();
        //true el texto esta vacio
        String text = fileLoad ? "Tienes un archivo cargado" : areaTextIsBlank ? "" : "Tienes un texto que puedes perder";
        if (fileLoad || !areaTextIsBlank) {
            int respuesta = JOptionPane.showConfirmDialog(null, text + "¿Desea Guardar el texto que tiene?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                //Guardar
                this.saven(file,textToSave);
            }
        }
    }
    
    // Guardar el texto en archivo
    private void saven(File file, String textToSave) {
        try {
            if (file == null) {
                file = selectFile("Guardar");
            }
            if (new StoreFile().aguardarTexto(file, textToSave)) {
                JOptionPane.showMessageDialog(null, "Se guardo con exito");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        } catch (Exception e) {
            System.out.println("Error en la funcion saven - view menu ->" + e.getMessage());
        }
    }
    
    //Selecion de un archivo
    private File selectFile(String botonText) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showDialog(null, botonText); // Muestra el diálogo
        if (result == JFileChooser.APPROVE_OPTION) {
            // El usuario seleccionó un archivo
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
