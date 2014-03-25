package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.GUIObserverInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToGUI;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.ITransformedObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.AddComponent;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Command;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Delete;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Paste;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.SetName;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.SetProperties;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Rotate;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.RotateTranslate;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Scale;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.ScaleTranslate;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Import;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.SetImage;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.TranslateFloat;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.TranslateXY;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.commands.Vector3D;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.IWindow;

/**
 * This class is used to forward events from the gui to the 
 * adapter. 
 * 
 * @author Patrick
 *
 */
public class AdapterCommunication implements IAdapterCommunication{
    
     private static final Logger LOGGER =
            Logger.getLogger(AdapterCommunication.class.getName());

    private GUIObserverInterface goi = null;
    private IWindow window = null;
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
    
    /**
     * Registers a window instance.
     * 
     * @param window The instance.
     */
    public void registerWindow(IWindow window){
        this.window = window;
    }
    
    @Override
    public void setObjectRemoval(ArrayList<Long> ids){
        Command command = new Delete(ids);
        executeCom(command);
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
        executeCom(command);
    }
    
    @Override
    public void setCopyUpdate(ArrayList<Long> object_ids){
        goi.notifyCopy(object_ids);
    }

    @Override
    public void setPasteUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates) {
        Command command = new Paste(ids, coordinates);
        executeCom(command);
    }

    @Override
    public void setRotationUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates,
            ArrayList<Double> rotation) {
        
        if(ids == null)
            return;
        
        ArrayList<Point> coordinates_old = new ArrayList<Point>();
        ArrayList<Vector3D> rotation_old = new ArrayList<Vector3D>();
        ArrayList<Vector3D> rotation_new = new ArrayList<Vector3D>();
        
        for(int i = 0; i<ids.size();i++){
            long id = ids.get(i);
            
            IDataObject o = dm.getObject(id);
            ITransformedObject t = dm.getTransformedObject(id);
            coordinates_old.add(new Point(t.getX(), t.getY()));
            
            rotation_old.add(new Vector3D(o.getRotationX(), o.getRotationY(),
                    o.getRotationZ()));
            rotation_new.add(new Vector3D(rotation.get(i), o.getRotationY(),
                    o.getRotationZ()));
        }
        
        Command command = new RotateTranslate(ids, coordinates_old, rotation_old, 
                coordinates, rotation_new);
        executeCom(command);
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
        
        Command command = new ScaleTranslate(ids, coordinates_old, scale_old, 
                coordinates, scale);
        executeCom(command);
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
    public boolean importKMZ(String name, String module_name,
            String image_url, double x, double y,
            double z, double rotationX, double rotationY, double rotationZ,
            double scale) {
        
        try {
            long id = goi.importKMZ(name, module_name, image_url, x,y,z, rotationX, 
                    rotationY, rotationZ, scale);
            
            //Import does not need all the baggage in order to undo/redo
            Import imp = new Import();
            imp.setID(id);
            
            executeCom(imp);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
            return false;
        }
    }


    @Override
    public void addRightsComponent(ArrayList<Long> ids) {
        AddComponent com = new AddComponent(ids, GUIObserverInterface.RIGHTSCOMPONENT);
        executeCom(com);
    }
    
    @Override
    public void setProperties(ArrayList<Long> ids, ArrayList<String> names,
            ArrayList<Float> coordsX, ArrayList<Float> coordsY, ArrayList<Float> coordsZ,
            ArrayList<Double> rotX, ArrayList<Double> rotY, ArrayList<Double> rotZ,
            ArrayList<Double> scale, ArrayList<String> imgName){
        
        String userDir = dm.getUserImgDir();

        ArrayList<String> names_old = new ArrayList<String>();
        ArrayList<Vector3D> coords_old = new ArrayList<Vector3D>();
        ArrayList<Vector3D> coords_new = new ArrayList<Vector3D>();
        ArrayList<Vector3D> rot_old = new ArrayList<Vector3D>();
        ArrayList<Vector3D> rot_new = new ArrayList<Vector3D>();
        ArrayList<Double> scale_old = new ArrayList<Double>();
        ArrayList<Double> scale_new = scale;
        ArrayList<String> img_name_old =  new ArrayList<String>();
        ArrayList<String> img_path_old =  new ArrayList<String>();
        ArrayList<String> img_path_new =  new ArrayList<String>();
        
        if(coordsX == null && coordsY == null && coordsZ == null){
            coords_new = null;
        }
        
        if(rotX == null && rotY == null && rotZ == null){
            rot_new = null;
        }
        for(int i=0;i< ids.size();i++){
            long id = ids.get(i);
            IDataObject o = dm.getObject(id);

            float x = o.getXf();            
            float y = o.getYf();            
            float z = o.getZf();
            
            coords_old.add(new Vector3D(x, y, z));
            
            if(names != null)
                names_old.add(o.getName());
            
            if(coords_new != null){
                if(coordsX != null && i<coordsX.size()){
                    x = coordsX.get(i);
                }
                
                if(coordsY != null && i<coordsY.size()){
                    y = coordsY.get(i);
                }
                
                if(coordsZ != null && i<coordsZ.size()){
                    z = coordsZ.get(i);
                }
                coords_new.add(new Vector3D(x,y,z));
            }

            double rot_x = o.getRotationX();            
            double rot_y = o.getRotationY();            
            double rot_z = o.getRotationZ();
            
            rot_old.add(new Vector3D(rot_x, rot_y, rot_z));
            
            if(rot_new != null){
                if(rotX != null && i<rotX.size()){
                    rot_x = rotX.get(i);
                }
                
                if(rotY != null && i<rotY.size()){
                    rot_y = rotY.get(i);
                }
                
                if(rotZ != null && i<rotZ.size()){
                    rot_z = rotZ.get(i);
                }
                rot_new.add(new Vector3D(rot_x,rot_y,rot_z));
            }
            
            if(imgName != null){
                img_name_old.add(o.getImgClass().getName());
                img_path_old.add(o.getImgClass().getDir());
                img_path_new.add(userDir);
            }
            
            double scale_object = o.getScale();
            scale_old.add(scale_object);
        }
        Command translateCom = null;
        if(coords_new != null){
            translateCom = new TranslateFloat(ids, coords_old, coords_new);
        }
        Command rotateCom = null;
        if(rot_new != null){
            rotateCom = new Rotate(ids, rot_old, rot_new);
        }
        Command scaleCom = null;
        if(scale_new != null){
            scaleCom = new Scale(ids, scale_old, scale_new);
        }
        
        Command nameCom = null;
        if(names != null){
            nameCom = new SetName(ids, names_old, names);
        }
        
        Command imgCom = null;
        if(imgName != null){
            imgCom = new SetImage(ids, img_name_old, img_path_old, imgName,
                    img_path_new);
        }
        
        Command command = new SetProperties(nameCom,
                translateCom, rotateCom, scaleCom, imgCom, null);
         
        executeCom(command);
        
    }

    @Override
    public boolean imageExists(String name) {
        return goi.imageFileExists(name);
    }

    @Override
    public void uploadImage(String imgUrl){
        try {
            goi.uploadImage(imgUrl);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Executes a command.
     * 
     * @param command The command to execute.
     */
    private void executeCom(Command command){
        
        undoList.add(command);
        
        try {
            command.execute(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.WARNING, null, ex);
        }
        redoList.clear();
        window.setUndoEnabled(true);
        window.setRedoEnabled(false);
    }

    @Override
    public void undo() {
        if(undoList.isEmpty())
            return;
        
        Command command = undoList.get(undoList.size()-1);
        undoList.remove(undoList.size()-1);
        
        if(undoList.size() <= 0){
            window.setUndoEnabled(false);
        }
        
        redoList.add(command);
        window.setRedoEnabled(true);
        
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

        if(redoList.size() <= 0){
            window.setRedoEnabled(false);
        }
        window.setUndoEnabled(true);
        undoList.add(command);
        try {
            command.redo(goi);
        } catch (Exception ex) {
            Logger.getLogger(AdapterCommunication.class.getName()).log(Level.SEVERE, null, ex);
            redo();
        }
    }
    
    
}
