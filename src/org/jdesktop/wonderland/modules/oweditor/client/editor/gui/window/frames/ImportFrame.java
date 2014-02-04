
package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 *
 * @author Patrick
 */
public class ImportFrame extends javax.swing.JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");

    private String lastDirModel = "";
    private String lastDirRep = "";
    private FrameController fc = null;
    
    private int boundsX = 0;
    private int boundsY = 0;
    private boolean working = false;
    private JProgressBar progressBar = null;
            
    /**
     * Creates new form NewJFrame
     */
    public ImportFrame(FrameController fc) {
        this.fc = fc;
        
        initComponents();
        reset();
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setVisible(false);
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        NumberFormat doubleFormat = new DecimalFormat("0.#####");
        doubleFormat.setGroupingUsed(false);
        
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        locationFieldY = new javax.swing.JFormattedTextField(doubleFormat);
        locationFieldZ = new javax.swing.JFormattedTextField(doubleFormat);
        locationButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        locationFieldX = new javax.swing.JFormattedTextField(doubleFormat);
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        modelField = new javax.swing.JTextField();
        modelButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        imgField = new javax.swing.JTextField();
        imgButton = new javax.swing.JButton();
        image = new ImagePanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rotationFieldX = new javax.swing.JFormattedTextField(doubleFormat);
        rotationFieldY = new javax.swing.JFormattedTextField(doubleFormat);
        rotationFieldZ = new javax.swing.JFormattedTextField(doubleFormat);
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        scaleField = new javax.swing.JFormattedTextField(doubleFormat);
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText(BUNDLE.getString("ImportLocation"));

        jLabel5.setText("Y:");

        jLabel6.setText("Z:");

        locationButton.setText(BUNDLE.getString("ImportLocButton"));
        locationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("X:");
        locationFieldX.setText("0");
        locationFieldY.setText("0");
        locationFieldZ.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(locationFieldY)
                            .addComponent(locationFieldZ)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(locationFieldX, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(locationButton)))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {locationFieldX, locationFieldY, locationFieldZ});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(locationFieldX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(locationFieldY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(locationFieldZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(locationButton)
                .addGap(20, 20, 20))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {locationFieldX, locationFieldY, locationFieldZ});

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText(BUNDLE.getString("ImportModel"));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setText(BUNDLE.getString("ImportModelName"));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        nameField.setToolTipText("");

        modelField.setEditable(false);
        modelField.setToolTipText(BUNDLE.getString("ImportModelTooltipp"));
        modelField.setEnabled(false);
        modelField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelFieldActionPerformed(evt);
            }
        });

        modelButton.setText(BUNDLE.getString("Select"));
        modelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelButtonActionPerformed(evt);
            }
        });

        jLabel12.setText(BUNDLE.getString("ImportRep"));
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        imgField.setEditable(false);
        imgField.setToolTipText(BUNDLE.getString("ImportRepTooltipp"));
        imgField.setEnabled(false);
        imgField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                represFieldActionPerformed(evt);
            }
        });

        imgButton.setText(BUNDLE.getString("Select"));
        imgButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                represButtonActionPerformed(evt);
            }
        });

        image.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout ImageLayout = new javax.swing.GroupLayout(image);
        image.setLayout(ImageLayout);
        ImageLayout.setHorizontalGroup(
            ImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
        );
        ImageLayout.setVerticalGroup(
            ImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(imgField)
                        .addGap(10, 10, 10)
                        .addComponent(imgButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(modelField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(nameField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(modelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(modelButton))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(imgField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80))
                    .addComponent(image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText(BUNDLE.getString("ImportRotation"));

        jLabel8.setText("X:");

        jLabel9.setText("Y:");

        jLabel10.setText("Z:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(rotationFieldY, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotationFieldX, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotationFieldZ, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rotationFieldX, rotationFieldY, rotationFieldZ});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rotationFieldX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(rotationFieldY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(rotationFieldZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {rotationFieldX, rotationFieldY, rotationFieldZ});

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setText(BUNDLE.getString("ImportScale"));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(27, 27, 27)
                .addComponent(scaleField, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(scaleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        okButton.setText(BUNDLE.getString("OK"));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(BUNDLE.getString("Cancel"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        resetButton.setText(BUNDLE.getString("Reset"));
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(okButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton)
                    .addComponent(resetButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setLocation(GUISettings.FRAMEPOSX, GUISettings.FRAMEPOSY);  
        setTitle(BUNDLE.getString("ImportTitle"));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        pack();
    }// </editor-fold>                        

    private void locationButtonActionPerformed(java.awt.event.ActionEvent evt) { 
        working = true;
        
        //TODO: Should also add a focus to the drawing panel,
        //otherwise null pointer!
        this.setVisible(false);
        
        double rotation = Double.valueOf(rotationFieldX.getText());
        double scale = Double.valueOf(scaleField.getText());
        fc.window.chooseLocation(boundsX, boundsY, rotation, scale);
    }                                               

    private void modelFieldActionPerformed(java.awt.event.ActionEvent evt) {                                          
        modelButtonActionPerformed(evt);
    }                                             

    private void represFieldActionPerformed(java.awt.event.ActionEvent evt) {                                            
        represButtonActionPerformed(evt);
    }                                            

    private void modelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File (lastDirModel));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "KMZ Models", "kmz");
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            
            String name = file.getName();
            String path = file.getAbsolutePath();
            lastDirModel = file.getParent();
            
            if(lastDirRep.equals("")){
                lastDirRep = lastDirModel;
            }
            modelField.setText(path);
            
            //split url
            String[] tokens = name.split("\\.(?=[^\\.]+$)");
            
            if(tokens[0] == null || tokens[tokens.length-1] == null){
                showError(BUNDLE.getString("FileReadError"),
                        BUNDLE.getString("FileReadErrorTitle"));
                return;
            }
            
            //set name, if nothing has been entered already
            if(nameField.getText().equals("")){
                nameField.setText(tokens[0]);
            }
            
            //progressBar.setVisible(true);
            int[] bounds = fc.window.loadKMZ(path);
            //progressBar.setVisible(false);
                
            //if bounds are wrong or null
            if(bounds == null || bounds.length<2){
                showError(BUNDLE.getString("ImportWrongBounds"),
                        BUNDLE.getString("ImportWrongBoundsTitle"));
                return;
            }

            this.boundsX = bounds[0];
            this.boundsY = bounds[1];
            okButton.setEnabled(true);
            locationButton.setEnabled(true);
        } 
    }                                           

    private void represButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        
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
            
            if(lastDirModel.equals("")){
                lastDirModel = lastDirRep;
            }
            
            //split url
            /*String[] tokens = name.split("\\.(?=[^\\.]+$)");
            
            if(tokens[0] == null || tokens[tokens.length-1] == null){
                showError(BUNDLE.getString("FileReadError"),
                        BUNDLE.getString("FileReadErrorTitle"));
                return;
            }*/
            
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(path));
                image.setImage(img);
                image.repaint();
                imgField.setText(path);
                
            } catch (IOException e) {
                showError(BUNDLE.getString("ImageError"),BUNDLE.getString("ImageErrorTitle"));
            }

        } 
    }                                         

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {  
        String image_url = imgField.getText();
        String name = nameField.getText();
        
        if(name.equals("")){
            showError(BUNDLE.getString("NoNameError"), 
                    BUNDLE.getString("NoNameErrorTitle"));
            return;
        }

        double x = 0;
        double y = 0;
        double z = 0;
        double rot_x = 0;
        double rot_y = 0;
        double rot_z = 0;
        double scale = 1;
        
        try{
            x = Double.valueOf(locationFieldX.getText());
        }catch(Exception ex){
        }
        try{
            y = Double.valueOf(locationFieldY.getText());
        }catch(Exception ex){
            }
        try{
            z = Double.valueOf(locationFieldZ.getText());
        }catch(Exception ex){
        }
        
        try{
            rot_x = Double.valueOf(rotationFieldX.getText());
        }catch(Exception ex){
        }
        try{
            rot_y = Double.valueOf(rotationFieldY.getText());
        }catch(Exception ex){
        }
        try{
            rot_z = Double.valueOf(rotationFieldZ.getText());
        }catch(Exception ex){
        }
        try{
            scale = Double.valueOf(scaleField.getText());
        }catch(Exception ex){
        }           
       
        
        //progressBar.setVisible(true);
        long id = fc.window.importKMZ(name, image_url, x, y, z, rot_x, rot_y, rot_z, scale);
        //progressBar.setVisible(false);
        
        if(id != -1){
            Object[] options = {BUNDLE.getString("UseExisting"),
                    BUNDLE.getString("Overwrite"),
                    BUNDLE.getString("Cancel")};
            int ret2 = JOptionPane.showOptionDialog(this,
                BUNDLE.getString("ConflictError"),
                BUNDLE.getString("ConflictErrorTitle"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
            
            //copy
            if(ret2 == 0){
                fc.window.importConflictCopy(id);                
            }//overwrite
            else if(ret2 == 1){
                fc.window.importConflictOverwrite(id);
            }//cancel
            else if(ret2==2){
                return;
            }
        }
        working = false;
        setVisible(false);
    } 

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        working = false;
        setVisible(false);
    } 

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        reset();
    } 
    
    public void setVisible(boolean b) {
        super.setVisible(b);
        
        //working means, the choose location button was hit.
        if(b && working){
            working = false;
            return;
        }else if(!b && working)
            return;
        
        working = false;
        
        if(!b){
            modelField.setText("");
            imgField.setText("");
            nameField.setText("");
            image.setImage(null);
            reset();
        }else{
            okButton.setEnabled(false);
            locationButton.setEnabled(false);
        }
    }                

    public void setLocation(double x, double y) {
        locationFieldX.setText(String.valueOf(x));
        locationFieldY.setText(String.valueOf(y));
    }
    
    private void reset(){
        locationFieldX.setText("0");
        locationFieldY.setText("0");
        locationFieldZ.setText("0");
        rotationFieldX.setText("0");
        rotationFieldY.setText("0");
        rotationFieldZ.setText("0");
        scaleField.setText("1");
    }
    
    private void showError(String text, String title){
        JOptionPane.showMessageDialog(this,
                text, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImportFrame().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify                     
    private ImagePanel image;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton locationButton;
    private javax.swing.JButton imgButton;
    private javax.swing.JButton modelButton;
    private javax.swing.JTextField modelField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JFormattedTextField locationFieldX;
    private javax.swing.JFormattedTextField locationFieldY;
    private javax.swing.JFormattedTextField locationFieldZ;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField imgField;
    private javax.swing.JFormattedTextField rotationFieldX;
    private javax.swing.JFormattedTextField rotationFieldY;
    private javax.swing.JFormattedTextField rotationFieldZ;
    private javax.swing.JFormattedTextField scaleField;
    // End of variables declaration   
}
