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
package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter.imagecomponent;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.DatatypeConverter;
import org.jdesktop.wonderland.client.cell.properties.CellPropertiesEditor;
import org.jdesktop.wonderland.client.cell.properties.annotation.PropertiesFactory;
import org.jdesktop.wonderland.client.cell.properties.spi.PropertiesFactorySPI;
import org.jdesktop.wonderland.common.cell.state.CellComponentServerState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.oweditor.common.ImageCellComponentServerState;

/**
 * The property sheet for the Tooltip Cell Component.
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
    private BufferedImage img = null;
    private String lastDirRep = "";
    
    /** Creates new form TooltipComponentProperties */
    public ImageCellComponentProperties() {
        // Initialize the GUI
        initComponents();
    }

    /**
     * @inheritDoc()
     */
    public String getDisplayName() {
        return BUNDLE.getString("Image_Cell_Component");
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
        LOGGER.warning("set cell editor");
        this.editor = editor;
    }

    /**
     * @inheritDoc()
     */
    public void open() {
        if(editor == null)
            return;

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
            String imgStr = tss.getImage();
            
            if(imgStr != null || !imgStr.equals("")){
                try {
                    byte[] bimg = DatatypeConverter.parseBase64Binary(imgStr);
                   InputStream in = new ByteArrayInputStream(bimg);
                   img = ImageIO.read(in);
                   in.close();
                } catch (IOException ex) {
                    img = null;
                    Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            String text = "";
            if(img != null)
                text = "Image is stored ("+
                        img.getWidth()+"x"+
                        img.getHeight()+")";
            else
                text = "No image stored.";
            
            imageTextArea.setText(text);
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
       LOGGER.warning("properties 1");
       CellServerState state = editor.getCellServerState();
       
       if(state == null){
           LOGGER.warning("No cell server state");
           return;
       }
       
       LOGGER.warning("properties 2");
       CellComponentServerState compState = state.getComponentServerState(
                ImageCellComponentServerState.class);
       
       if(compState == null){
           LOGGER.warning("Image Component server state == null");
           return;
       }

        // Update the image in the component server state
       if(img != null){
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "jpg", baos);
                baos.flush();
                byte[] bimg = baos.toByteArray();
                baos.close();
                ((ImageCellComponentServerState) compState).setImage(
                        DatatypeConverter.printBase64Binary(bimg));
                
                 // Tell the Cell editor that this property sheet is "dirty"
                editor.addToUpdateList(compState);
                LOGGER.warning("properties 3");
                editor.setPanelDirty(ImageCellComponentProperties.class,
                    false);
             } catch (IOException ex) {
                 Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, null, ex);
             } catch (Exception e){
                 Logger.getLogger(ImageCellComponentServerState.class.getName()).log(Level.SEVERE, "Something went wrong",e);
             }
        }else
            ((ImageCellComponentServerState) compState).setImage(null);
        
       
    }

    /**
     * @inheritDoc()
     */
    public void restore() {
    }
    public void represButtonActionPerformed(java.awt.event.ActionEvent evt){
        
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
                img = ImageIO.read(new File(path));
                repaint();
                imgField.setText(path);
                
                if (editor != null ) {
                    editor.setPanelDirty(ImageCellComponentProperties.class,
                            true);
                }
                
            } catch (IOException e) {
                showError(BUNDLE.getString("ImageError"),BUNDLE.getString("ImageErrorTitle"));
            }

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
        imageTextArea = new javax.swing.JTextArea();
        imgField = new javax.swing.JTextField();
        imgButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();

        //setLayout(new java.awt.GridBagLayout());
        
        imgField.setEditable(false);
        imgField.setToolTipText(BUNDLE.getString("ImportRepTooltipp"));
        imgField.setEnabled(false);
        imgField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                represButtonActionPerformed(evt);
            }
        });
        
        imgButton.setText(BUNDLE.getString("Select"));
        imgButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                represButtonActionPerformed(evt);
            }
        });

        //imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(BUNDLE.getString("Image_Cell_Component"))); // NOI18N
        imagePanel.setLayout(new java.awt.GridLayout(1, 0));

        imageTextArea.setEditable(false);
        imageTextArea.setColumns(20);
        imageTextArea.setRows(5);
        imageTextArea.setEnabled(false);
        imageTextArea.setFocusable(false);

        

        imageTextArea.setColumns(1);
        imageTextArea.setRows(5);
        jScrollPane1.setViewportView(imageTextArea);

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(imagePanelLayout.createSequentialGroup()
                        .addComponent(imgField, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(imgButton)))
                .addContainerGap())
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imgField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgButton))
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
    
    
    
    public void paint(Graphics g) {
        super.paint(g);
        if(img == null){
            return;
        }
        Rectangle b = imagePanel.getBounds();
        int x = b.x;
        int y = b.y + b.height + 10;
        
        int width = this.getWidth()-10;
        //int height = this.getHeight();
        int iwidth = img.getWidth();
        int iheight= img.getHeight();
            
        double scale_w = (double)width/iwidth;

        iheight = (int) Math.round(iheight * scale_w);
        iwidth=width;
        
        g.drawImage(img,x,y,iwidth, iheight,null);
    }
        
    
    private void showError(String text, String title){
        JOptionPane.showMessageDialog(this,
                text, title, JOptionPane.ERROR_MESSAGE);
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JPanel imagePanel;
    private javax.swing.JTextArea imageTextArea;
    private javax.swing.JButton imgButton;
    private javax.swing.JTextField imgField;   
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
