/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath"
 * exception as provided by Sun in the License file that accompanied
 * this code.
 */
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.components;

import java.util.ResourceBundle;
import javax.swing.JPanel;
import org.jdesktop.wonderland.client.cell.properties.CellPropertiesEditor;
import org.jdesktop.wonderland.client.cell.properties.annotation.PropertiesFactory;
import org.jdesktop.wonderland.client.cell.properties.spi.PropertiesFactorySPI;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.oweditor.common.IDCellComponentServerState;

/**
 * The property sheet for the Tooltip Cell Component.
 *
 * @author Jordan Slott <jslott@dev.java.net>
 */
@PropertiesFactory(IDCellComponentServerState.class)
public class IDCellComponentProperties extends JPanel
        implements PropertiesFactorySPI {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
        "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
        
    // The editor window
    private CellPropertiesEditor editor = null;

    // The original value for the tooltip text, before any editing
    private long id = -1;
    
    /** Creates new form TooltipComponentProperties */
    public IDCellComponentProperties() {
        // Initialize the GUI
        initComponents();
        warningField.setText(BUNDLE.getString("ID_Cell_Component_Warning"));
    }

    /**
     * @inheritDoc()
     */
    public String getDisplayName() {
        return BUNDLE.getString("ID_Cell_Component");
    }

    /**
     * @inheritDoc()
     */
    public JPanel getPropertiesJPanel() {
        return this;
    }

    /**
     * @inheritDoc()
     */
    public void setCellPropertiesEditor(CellPropertiesEditor editor) {
        this.editor = editor;
    }

    /**
     * @inheritDoc()
     */
    public void open() {

        CellServerState state = editor.getCellServerState();

        if (state != null) {
            CellComponentServerState compState = state.getComponentServerState(
                IDCellComponentServerState.class);
            IDCellComponentServerState tss =
                    (IDCellComponentServerState) compState;
            
            if(tss == null)
                return;
            
            id = tss.getID();
            idField.setText(Long.toString(id));
        }
    }

    /**
     * @inheritDoc()
     */
    public void close() {
        // Do nothing for now.
    }

    /**
     * @inheritDoc()
     */
    public void apply() {
    }

    /**
     * @inheritDoc()
     */
    public void restore() {
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        warningField = new javax.swing.JTextArea();

        jLabel1.setText("ID:");

        idField.setEditable(false);
        idField.setEnabled(false);
        idField.setFocusable(false);
        warningField.setEnabled(false);
        warningField.setEditable(false);
        warningField.setColumns(20);
        warningField.setRows(5);
        warningField.setLineWrap(true);
        warningField.setWrapStyleWord(true);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup()
                .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(warningField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(warningField)
                .addContainerGap(269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField idField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextArea warningField;
    // End of variables declaration//GEN-END:variables
}
