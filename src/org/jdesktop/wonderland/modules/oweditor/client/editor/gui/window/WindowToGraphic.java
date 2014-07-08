package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IDataManager;

/**
 * The interface implementation for the graphic package.
 * 
 * @author Patrick
 *
 */
public class WindowToGraphic implements IWindowToGraphic{
    
    private IDataManager dm = null;
    private IAdapterCommunication adapter = null;
    private WindowController wc = null;
    
    public WindowToGraphic(WindowController wc, IAdapterCommunication adapter){
        this.adapter = adapter;
        this.wc = wc;
    }

    public void registerDataManager(IDataManager dm) {
        this.dm = dm;
    }

    @Override
    public void repaint() {
        wc.repaint();
    }

    @Override
    public void setObjectRemoval(ArrayList<Long> ids) {
        adapter.setObjectRemoval(ids);
    }

    @Override
    public void setTranslationUpdate(ArrayList<Long> ids, 
            ArrayList<Point> coordinates) {
        adapter.setTranslationUpdate(ids, coordinates);
    }

    @Override
    public void setCopyUpdate(ArrayList<Long> object_ids) {
        adapter.setCopyUpdate(object_ids);
    }

    @Override
    public void setPasteUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates) {
        adapter.setPasteUpdate(ids, coordinates);
    }

    @Override
    public void setRotationUpdate(ArrayList<Long> ids, 
            ArrayList<Point> coordinates, 
            ArrayList<Double> rotation) {
        adapter.setRotationUpdate(ids, coordinates, rotation);
    }

    @Override
    public void setScaleUpdate(ArrayList<Long> ids, 
            ArrayList<Point> coordinates, 
            ArrayList<Double> scale) {
        adapter.setScaleUpdate(ids, coordinates, scale);
    }

    @Override
    public BufferedImage getImage(String name, String dir) {
        return dm.getImage(name, dir);
    }
}
