package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToGUI;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IImage;

/**
 * This class is used to retrieve data from the data package.
 * 
 * @author Patrick
 *
 */
public class DataManager implements IDataManager{
    
    IDataToGUI dm = null;
    
    public DataManager(){
    }
    
    public void registerDataInterface(IDataToGUI dm){
        this.dm = dm;
    }
    
    @Override
    public BufferedImage getImage(String name, String dir) {
        return dm.getImage(name, dir);
    }
    
    @Override
    public String[] getServerList() {
        return dm.getServerList();
    }
    
    @Override
    public ArrayList<IImage> getImgLib() {
        return dm.getImgLibrary();
    }
    
    @Override
    public IDataObject getDataObject(long id) {
        return dm.getObject(id);
    }
    
    @Override
    public Point2D.Double transformCoordsBack(Point p){
        return dm.transformCoordsBack(p);
    }
    
    @Override
    public Point2D.Double transformCoordsBack(Point coords, 
            int width, int height){
        return dm.transformCoordsBack(coords,
                width, height);
    }

    @Override
    public String getUserName() {
        return dm.getUserName();
    }

}
