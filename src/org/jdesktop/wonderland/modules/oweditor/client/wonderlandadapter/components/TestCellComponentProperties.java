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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.client.login.ServerSessionManager;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.contentrepo.client.ContentRepositoryRegistry;
import org.jdesktop.wonderland.modules.contentrepo.client.ui.modules.ArtContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.client.ui.modules.ModuleContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.client.ui.modules.ModuleRootContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentNode;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentResource;
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
    
    private String imgString = "img_";
    
    
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
            
            try {
                ContentNode fileNode = getFileRoot(originalImageURL);
                //InputStream is = ((ContentResource) fileNode).getInputStream();
                //image = ImageIO.read(is);
                //is.close();
                
                if(fileNode != null){
                 LOGGER.warning("not null "+ fileNode.getName());
            }
                
                
               // InputStream is = ((ContentResource) fileNode).getInputStream();
                //image = ImageIO.read(is);
            } catch (ContentRepositoryException ex) {
                LOGGER.log(Level.SEVERE, "Problem getting the file root", ex);
            } //catch (IOException ex) {
               // LOGGER.log(Level.SEVERE, "could not read input stream", ex);
           // }
            
            
            if(image == null){
                LOGGER.warning("Image is null");
            }else{
                imageInfo.setText(originalImageURL);
            }      
            
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
                
                LOGGER.warning("New URL IS "+ newImageURL);
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
    
    
    /*public String uploadContent(File inputFile) throws IOException {
        
        String name = "";

        int i = inputFile.getName().lastIndexOf('.');
        if (i > 0) {
            name = inputFile.getName().substring(i+1);
            
        }
        LOGGER.warning(name + " "  + inputFile.getName());
        
        final AbstractContentImporter importer = 
                (AbstractContentImporter) cim.getContentImporter(name, true);
        if (importer == null) {
            LOGGER.severe("No importer found for " + inputFile.getName());
            throw new IOException("No importer found for " + inputFile.getName());
        }
        return importer.uploadContent(inputFile);
    }*/
    
    public String uploadContent(File file) throws IOException{
        String name = imgString + file.getName();
        
        try {
            ContentNode fileNode = getFileRoot(name);
            
            if(fileNode == null){
                LOGGER.warning("FILE NODE == 0");
                return null;
            }
            
            if(fileNode instanceof ContentResource){
                InputStream is = new FileInputStream(file);

                ((ContentResource) fileNode).put(is);
                is.close();
                
                
            }else {
                LOGGER.warning("Could not instanciate file root");
            }
        } catch (ContentRepositoryException ex) {
            LOGGER.log(Level.SEVERE, "Could not get to file root", ex);
        }
        LOGGER.warning(name + " "  + file.getName());
        return name;
    }
    
    /**
     * Returns the root directory for all PDF files, pdf/ under the user's
     * WebDav directory.
     * @param filename The name, the file should have on the server.
     * @return The content collection
     * @throws org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException
     */
    public ContentNode getFileRoot(String filename) throws ContentRepositoryException {

        // Fetch the user's root using the current primary server. It should
        // be ok to use the primary server at this point
        ContentRepositoryRegistry r = ContentRepositoryRegistry.getInstance();
        ServerSessionManager session = LoginManager.getPrimary();

        // Try to find the pdf/ directory if it exists, otherwise, create it
        ContentCollection root = r.getRepository(session).getSystemRoot();
        
        if(root instanceof ArtContentCollection){
            LOGGER.warning("ARt");
        }else if (root instanceof ModuleContentCollection){
            LOGGER.warning("Module");
        }else if (root instanceof ModuleRootContentCollection){
            LOGGER.warning("module root");
        }
        
        LOGGER.warning("Can write: "+root.canWrite());
        
        for( ContentNode n : root.getChildren()){
            LOGGER.warning(n.getName());
            if(n instanceof ContentResource)
                LOGGER.warning("Resource");
            else if (n instanceof ContentCollection)
                LOGGER.warning("Collection");
        }
        
        ContentNode imgRoot = root.getChild("img");
        if (imgRoot == null) {
            LOGGER.warning("IMG root is null");
                imgRoot = root.createChild("img", ContentNode.Type.COLLECTION);
        }
        if(imgRoot == null)
            return null;
        
        if(imgRoot instanceof ContentCollection){
            LOGGER.warning("img root is collection");
        }else if (imgRoot instanceof ContentResource)
            LOGGER.warning("img root is resource");
        
        
        
        ContentNode node = root.getChild(filename);
        if (node == null) {
            node = (ContentNode) root.createChild(filename, ContentNode.Type.RESOURCE);
        } else if (!(node instanceof ContentResource)) {
            node.getParent().removeChild(filename);
            node = (ContentNode) root.createChild(filename, ContentNode.Type.RESOURCE);
        }
        return node;
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
