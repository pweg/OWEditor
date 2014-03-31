package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

public class PermissionTable extends AbstractTableModel{

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org/jdesktop/wonderland/modules/oweditor/client/resources/Bundle");
    private ArrayList<PermissionTableEntry> rightsList;
    
    public PermissionTable(){
        rightsList = new ArrayList<PermissionTableEntry>();
    }
        
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            case 3:
                return Boolean.class;
            case 4:
                return Boolean.class;
            case 5:
                return Boolean.class;
            case 6:
                return Boolean.class;
            default:
                throw new IllegalStateException("Unknown column " + column);
        }
    }
    
    public ArrayList<PermissionTableEntry> getEntries(){
        return rightsList;
    }
    
    public void addEntry(String type, String name, boolean owner, 
            boolean addSubObjects, boolean changeAbilities,
            boolean move, boolean view, boolean editable, boolean
            isEverybody){
        
        
        PermissionTableEntry entry = new PermissionTableEntry(type,
                name, owner, addSubObjects, changeAbilities,
                move, view, editable, isEverybody);
        entry.isNew = true;
        rightsList.add(entry);
        
        this.fireTableRowsInserted(rightsList.size() - 1,
                rightsList.size() - 1);
    }
    
    public ArrayList<PermissionTableEntry> getNewEntries(){
        ArrayList<PermissionTableEntry> table = 
                new ArrayList<PermissionTableEntry>();
        
        for(PermissionTableEntry entry : rightsList){
            if(entry.isNew){
                table.add(entry);
                entry.isNew = false;
            }
        }
        
        return table;
    }
    
    public boolean isEntry(String type, String name){
        for(PermissionTableEntry entry : rightsList){
            if(entry.name.equals(name) && entry.type.equals(type))
                return true;
        }
        return false;
    }
    
    public void removeEntry(int index){
        
        if(index == -1)
            return;
        
        if(!rightsList.get(index).isEditable ||
                rightsList.get(index).isEverybody)
            return;
        
        rightsList.remove(index);
        this.fireTableRowsDeleted(index, index);
    }
    
    public void clear() {
        int size = rightsList.size();

        rightsList.clear();

        fireTableRowsDeleted(0, size);
        fireTableDataChanged();
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return BUNDLE.getString("RightsType");
            case 1:
                return BUNDLE.getString("RightsName");
            case 2:
                return BUNDLE.getString("RightsOwner");
            case 3:
                return BUNDLE.getString("RightsPermSubObject");
            case 4:
                return BUNDLE.getString("RightsPermAbilities");
            case 5:
                return BUNDLE.getString("RightsPermMove");
            case 6:
                return BUNDLE.getString("RightsPermView");
            default:
                throw new IllegalStateException("Unknown column " + column);
        }
    }
        
    @Override
    public int getRowCount() {
        return rightsList.size();
    }
    
    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(rowIndex >= rightsList.size())
            throw new IllegalStateException("Unknown row " + rowIndex);
        
        PermissionTableEntry entry = (PermissionTableEntry)rightsList.get(rowIndex);
        if(entry.isEverybody && entry.isEditable){
            if(columnIndex == 0 || columnIndex == 1)
                return false;
            else
                return true;
        }else if(columnIndex >2){
            return entry.isEditable;
        }
        return true;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex>=rightsList.size())
            throw new IllegalStateException("Unknown row " + rowIndex);
        
        PermissionTableEntry entry = (PermissionTableEntry)rightsList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return entry.type;
            case 1:
                return entry.name;
            case 2:
                return entry.owner;
            case 3:
                return entry.addSubObjects;
            case 4:
                return entry.changeAbilities;
            case 5:
                return entry.move;
            case 6:
                return entry.view;
            default:
                throw new IllegalStateException("Unknown column " + columnIndex);
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        
        if(rowIndex>= rightsList.size())
            throw new IllegalStateException("Unknown row " + rowIndex);
        
        PermissionTableEntry entry = (PermissionTableEntry)rightsList.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                if(isEntry((String) value, (String) getValueAt(rowIndex,1))){
                    setValueAt("",rowIndex, 1);
                }
                entry.type = (String) value;
                fireTableCellUpdated(rowIndex, columnIndex);
                return;
            case 1:
                if(isEntry((String) getValueAt(rowIndex,0), (String) value)){
                    return;
                }
                entry.name = (String) value;
                fireTableCellUpdated(rowIndex, columnIndex);
                return;
            case 2:
                entry.owner = (Boolean) value;
                entry.isEditable = !(Boolean) value;
                fireTableCellUpdated(rowIndex, columnIndex);
                return;
            case 3:
                entry.addSubObjects = (Boolean) value;
                fireTableCellUpdated(rowIndex, columnIndex);
                return;
            case 4:
                entry.changeAbilities = (Boolean) value;
                fireTableCellUpdated(rowIndex, columnIndex);
                return;
            case 5:
                entry.move = (Boolean) value;
                fireTableCellUpdated(rowIndex, columnIndex);
                return;
            case 6:
                entry.view = (Boolean) value;
                fireTableCellUpdated(rowIndex, columnIndex);
                return;
            default:
                throw new IllegalStateException("Column " + columnIndex +
                        " not editable.");
        }
    }
}
