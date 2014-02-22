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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdesktop.wonderland.client.cell.properties.CellPropertiesEditor;
import org.jdesktop.wonderland.client.cell.properties.annotation.PropertiesFactory;
import org.jdesktop.wonderland.client.cell.properties.spi.PropertiesFactorySPI;
import org.jdesktop.wonderland.client.content.ContentImportManager;
import org.jdesktop.wonderland.client.jme.content.AbstractContentImporter;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.oweditor.common.TestCellComponentServerState;

/**
 * The property sheet for the Test Cell Component.
 *
 * @author Jordan Slott <jslott@dev.java.net>
 */
@PropertiesFactory(TestCellComponentServerState.class)
public class TestCellComponentProperties extends JPanel
        implements PropertiesFactorySPI {

    // The I18N resource bundle
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
     private static final Logger LOGGER =
            Logger.getLogger(TestCellComponentProperties.class.getName());

    // The editor window
    private CellPropertiesEditor editor = null;
    
    private String originalImageURL = null;
    private String newImageURL = null;
    private BufferedImage image = null;
    private ContentImportManager cim = null;
    
    
    private String lastDirRep = "";
    
    /** Creates new form TestComponentProperties */
    public TestCellComponentProperties() {
         cim = ContentImportManager.getContentImportManager();
        
        
        // Initialize the GUI
        initComponents();

        // Listen for changes to the info text field, and update the "dirty"
        // state if necessary
        imageInfo.getDocument().addDocumentListener(
                new InfoTextFieldListener());
    }

    /**
     * @inheritDoc()
     */
    public String getDisplayName() {
        return "Test_Cell_Component";
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

        // Fetch the tooltip component state from the Cell server state.
        CellServerState state = editor.getCellServerState();
        CellComponentServerState compState = state.getComponentServerState(
                TestCellComponentServerState.class);

        // If there is a tooltip component server state (there should be), then
        // populate its values in the GUI.
        if (state != null) {
            TestCellComponentServerState tss =
                    (TestCellComponentServerState) compState;

            // Store away the tooltip text and update the GUI
            originalImageURL = tss.getImage();
            
            if(originalImageURL == null){
                imageInfo.setText("No image url stored");
                return;
            }
            File file = new File(originalImageURL);
            
            
            try {
                //AssetURI c = new AssetURI(originalImageURL);
                image = ImageIO.read(file);
                
                if(image != null){
                    imageInfo.setText("Image present "+image.getWidth() + "x"+
                        image.getHeight());
                }else{
                    imageInfo.setText("Image not present");
                }
            } catch (IOException ex) {
                Logger.getLogger(TestCellComponentProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(image == null){
                LOGGER.warning("File not null" + file.getName() + " " + file.length());
            }
            
            //image = ImageTranslator.StringToImage(originalImageURL);
            
            
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
        // Fetch the latest tooltip component server state from the Cell server
        // state.
        CellServerState state = editor.getCellServerState();
        CellComponentServerState compState = state.getComponentServerState(
                TestCellComponentServerState.class);

        // Update the tooltip text in the component server state
        ((TestCellComponentServerState) compState).setImage(newImageURL);

        // Tell the Cell editor that this property sheet is "dirty"
        editor.addToUpdateList(compState);
        editor.setPanelDirty(TestCellComponentProperties.class,
                        false);
    }

    /**
     * @inheritDoc()
     */
    public void restore() {
        // Restore from the original state stored.
        newImageURL = originalImageURL;
    }

    /**
     * Inner class to listen for changes to the text field and fire off dirty
     * or clean indications to the cell properties editor.
     */
    class InfoTextFieldListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            checkDirty();
        }

        public void removeUpdate(DocumentEvent e) {
            checkDirty();
        }

        public void changedUpdate(DocumentEvent e) {
            checkDirty();
        }

        private void checkDirty() {
            /*String name = tooltipTextArea.getText();
            if (editor != null && name.equals(originalImageURL) == false) {
                editor.setPanelDirty(TestCellComponentProperties.class,
                        true);
            }
            else if (editor != null) {
                editor.setPanelDirty(TestCellComponentProperties.class,
                        false);
            }*/
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        imagePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        imagePath = new javax.swing.JTextField();
        fileButton = new javax.swing.JToggleButton();
        imageInfo = new javax.swing.JTextField();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLayout(new java.awt.GridBagLayout());

        imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(BUNDLE.getString("Image_Cell_Component")));
        imagePanel.setLayout(new java.awt.GridLayout(1, 0));

        imagePath.setEditable(false);
        imagePath.setEnabled(false);
        imagePath.setPreferredSize(new java.awt.Dimension(200, 25));
        imagePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagePathActionPerformed(evt);
            }
        });

        fileButton.setText("File");
        fileButton.setToolTipText("");
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        imageInfo.setEditable(false);
        imageInfo.setEnabled(false);
        imageInfo.setMinimumSize(new java.awt.Dimension(6, 25));
        imageInfo.setName(""); // NOI18N
        imageInfo.setPreferredSize(new java.awt.Dimension(330, 25));
        imageInfo.setRequestFocusEnabled(false);
        imageInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageInfoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(36, 36, 36)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(imageInfo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 318, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(imagePath, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(fileButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(5, 5, 5)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fileButton)
                    .add(imagePath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(imageInfo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        imagePanel.add(jPanel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(imagePanel, gridBagConstraints);
        imagePanel.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void imagePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imagePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imagePathActionPerformed

    private void imageInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imageInfoActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        JFileChooser chooser = new JFileChooser(new File (lastDirRep));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG", "jpg", "jpeg", "JPEG");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
                "GIF", "gif", "GIF");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter(
                "PNG", "png", "PNG");
        FileNameExtensionFilter filter4 = new FileNameExtensionFilter(
                "BMP", "bmp", "BMP");
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.addChoosableFileFilter(filter);
        chooser.addChoosableFileFilter(filter2);
        chooser.addChoosableFileFilter(filter3);
        chooser.addChoosableFileFilter(filter4);
        int returnVal = chooser.showOpenDialog(this);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            
            //String name = file.getName();
            String path = file.getAbsolutePath();
            lastDirRep = file.getParent();
            
            try {
                image = ImageIO.read(new File(path));
                repaint();
                imagePath.setText(path);
                
                newImageURL = uploadContent(chooser.getSelectedFile());
                
                LOGGER.warning(newImageURL);
                //newImage = "Glgal";
                
                if (editor != null ) {
                    editor.setPanelDirty(TestCellComponentProperties.class,
                        true);
                }
            } catch (IOException e) {
                showError(BUNDLE.getString("ImageError"),BUNDLE.getString("ImageErrorTitle"));
            }

        } 
    }//GEN-LAST:event_fileButtonActionPerformed
    
    
    public String uploadContent(File inputFile) throws IOException {
        
        String extension = "";

        int i = inputFile.getName().lastIndexOf('.');
        if (i > 0) {
            extension = inputFile.getName().substring(i+1);
            
        }
        LOGGER.warning(extension + " "  + inputFile.getName());
        
        final AbstractContentImporter importer = 
                (AbstractContentImporter) cim.getContentImporter(extension, true);
        if (importer == null) {
            LOGGER.severe("No importer found for " + inputFile.getName());
            throw new IOException("No importer found for " + inputFile.getName());
        }
        return importer.uploadContent(inputFile);
    }
    
    public File downloadContent(String url){
        return null;
    }
    
    private void showError(String text, String title){
        JOptionPane.showMessageDialog(this,
                text, title, JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton fileButton;
    private javax.swing.JTextField imageInfo;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JTextField imagePath;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
