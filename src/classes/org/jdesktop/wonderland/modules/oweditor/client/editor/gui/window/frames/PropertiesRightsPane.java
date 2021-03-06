/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IRights;

/**
 *
 * @author Patrick
 */
public class PropertiesRightsPane extends JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 12;
    private PropertiesFrame frame = null;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");

    /**
     * Creates new form NewJPanel
     * @param frame The properties frame.
     */
    public PropertiesRightsPane(PropertiesFrame frame) {
        initComponents();
        this.frame = frame;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        permTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        addCompButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        applyButton = new javax.swing.JButton();
        permTable.setModel(permTableModel);
        permTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jScrollPane1 = new javax.swing.JScrollPane(permTable,  
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        addButton.setText(BUNDLE.getString("Add"));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        addCompButton.setText(BUNDLE.getString("AddComponent"));
        addCompButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addComponentActionPerformed(evt);
            }
        });

        removeButton.setText(BUNDLE.getString("Remove"));
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        
        okButton.setText(BUNDLE.getString("OK"));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt, true);
            }
        });

        cancelButton.setText(BUNDLE.getString("Cancel"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        
        applyButton.setText(BUNDLE.getString("Apply"));
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt, false);
            }
        });
        
        resetButton.setText(BUNDLE.getString("Reset"));
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 143, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(addCompButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                               .addComponent(applyButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(okButton)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(removeButton)
                        .addComponent(addButton)
                        .addComponent(addCompButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(okButton)
                        .addComponent(cancelButton)
                        .addComponent(resetButton)
                        .addComponent(applyButton))
                    .addContainerGap())
            );

        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addItem(BUNDLE.getString("User"));
        comboBox.addItem(BUNDLE.getString("Group"));

        permTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(comboBox));
    }// </editor-fold>                        

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        permTableModel.addEntry(BUNDLE.getString("User"), "", false, false, 
                false, true, true, true, false);
    } 
    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int curRow = permTable.getSelectedRow();
        permTableModel.removeEntry(curRow);
    }                                        

    private void addComponentActionPerformed(java.awt.event.ActionEvent evt) {  
        ArrayList<Long> ids = new ArrayList<Long>();
        
        for(IDataObject object : frame.objects)
            ids.add(object.getID());
        frame.fc.window.addRightsComponent(ids);
    } 
    
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt, boolean close) {                                         
        frame.okButtonActionPerformed(evt,close);
    } 
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        frame.cancelButtonActionPerformed(evt);
    } 
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        setObjects();
    } 
    
    /**
     * Returns all changed entries.
     * 
     * @return A list of all changed entries.
     */
    public ArrayList<PermissionTableEntry> getChanged() {
        
        Set<IRights> allRights = new HashSet<IRights>();
        
        for(IDataObject object : frame.objects){
            ArrayList<IRights> rights = object.getRights();
            
            if(rights != null){
                
                //set does seem to have duplicates, therefore
                //removing it the old way.
                for(IRights right : rights){
                    boolean found = false;
                    for(IRights r : allRights)
                        if(r.equals(right)){
                            found = true;
                            break;
                        }
                        
                    if(!found)    
                        allRights.add(right);
                }
            }
        }
        ArrayList<PermissionTableEntry> checkList = 
                new ArrayList<PermissionTableEntry>();
        
        //remove rights that are not in every item.
        for(IRights rights : allRights){
            int count = frame.objects.size();
            
            for(IDataObject object : frame.objects){
                ArrayList<IRights> orights = object.getRights();
                if(orights != null){
                    for(IRights oright : object.getRights()){
                        if(rights.equals(oright)){
                            count--;
                            break;
                        }
                    }
                }
            }
            if(count == 0){
                checkList.add(new PermissionTableEntry(rights.getType(), 
                        rights.getName(), rights.getOwner(), 
                        rights.getPermAddSubObjects(), rights.getPermChangeAbilities(),
                        rights.getPermMove(), rights.getPermView(), rights.isEditable(),
                        rights.isEverybody()));
            }
        }
        
        for(int i = 0; i<checkList.size(); i++){
            PermissionTableEntry right = checkList.get(i);
            boolean match = false;
            boolean found = false;
            for(PermissionTableEntry right2 : permTableModel.getEntries()){
                if(right.equals(right2)){
                    match = true;
                    break;
                }else if(right.oldName.equals(right2.oldName) &&
                        right.oldType.equals(right2.oldType)){
                    found = true;
                    right = right2;
                    break;
                }
            }
            if(match){
                checkList.remove(i);
                i--;
            }else if(!found){
                right.toRemove = true;
            }else if(found){
                checkList.remove(i);
                checkList.add(i, right);
            }
            
        }
        checkList.addAll(permTableModel.getNewEntries());
        return checkList;
    }

    /**
     * Sets up the permission table.
     */
    public void setObjects() {
        permTableModel.clear();
        
        int countRights = 0;
        
        Set<IRights> allRights = new HashSet<IRights>();
        
        for(IDataObject object : frame.objects){
            ArrayList<IRights> rights = object.getRights();
            
            if(rights != null){
                countRights++;
                
                //set does seem to have duplicates, therefore
                //removing it the old way.
                for(IRights right : rights){
                    boolean found = false;
                    for(IRights r : allRights)
                        if(r.equals(right)){
                            found = true;
                            break;
                        }
                        
                    if(!found)    
                        allRights.add(right);
                }
            }
        }
        
        //set buttons according to rights component present or not.
        if(countRights == frame.objects.size()){
            addButton.setEnabled(true);
            removeButton.setEnabled(true);
            addCompButton.setEnabled(false);
        }else{
            addButton.setEnabled(false);
            removeButton.setEnabled(false);
            addCompButton.setEnabled(true);
        }
        
        //remove rights that are not in every item.
        for(IRights rights : allRights){
            int count = frame.objects.size();
            
            for(IDataObject object : frame.objects){
                ArrayList<IRights> orights = object.getRights();
                if(orights != null){
                    for(IRights oright : object.getRights()){
                        if(rights.equals(oright)){
                            count--;
                            break;
                        }
                    }
                }
            }
            if(count == 0){
                permTableModel.addEntry(rights.getType(), 
                        rights.getName(), rights.getOwner(), 
                        rights.getPermAddSubObjects(), rights.getPermChangeAbilities(),
                        rights.getPermMove(), rights.getPermView(), rights.isEditable(),
                        rights.isEverybody());
            }
        }
        //just to remove the isNew boolean for these entries.
        permTableModel.getNewEntries();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton addButton;  
    private javax.swing.JButton applyButton;               
    private javax.swing.JButton removeButton;
    private javax.swing.JButton addCompButton;              
    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable permTable;
    private PermissionTable permTableModel = new PermissionTable();
    // End of variables declaration  
    
}
