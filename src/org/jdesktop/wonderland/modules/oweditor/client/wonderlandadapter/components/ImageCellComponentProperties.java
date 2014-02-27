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

import java.awt.Graphics;
import java.awt.Rectangle;
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
import org.jdesktop.wonderland.client.content.ContentExportManager;
import org.jdesktop.wonderland.client.content.ContentImportManager;
import org.jdesktop.wonderland.client.jme.content.AbstractContentImporter;
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.client.login.ServerSessionManager;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.contentrepo.client.ContentRepository;
import org.jdesktop.wonderland.modules.contentrepo.client.ContentRepositoryRegistry;
import org.jdesktop.wonderland.modules.contentrepo.client.ui.modules.ArtContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.client.ui.modules.ModuleContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.client.ui.modules.ModuleRootContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentCollection;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentNode;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException;
import org.jdesktop.wonderland.modules.contentrepo.common.ContentResource;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentServerState;

/**
 * The property sheet for the Test Cell Component.
 *
 * @author Jordan Slott <jslott@dev.java.net>
 */
@PropertiesFactory(ImageCellComponentServerState.class)
public class ImageCellComponentProperties extends JPanel
        implements PropertiesFactorySPI {

    // The I18N resource bundle
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    
     private static final Logger LOGGER =
            Logger.getLogger(ImageCellComponentProperties.class.getName());

    // The editor window
    private CellPropertiesEditor editor = null;
    
    private String originalImageName = null;
    private String newImageName = null;
    private String originalImageDir = null;
    private String newImageDir = null;
    private BufferedImage image = null;
    
    private String imgString = "img";
    
    
    private String lastDirRep = "";
    
    /** Creates new form TestComponentProperties */
    public ImageCellComponentProperties() {        
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
                ImageCellComponentServerState.class);

        // If there is a tooltip component server state (there should be), then
        // populate its values in the GUI.
        if (state != null) {
            ImageCellComponentServerState tss =
                    (ImageCellComponentServerState) compState;

            // Store away the tooltip text and update the GUI
            originalImageName = tss.getImage();
            originalImageDir = tss.getDir();
            
            if(originalImageName == null){
                imageInfo.setText("No image url stored");
                return;
            }
            
            try {
                ContentNode fileNode = getForeignFile(originalImageName,
                        originalImageDir);
                if(fileNode == null){
                    LOGGER.warning("file node is null");
                    imageInfo.setText("No image was found");
                    return;
                }
                if(fileNode instanceof ContentResource){
                    LOGGER.warning("is instance of resource");
                }
                
                InputStream is = ((ContentResource) fileNode).getInputStream();
                image = ImageIO.read(is);
                is.close();
                
                if(fileNode != null){
                 LOGGER.warning("not null "+ fileNode.getName());
            }
                
                
               // InputStream is = ((ContentResource) fileNode).getInputStream();
                //image = ImageIO.read(is);
            } catch (ContentRepositoryException ex) {
                LOGGER.log(Level.SEVERE, "Problem getting the file root", ex);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "could not read input stream", ex);
            }
            
            
            if(image == null){
                LOGGER.warning("Image is null");
            }else{
                imageInfo.setText(originalImageName);
                repaint();
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
        
        try {
            newImageName = uploadContent(new File(imagePath.getText()));
        } catch (IOException ex) {
            Logger.getLogger(ImageCellComponentProperties.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
                
        if(newImageName == null || newImageDir == null)
            return;
        
        CellServerState state = editor.getCellServerState();
        CellComponentServerState compState = state.getComponentServerState(
                ImageCellComponentServerState.class);

        // Update the tooltip text in the component server state
        ((ImageCellComponentServerState) compState).setImage(newImageName);
        ((ImageCellComponentServerState) compState).setDir(newImageDir);

        // Tell the Cell editor that this property sheet is "dirty"
        editor.addToUpdateList(compState);
        editor.setPanelDirty(ImageCellComponentProperties.class,
                        false);
    }

    /**
     * @inheritDoc()
     */
    public void restore() {
        // Restore from the original state stored.
        newImageName = originalImageName;
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
            if (editor != null && name.equals(originalImageName) == false) {
                editor.setPanelDirty(ImageCellComponentProperties.class,
                        true);
            }
            else if (editor != null) {
                editor.setPanelDirty(ImageCellComponentProperties.class,
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        imagePanel = new javax.swing.JPanel();
        imageInfo = new javax.swing.JTextArea();
        imagePath = new javax.swing.JTextField();
        fileButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();

        //setLayout(new java.awt.GridBagLayout());
        
        imagePath.setEditable(false);
        imagePath.setToolTipText(BUNDLE.getString("ImportRepTooltipp"));
        imagePath.setEnabled(false);
        imagePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });
        
        fileButton.setText(BUNDLE.getString("Select"));
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        //imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(BUNDLE.getString("Image_Cell_Component"))); // NOI18N
        imagePanel.setLayout(new java.awt.GridLayout(1, 0));

        imageInfo.setEditable(false);
        imageInfo.setColumns(20);
        imageInfo.setRows(5);
        imageInfo.setEnabled(false);
        imageInfo.setFocusable(false);

        

        imageInfo.setColumns(1);
        imageInfo.setRows(5);
        jScrollPane1.setViewportView(imageInfo);

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(imagePanelLayout.createSequentialGroup()
                        .addComponent(imagePath, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(fileButton)))
                .addContainerGap())
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imagePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        add(imagePanel);
    }// </editor-fold> 

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
            String path = file.getAbsolutePath();
            lastDirRep = file.getParent();
            
            try {
                //Read file just to make sure.
                ImageIO.read(new File(path));
                repaint();
                imagePath.setText(path);
                
                if (editor != null ) {
                    editor.setPanelDirty(ImageCellComponentProperties.class,
                        true);
                }
            } catch (IOException e) {
                showError("Could not use image, please select another", "image error");
                showError(BUNDLE.getString("ImageError"),BUNDLE.getString("ImageErrorTitle"));
            }

        } 
    }//GEN-LAST:event_fileButtonActionPerformed

    public String uploadContent(File file) throws IOException{
        String name = file.getName();
        
        try {
            ContentResource fileNode = getOwnFile(name);
            
            if(fileNode == null){
                LOGGER.warning("UPLOAD CONTENT:"
                        + "Could not create the necessary node for the file");
                showError("Could not create the necessary file system to upload", "file error");
                return null;
            }
            
            
            InputStream is = new FileInputStream(file);

            fileNode.put(is);
            is.close();
            
            newImageDir = fileNode.getParent().getParent().getName();
              
        } catch (ContentRepositoryException ex) {
            showError("Could not get file root", "file error");
            LOGGER.log(Level.SEVERE, "Could not get to file root", ex);
            return null;
        }
        return name;
    }
    
    /**
     * Returns the root directory for all PDF files, pdf/ under the user's
     * WebDav directory.
     * @param filename The name, the file should have on the server.
     * @return The content collection
     * @throws org.jdesktop.wonderland.modules.contentrepo.common.ContentRepositoryException
     */
    public ContentResource getOwnFile(String filename) throws ContentRepositoryException {

        // Fetch the user's root using the current primary server. It should
        // be ok to use the primary server at this point
        ContentRepositoryRegistry r = ContentRepositoryRegistry.getInstance();
        ServerSessionManager session = LoginManager.getPrimary();

        // Try to find the pdf/ directory if it exists, otherwise, create it
        ContentCollection root = r.getRepository(session).getUserRoot();
        
        ContentNode imgRoot = root.getChild(imgString);
        if (imgRoot == null) {
            LOGGER.warning("IMG root is null");
                imgRoot = root.createChild(imgString, ContentNode.Type.COLLECTION);
        }else if(!(imgRoot instanceof ContentCollection)){
            imgRoot.getParent().removeChild(imgString);
            imgRoot = root.createChild(imgString, ContentNode.Type.COLLECTION);
        }
        if(imgRoot == null){
            LOGGER.warning("Could not create img folder");
            return null;
        }
        
        ContentNode node = root.getChild(filename);
        if (node == null) {
            node = (ContentNode) ((ContentCollection)imgRoot).createChild(
                    filename, ContentNode.Type.RESOURCE);
        } else if (!(node instanceof ContentResource)) {
            node.getParent().removeChild(filename);
            node = (ContentNode) ((ContentCollection)imgRoot).createChild(
                    filename, ContentNode.Type.RESOURCE);
        }
        
        if(!(node instanceof ContentResource)){
            LOGGER.warning("The created node is not the instance of content resource");
            return null;
        }
        
        return (ContentResource) node;
    }
    
    public ContentResource getForeignFile(String filename, String dir)throws ContentRepositoryException{
         // Fetch the user's root using the current primary server. It should
        // be ok to use the primary server at this point
        ContentRepositoryRegistry r = ContentRepositoryRegistry.getInstance();
        ServerSessionManager session = LoginManager.getPrimary();

        // Try to find the pdf/ directory if it exists, otherwise, create it
        ContentCollection root = r.getRepository(session).getUserRoot().getParent();
        
        ContentCollection imgRoot = (ContentCollection)(
                (ContentCollection)root.getChild(dir)).getChild(imgString);
        
        ContentNode node = imgRoot.getChild(filename);
        if(!(node instanceof ContentResource)){
            LOGGER.warning("Downloading File: File is not a Content Resource");
            return null;
        }
        return (ContentResource) node;
        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(image == null){
            return;
        }
        Rectangle b = imagePanel.getBounds();
        int x = b.x;
        int y = b.y + b.height + 10;
        
        int width = this.getWidth()-10;
        //int height = this.getHeight();
        int iwidth = image.getWidth();
        int iheight= image.getHeight();
            
        double scale_w = (double)width/iwidth;

        iheight = (int) Math.round(iheight * scale_w);
        iwidth=width;
        
        g.drawImage(image,x,y,iwidth, iheight,null);
    }
    
    
    public File downloadContent(String url){
        return null;
    }
    
    private void showError(String text, String title){
        JOptionPane.showMessageDialog(this,
                text, title, JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileButton;
    private javax.swing.JTextArea imageInfo;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JTextField imagePath;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
