/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.drymnz.plpgsql_studio.a_entidades;

import com.cunoc.drymnz.plpgsql_studio.d_drivers.InicioJFrameView;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author drymnz
 */
public class StartJFrame {
    private RSyntaxTextArea textArea;
    private RTextScrollPane sp;
    private InicioJFrameView view;
    
    public StartJFrame(InicioJFrameView view){
        this.view = view;
    }
    
    public void startResources(){
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);

        // Crear la barra de estado
        JLabel statusLabel = new JLabel("Línea: 1, Columna: 1");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Listener para actualizar la posición del cursor
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                try {
                    int caretPos = textArea.getCaretPosition();
                    int line = textArea.getLineOfOffset(caretPos);
                    int column = caretPos - textArea.getLineStartOffset(line);

                    statusLabel.setText("Línea: " + (line + 1) + ", Columna: " + (column + 1));
                } catch (Exception ex) {
                    statusLabel.setText("Línea: -, Columna: -");
                }
            }
        });

        // Establecer un layout manager
        this.view.getTextArea().setLayout(new BorderLayout());
        this.view.getTextArea().add(sp, BorderLayout.CENTER);
        this.view.getTextArea().add(statusLabel, BorderLayout.SOUTH); // Agregar barra de estado

        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        this.view.setTitle("Text Editor Demo");
        this.view.pack(); // O setSize() si prefieres tamaño fijo
    }
}
