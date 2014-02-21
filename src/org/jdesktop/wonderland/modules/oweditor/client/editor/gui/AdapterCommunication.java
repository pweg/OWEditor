package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToGUI;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Command;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Delete;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Paste;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Rotate;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Scale;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Import;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.TranslateXY;

/**
 * This class is used to forward events from the gui to the 
 * adapter. 
 * 
 * @author Patrick
 *
 */
public class AdapterCommunication implements IAdapterCommunication{

    private GUIObserverInterface goi = null;
    private ArrayList<Command> undoList = null;
    private ArrayList<Command> redoList = null;
    private IDataToGUI dm;
    
    public AdapterCommunication(){
        undoList = new ArrayList<Command>();
        redoList = new ArrayList<Command>();
    }
    
    /**
     * Registers a GUIObserverInterface instance to this
     * class.
     * 
     * @param goi the observer instance.
     */
    public void registerObserver(GUIObserverInterface goi){
        this.goi = goi;
    }

    /**
     * Registers a data manager interface.
     * 
     * @param dm The data manger instance.
     */
    public void registerDataManager(IDataToGUI dm) {
        this.dm = dm;
    }
    
    @Override
    public void setObjectRemoval(ArrayList<Long> ids){
        Command command = new Delete(ids);
        undoList.add(command);
        try {
            command.execute(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
        }
        redoList.clear();
    }
    
    @Override
    public void setTranslationUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates) {
        
        if(ids == null)
            return;
        
        ArrayList<Point> coordinates_old = new ArrayList<Point>();
        
        for(long id : ids){
            ITransformedObject o = dm.getTransformedObject(id);
            coordinates_old.add(new Point(o.getX(), o.getY()));
        }
        
        Command command = new TranslateXY(ids, coordinates_old, coordinates);
        undoList.add(command);
        try {
            command.execute(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
        }
        redoList.clear();
    }
    
    @Override
    public void setCopyUpdate(ArrayList<Long> object_ids){
        goi.notifyCopy(object_ids);
    }

    @Override
    public void setPasteUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates) {
        Command command = new Paste(ids, coordinates);
        undoList.add(command);
        try {
            command.execute(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
        }
        redoList.clear();
    }

    @Override
    public void setRotationUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates,
            ArrayList<Double> rotation) {
        
        if(ids == null)
            return;
        
        ArrayList<Point> coordinates_old = new ArrayList<Point>();
        ArrayList<Double> rotation_old = new ArrayList<Double>();
        
        for(long id : ids){
            ITransformedObject o = dm.getTransformedObject(id);
            coordinates_old.add(new Point(o.getX(), o.getY()));
            rotation_old.add(o.getRotation());
        }
        
        Command command = new Rotate(ids, coordinates_old, rotation_old, 
                coordinates, rotation);
        undoList.add(command);
        try {
            command.execute(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
        }
        redoList.clear();
    }

    @Override
    public void setScaleUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates,
            ArrayList<Double> scale) {
        
        if(ids == null)
            return;
        
        ArrayList<Point> coordinates_old = new ArrayList<Point>();
        ArrayList<Double> scale_old = new ArrayList<Double>();
        
        for(long id : ids){
            ITransformedObject o = dm.getTransformedObject(id);
            coordinates_old.add(new Point(o.getX(), o.getY()));
            scale_old.add(o.getScale());
        }
        
        Command command = new Scale(ids, coordinates_old, scale_old, 
                coordinates, scale);
        undoList.add(command);
        try {
            command.execute(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
        }
        redoList.clear();
    }

    @Override
    public int[] loadKMZ(String url) {
        try {
            return goi.loadKMZ(url);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
            return null;
        }
    }
    
    @Override
    public boolean importCheckName(String moduleName, String server){
        try {
            return goi.importCheckName(moduleName, server);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void cancelImport() {
        goi.cancelImport();
    }

    @Override
    public boolean importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) {
        
        try {
            long id = goi.importKMZ(name, image_url, x,y,z, rotationX, 
                    rotationY, rotationZ, scale);
            
            //Import does not need all the baggage in order to undo/redo
            Import imp = new Import();
            imp.setID(id);
            
            undoList.add(imp);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
            return false;
        }
    }

    /*
    @Override
    public void importConflictCopy(long id) {
        undoList.add(new Import());
        goi.importConflictCopy(id);
        
    }

    @Override
    public void importConflictOverwrite() {
        undoList.add(new Import());
        goi.importConflictOverwrite(id);
    }*/

    @Override
    public void undo() {
        if(undoList.isEmpty())
            return;
        
        Command command = undoList.get(undoList.size()-1);
        undoList.remove(undoList.size()-1);
        redoList.add(command);
        try {
            command.undo(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
            undo();
        }
    }

    @Override
    public void redo() {
        if(redoList.isEmpty())
            return;

        Command command = redoList.get(redoList.size()-1);
        redoList.remove(redoList.size()-1);
        undoList.add(command);
        try {
            command.redo(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.SEVERE, null, ex);
            redo();
        }
    }
}
