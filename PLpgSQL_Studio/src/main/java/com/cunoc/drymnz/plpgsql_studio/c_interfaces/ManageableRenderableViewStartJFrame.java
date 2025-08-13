/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.drymnz.plpgsql_studio.c_interfaces;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.StartJFrame;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;

/**
 *
 * @author drymnz
 */
public class ManageableRenderableViewStartJFrame {

    public void renderViewCueston(StartJFrame use) {
        this.renderTextEditor(use);
        this.renderConsole(use);
    }

    private void renderConsole(StartJFrame use) {
        use.getConsoleArea().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_VISUAL_BASIC);
        use.getConsoleArea().setCodeFoldingEnabled(true);
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(use.getConsoleArea());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        use.getConsoleArea().setEditable(false);
        use.getConsoleArea().setText("Console:");
        use.getView().JPanelConsole().setLayout(new BorderLayout());
        use.getView().JPanelConsole().add(use.getConsoleArea(), BorderLayout.CENTER);
    }

    private void renderTextEditor(StartJFrame use) {
        use.getTextArea().setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        use.getTextArea().setCodeFoldingEnabled(true);

        JLabel statusLabel = new JLabel("Línea: 1, Columna: 1");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);

        use.getTextArea().addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                try {
                    int caretPos = use.getTextArea().getCaretPosition();
                    int line = use.getTextArea().getLineOfOffset(caretPos);
                    int column = caretPos - use.getTextArea().getLineStartOffset(line);

                    statusLabel.setText("Línea: " + (line + 1) + ", Columna: " + (column + 1));
                } catch (Exception ex) {
                    statusLabel.setText("Línea: -, Columna: -");
                }
            }
        });

        // Establecer un layout manager
        use.getView().getJPanelTextArea().setLayout(new BorderLayout());
        use.getView().getJPanelTextArea().add(use.getSp(), BorderLayout.CENTER);
        use.getView().getJPanelTextArea().add(statusLabel, BorderLayout.SOUTH);

        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(use.getTextArea());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        use.getView().setTitle("Text Editor Demo");
        use.getView().pack();
    }
}
