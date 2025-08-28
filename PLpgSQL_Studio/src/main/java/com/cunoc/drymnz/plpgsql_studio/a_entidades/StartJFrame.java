/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.drymnz.plpgsql_studio.a_entidades;

import com.cunoc.drymnz.plpgsql_studio.e_drivers.InicioJFrameView;
import java.io.File;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author drymnz
 */
public class StartJFrame {

    private RSyntaxTextArea textArea;
    private RSyntaxTextArea consoleArea;
    private RTextScrollPane sp;
    private InicioJFrameView view;
    private File userFile;

    /*
     * Constructor
     * @param view
     * @param userFile
     */
    public StartJFrame(InicioJFrameView view) {
        this.view = view;
        this.textArea = new RSyntaxTextArea();
        this.sp = new RTextScrollPane(textArea);
        this.consoleArea = new RSyntaxTextArea();
    }

    public File getUseFile() {
        return this.userFile;
    }

    public InicioJFrameView getView() {
        return this.view;
    }

    public RSyntaxTextArea getTextArea() {
        return this.textArea;
    }
    
    public RSyntaxTextArea getConsoleArea() {
        return this.consoleArea;
    }

    public RTextScrollPane getSp() {
        return this.sp;
    }
}
