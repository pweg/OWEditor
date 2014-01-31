package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.TransformedObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Command;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Paste;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Rotate;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Scale;
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
    private DataObjectManagerGUIInterface dm;
    
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
    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        this.dm = dm;
    }
    
    /**
     * Notifies the Adapter for a removal of an
     * object.
     * 
     * @param id the id of the object, that needs to 
     * be removed.
     */
    @Override
    public void setObjectRemoval(long id){
        goi.notifyRemoval(id);
    }
    
    /**
    * Sets an translation update for the adapter
    * 
    * @param id the id of the object that should be updated.
    * @param x the new x coordinate.
    * @param y the new y coordinate.
    * @param z the new z coordinate.
    */
    @Override
    public void setTranslationUpdate(long id, int x, int y) {
        TransformedObjectInterface o = dm.getTransformedObject(id);
        Command command = new TranslateXY(id, o.getX(), o.getY(), x, y);
        undoList.add(command);
        command.execute(goi);
        redoList.clear();
    }
    
    @Override
    public void setCopyUpdate(ArrayList<Long> object_ids){
        goi.notifyCopy(object_ids);
    }

    @Override
    public void setPasteUpdate(long id, int x, int y) {
        Command command = new Paste(id, x, y);
        undoList.add(command);
        command.execute(goi);
        redoList.clear();
    }

    @Override
    public void setRotationUpdate(long id, int x, int y, double rotation) {
        TransformedObjectInterface o = dm.getTransformedObject(id);
        Command command = new Rotate(id, o.getX(), o.getY(), o.getRotation(), 
                x, y, rotation);
        undoList.add(command);
        command.execute(goi);
        redoList.clear();
    }

    @Override
    public void setScaleUpdate(long id, int x, int y, double scale) {
        TransformedObjectInterface o = dm.getTransformedObject(id);
        Command command = new Scale(id, o.getX(), o.getY(), o.getScale(), 
                x, y, scale);
        undoList.add(command);
        command.execute(goi);
        redoList.clear();
    }

    @Override
    public int[] loadKMZ(String url) {
        return goi.loadKMZ(url);
    }

    @Override
    public long importKMZ(String name, String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) {
        return goi.importKMZ(name, image_url, x,y,z, rotationX, 
                rotationY, rotationZ, scale);
    }

    @Override
    public void copyKMZ(long id, String image_url, double x, double y,
            double z, double rot_x, double rot_y, double rot_z, double scale) {
        goi.notifyCopy(id, image_url, x, y, z,
                rot_x,rot_y,rot_z,scale);
        
    }

    @Override
    public void overwriteKMZ(long id, String image_url, double x, double y,
            double z, double rot_x, double rot_y, double rot_z, double scale) {
        goi.notifyOverwrite(id, image_url, x, y, z,
                rot_x,rot_y,rot_z,scale);
    }

    @Override
    public void undo() {
        if(undoList.isEmpty())
            return;
        
        Command command = undoList.get(undoList.size()-1);
        undoList.remove(undoList.size()-1);
        command.undo(goi);
        redoList.add(command);
    }

    @Override
    public void redo() {
        if(redoList.isEmpty())
            return;

        Command command = redoList.get(redoList.size()-1);
        redoList.remove(redoList.size()-1);
        command.execute(goi);
        undoList.add(command);
    }
}
