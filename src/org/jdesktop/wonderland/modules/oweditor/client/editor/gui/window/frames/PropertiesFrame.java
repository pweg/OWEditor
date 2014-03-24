
package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * This frame is used for importing kmz models.
 * 
 * @author Patrick
 */
public class PropertiesFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER =
            Logger.getLogger(PropertiesFrame.class.getName());
    
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");

    private FrameController fc = null;
    
    private NumberFormat doubleFormat = null;
    
    //color for the labels.
    private final Color errorColor = Color.red;
    private final Color normalColor = Color.black;
    
    //font for the labels.
    private Font normalFont = null;
    
    protected ArrayList<IDataObject> objects = null;
    
    private String imgName = null;
    
    private String initialX = null;
    private String initialY = null;
    private String initialZ = null;
    private String initialRotX = null;
    private String initialRotY = null;
    private String initialRotZ = null;
    private String initialScale = null;
    private String initialName = null;
    private String initialImg = null;
    
    private JTabbedPane tabbedPane = null;
    private PropertiesRightsPane rightsPane = null;
            
    /**
     * Creates new properties frame.
     * 
     * @param fc A frameController instance.
     */
    public PropertiesFrame(FrameController fc) {
        this.fc = fc;
        objects = new ArrayList<IDataObject>();
        
        initComponents();
        reset();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        
        rightsPane = new PropertiesRightsPane(this);
        
        doubleFormat = new DecimalFormat("0.00");
        doubleFormat.setGroupingUsed(false);

        DecimalFormatSymbols custom=new DecimalFormatSymbols();
        custom.setDecimalSeparator('.');
        ((DecimalFormat) doubleFormat).setDecimalFormatSymbols(custom);
        
        tabbedPane = new JTabbedPane();
        
        nameField = new javax.swing.JTextField();        
        image = new ImageButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        locationFieldY = new javax.swing.JFormattedTextField(doubleFormat);
        locationFieldZ = new javax.swing.JFormattedTextField(doubleFormat);
        xLabel = new javax.swing.JLabel();
        locationFieldX = new javax.swing.JFormattedTextField(doubleFormat);
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        image = new ImageButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        rotXLabel = new javax.swing.JLabel();
        rotYLabel = new javax.swing.JLabel();
        rotZLabel = new javax.swing.JLabel();
        
        rotationFieldX = new javax.swing.JFormattedTextField(doubleFormat);
        rotationFieldY = new javax.swing.JFormattedTextField(doubleFormat);
        rotationFieldZ = new javax.swing.JFormattedTextField(doubleFormat);
        jPanel4 = new javax.swing.JPanel();
        scaleLabel = new javax.swing.JLabel();
        scaleField = new javax.swing.JFormattedTextField(doubleFormat);
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText(BUNDLE.getString("Location"));

        yLabel.setText("Y:");

        zLabel.setText("Z:");
        
        xLabel.setText("X:");
        locationFieldX.setText("0");
        locationFieldY.setText("0");
        locationFieldZ.setText("0");
        rotationFieldX.setText("0");
        rotationFieldY.setText("0");
        rotationFieldZ.setText("0");
        scaleField.setText("1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(zLabel)
                            .addComponent(yLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(xLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(locationFieldY)
                            .addComponent(locationFieldZ)
                            .addComponent(locationFieldX, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                    ))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {locationFieldX, locationFieldY, locationFieldZ});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xLabel)
                    .addComponent(locationFieldX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yLabel)
                    .addComponent(locationFieldY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zLabel)
                    .addComponent(locationFieldZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGap(20, 20, 20))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {locationFieldX, locationFieldY, locationFieldZ});

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        nameLabel.setText(BUNDLE.getString("Name"));

        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        image.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageButtonActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(45, 45, 45)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
            )
        );
        
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField)
                    .addComponent(image, 150, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)   
                )
                .addContainerGap()
            )
        );
        
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText(BUNDLE.getString("Rotation"));

        rotXLabel.setText("X:");

        rotYLabel.setText("Y:");

        rotZLabel.setText("Z:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rotZLabel)
                            .addComponent(rotYLabel)
                            .addComponent(rotXLabel))
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
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotXLabel)
                    .addComponent(rotationFieldX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotYLabel)
                    .addComponent(rotationFieldY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotZLabel)
                    .addComponent(rotationFieldZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {rotationFieldX, rotationFieldY, rotationFieldZ});

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        scaleLabel.setText(BUNDLE.getString("Scale"));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scaleLabel)
                .addGap(27, 27, 27)
                .addComponent(scaleField, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scaleLabel)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(layout);
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
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton)
                    .addComponent(resetButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        tabbedPane.addTab(BUNDLE.getString("PropertiesGeneral"), null, jPanel5,
                BUNDLE.getString("PropertiesGeneralInfo"));
        getContentPane().add(tabbedPane);
        tabbedPane.addTab(BUNDLE.getString("PropertiesRights"), null, rightsPane, 
                BUNDLE.getString("PropertiesRightsInfo"));


        setLocation(GUISettings.FRAMEPOSX, GUISettings.FRAMEPOSY);  
        setTitle(BUNDLE.getString("MenuProperties"));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        pack();
    }// </editor-fold>                        
                 
    private void imageButtonActionPerformed(java.awt.event.ActionEvent evt){
        ImageSelectionFrame frame = fc.getImageSelectionFrame();
        frame.setLocationRelativeTo(this);
        frame.setActive(imgName);
        
        /*
         * Add a listener for when the image selection frame is
         * closed.
         */
        frame.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void componentShown(ComponentEvent e) {
                // TODO Auto-generated method stub
            }

            /**
             * Gets the image from the image selection frame.
             */
            @Override
            public void componentHidden(ComponentEvent e) {
                IImage img = fc.imageSelection.getSelectedImage();
                fc.imageSelection.removeComponentListener(this);
                
                if(img == null){
                    image.setImage(null);
                    imgName = null;
                }else{
                    image.setImage(img.getImage());
                    imgName = img.getName();
                }
            }

        });
        frame.setVisible(true);
    }

    protected void okButtonActionPerformed(java.awt.event.ActionEvent evt) {  
        resetLabelColor();
        
        ArrayList<Long> ids = new ArrayList<Long>();
        ArrayList<String> names = null; 
        ArrayList<Float> coordsX = null; 
        ArrayList<Float> coordsY = null; 
        ArrayList<Float> coordsZ = null; 
        ArrayList<Double> rotX = null; 
        ArrayList<Double> rotY = null; 
        ArrayList<Double> rotZ = null; 
        ArrayList<Double> scaleList = null; 
        ArrayList<String> img_name = null;

        String name = nameField.getText();
        String xs = locationFieldX.getText();
        String ys = locationFieldY.getText();
        String zs = locationFieldZ.getText();
        String rot_xs = rotationFieldX.getText();
        String rot_ys = rotationFieldY.getText();
        String rot_zs = rotationFieldZ.getText();
        String scales = scaleField.getText(); 
        
        //look for changes.
        if(objects.size() == 1 && !initialName.equals(name))
            names = new ArrayList<String>(); 
        if(!initialX.equals(xs) || !initialY.equals(ys) ||
                !initialZ.equals(zs)){
            coordsX = new ArrayList<Float>(); 
            coordsY = new ArrayList<Float>(); 
            coordsZ = new ArrayList<Float>(); 
        }
        if(!initialRotX.equals(rot_xs) || !initialRotY.equals(rot_ys) ||
                !initialRotZ.equals(rot_zs)){
            rotX = new ArrayList<Double>(); 
            rotY = new ArrayList<Double>(); 
            rotZ = new ArrayList<Double>(); 
        }
        if(!initialScale.equals(scales))
            scaleList = new ArrayList<Double>(); 

        if((initialImg == null && imgName != null)  || 
                (initialImg != null && imgName == null)
                || (imgName != null && !imgName.equals(initialImg))){
            img_name = new ArrayList<String>(); 
        }
        
        //do nothing on no changes.
        if(names == null && coordsX == null && coordsY == null &&
                coordsZ == null && rotX == null && rotY == null &&
                rotZ == null && scaleList == null && img_name == null){
            setVisible(false);
            return;      
        }
        
        float x = -1;
        float y = -1;
        float z = -1;
        double rot_x = -1;
        double rot_y = -1;
        double rot_z = -1;
        double scale = -1;
        
        boolean error = false;
        
        //check name and position only if one object is selected.
        if(objects.size() == 1){
            if(name.equals("")){
                setLabelColorError(nameLabel);
                error = true;
            }

            //parse strings from the fields.
            try{
                x = Float.parseFloat(xs);
            }catch(NumberFormatException e){
                setLabelColorError(xLabel);
                error = true;
            }
            try{
                y = Float.parseFloat(ys);
            }catch(NumberFormatException e){
                setLabelColorError(yLabel);
                error = true;
            }
            try{
                z = Float.parseFloat(zs);
            }catch(NumberFormatException e){
                setLabelColorError(zLabel);
                error = true;
            }
        }
        //check other objects only, if either one is selected, or
        //different values is not set on multiple objects.
        if((objects.size() > 1 && !rot_xs.equals(BUNDLE.getString("DifferentValues")))
                || objects.size() == 1){
            try{
                rot_x = Double.parseDouble(rot_xs);
            }catch(NumberFormatException e){
                setLabelColorError(rotXLabel);
                error = true;
            }
        }
        if((objects.size() > 1 && !rot_ys.equals(BUNDLE.getString("DifferentValues")))
            || objects.size() == 1){
            try{
                rot_y = Double.parseDouble(rot_ys);
            }catch(NumberFormatException e){
                setLabelColorError(rotYLabel);
                error = true;
            }
        }
        if((objects.size() > 1 && !rot_zs.equals(BUNDLE.getString("DifferentValues")))
            || objects.size() == 1){
            try{
                rot_z = Double.parseDouble(rot_zs);
            }catch(NumberFormatException e){
                setLabelColorError(rotZLabel);
                error = true;
            }
        }
        if((objects.size() > 1 && !scales.equals(BUNDLE.getString("DifferentValues")))
                || objects.size() == 1){
            try{
                scale = Double.parseDouble(scales);  
            }catch(NumberFormatException e){
                setLabelColorError(scaleLabel);
                error = true;
            }
        }
        
        if(error){
            showError(BUNDLE.getString("ImportMissingError"), 
                    BUNDLE.getString("ImportMissingErrorTitle"));
            return;
        }  
        
        //fill the lists which are not null with data.
        for(IDataObject o : objects){
            ids.add(o.getID());
            
            if(names != null)
                names.add(name);
            if(coordsX != null){
                if(x != -1)
                    coordsX.add(x);
                else
                    coordsX.add(o.getXf());
            }
            if(coordsY != null){
                if(y != -1)
                    coordsY.add(y);
                else
                    coordsY.add(o.getYf());
            }
            if(coordsZ != null){
                if(z != -1)
                    coordsZ.add(z);
                else
                    coordsZ.add(o.getZf());
            }
            if(rotX != null){
                if(rot_x != -1)
                    rotX.add(rot_x);
                else
                    rotX.add(o.getRotationX());
            }
            if(rotY != null){
                if(rot_y != -1)
                    rotY.add(rot_y);
                else
                    rotY.add(o.getRotationY());
            }
            if(rotZ != null){
                if(rot_z != -1)
                    rotZ.add(rot_z);
                else
                    rotZ.add(o.getRotationZ());
            }
            if(scaleList != null){
                if(scale != -1)
                    scaleList.add(scale);
                else
                    scaleList.add(o.getScale());
            }
            if(img_name != null)
                img_name.add(imgName);
        }
        
        fc.window.setProperties(ids, names, coordsX, coordsY, coordsZ, 
                rotX, rotY, rotZ, scaleList, img_name);
        setVisible(false);
        
    } 

    protected void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
    } 

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {   
        reset();
    }   
    
    @Override
    public void setVisible(boolean b){
        fc.mainframe.setEnabled(!b);
        if(!b){
            objects.clear();
            fc.setImageSelectionVisible(b);
            image.setImage(null);
            
            locationFieldX.setText("0.00");
            locationFieldY.setText("0.00");
            locationFieldZ.setText("0.00");
            rotationFieldX.setText("0.00");
            rotationFieldY.setText("0.00");
            rotationFieldZ.setText("0.00");
            scaleField.setText("1.00");
        }else{
            tabbedPane.setSelectedIndex(0);
            ArrayList<Long> ids = fc.window.getSelectedIDs();
            if(ids == null)
                return;
            
            for(long id : ids){
                IDataObject object = fc.window.getDataObject(id);
                objects.add(object);
            }
            rightsPane.setObjects();
            setObjects();
            setImage();   
            resetLabelColor();  
        }
        super.setVisible(b);
    }
    
    /**
     * Fills the fields with data.
     */
    private void setObjects(){
        
        initialImg = "";
        
        for(IDataObject object : objects){
        
            locationFieldX.setText(doubleFormat.format(object.getXf()));
            locationFieldY.setText(doubleFormat.format(object.getYf()));
            locationFieldZ.setText(doubleFormat.format(object.getZf()));
            nameField.setText(object.getName());
    
            String rotX = doubleFormat.format(object.getRotationX());
            String rotY = doubleFormat.format(object.getRotationY());
            String rotZ = doubleFormat.format(object.getRotationZ());
            String scale = doubleFormat.format(object.getScale());
            
            //if more items are selected, check if they are different.
            if(!rotationFieldX.getText().equals("0.00") && !rotationFieldX.getText().equals(rotX))
                rotationFieldX.setText(BUNDLE.getString("DifferentValues"));
            else
                rotationFieldX.setText(rotX);
            
            if(!rotationFieldY.getText().equals("0.00") && !rotationFieldY.getText().equals(rotY))
                rotationFieldY.setText(BUNDLE.getString("DifferentValues"));
            else
                rotationFieldY.setText(rotY);
            
            if(!rotationFieldZ.getText().equals("0.00") && !rotationFieldZ.getText().equals(rotZ))
                rotationFieldZ.setText(BUNDLE.getString("DifferentValues"));
            else
                rotationFieldZ.setText(rotZ);
            
            if(!scaleField.getText().equals("1.00") && !scaleField.getText().equals(scale))
                scaleField.setText(BUNDLE.getString("DifferentValues"));
            else
                scaleField.setText(scale);

            if(initialImg != null){
                String imgName = object.getImgClass().getName();
                
                if(initialImg.equals(""))
                    initialImg = imgName;
                else if(initialImg.equals(imgName))
                    initialImg = null;
            }
        }
        
        initialX = locationFieldX.getText();
        initialY = locationFieldY.getText();
        initialZ = locationFieldZ.getText();
        initialRotX = rotationFieldX.getText();
        initialRotY = rotationFieldY.getText();
        initialRotZ = rotationFieldZ.getText();
        initialScale = scaleField.getText();
        initialName = nameField.getText();
        
        //lock specific fields, if there are more than one
        //item selected.
        if(objects.size()>1){
            locationFieldX.setText("");
            locationFieldY.setText("");
            locationFieldZ.setText("");
            nameField.setText("");
            locationFieldX.setEnabled(false);
            locationFieldY.setEnabled(false);
            locationFieldZ.setEnabled(false);
            nameField.setEnabled(false);
        }else{
            locationFieldX.setEnabled(true);
            locationFieldY.setEnabled(true);
            locationFieldZ.setEnabled(true);
            nameField.setEnabled(true);
        }
    }
    
    /**
     * Sets up the image of the image button.
     */
    private void setImage(){
        if(objects.size() <= 0)
            return;

        IImage img = objects.get(0).getImgClass();
        boolean different = false;
        
        //check for different images.
        for(IDataObject object : objects){
            IImage i = object.getImgClass();
            
            if((img == null && i != null) || 
                   (img != null && i == null)){
                different = true;
                break;
            }else if (img != null && i != null){ 
                
                if(!i.getName().equals(img.getName()) ||
                        !i.getDir().equals(img.getDir())){
                    different = true;
                    break;
                }
            } 
        }
        
        if(!different){
            if(img != null){
                imgName = img.getName();
                image.setImage(img.getImage());
            }else{
                image.setImage(null);
            }
        }else{
            image.setText(BUNDLE.getString("DifferentImages"));
        }
        
        image.repaint();
    }
    
    /**
     * Resets the form. Means it sets all field values to zero.
     */
    private void reset(){
        locationFieldX.setText("0.00");
        locationFieldY.setText("0.00");
        locationFieldZ.setText("0.00");
        rotationFieldX.setText("0.00");
        rotationFieldY.setText("0.00");
        rotationFieldZ.setText("0.00");
        scaleField.setText("1.00");
        
        setObjects();
        setImage();
    }
    
    /**
     * Shows an error message.
     * 
     * @param text The text of the message.
     * @param title The title.
     */
    private void showError(String text, String title){
        JOptionPane.showMessageDialog(this,
                text, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Sets the color of one label to the error color (usually red).
     * 
     * @param label The label.
     */
    private void setLabelColorError(JLabel label){
        Font font = label.getFont();
        
        if(normalFont == null)
            normalFont = font;
        
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        label.setFont(boldFont);
        label.setForeground(errorColor);
    }
    
    /**
     * Resets the color for every label to standard (usually black).
     */
    private void resetLabelColor(){
        if(normalFont == null)
            return;
        setLabelColorStandard(nameLabel);
        setLabelColorStandard(xLabel);
        setLabelColorStandard(yLabel);
        setLabelColorStandard(zLabel);
        setLabelColorStandard(rotXLabel);
        setLabelColorStandard(rotYLabel);
        setLabelColorStandard(rotZLabel);
        setLabelColorStandard(scaleLabel);
    }
    
    /**
     * Sets one label to standard color.
     * 
     * @param label The label.
     */
    private void setLabelColorStandard(JLabel label){
        label.setFont(normalFont);
        label.setForeground(normalColor);
    }

    // Variables declaration - do not modify                     
    private ImageButton image;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    private javax.swing.JLabel zLabel;
    private javax.swing.JLabel rotXLabel;
    private javax.swing.JLabel rotYLabel;
    private javax.swing.JLabel rotZLabel;
    private javax.swing.JLabel scaleLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField nameField;
    private javax.swing.JFormattedTextField locationFieldX;
    private javax.swing.JFormattedTextField locationFieldY;
    private javax.swing.JFormattedTextField locationFieldZ;
    private javax.swing.JFormattedTextField rotationFieldX;
    private javax.swing.JFormattedTextField rotationFieldY;
    private javax.swing.JFormattedTextField rotationFieldZ;
    private javax.swing.JFormattedTextField scaleField;
    // End of variables declaration   
}
