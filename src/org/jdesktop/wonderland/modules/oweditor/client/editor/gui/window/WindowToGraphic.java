package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The interface implementation for the graphic package.
 * 
 * @author Patrick
 *
 */
public class WindowToGraphic implements IWindowToGraphic{
    
    private WindowController wc = null;
    
    public WindowToGraphic(WindowController wc){
        this.wc = wc;
    }
    
    @Override
    public void repaint() {
        wc.repaint();
    }

    @Override
    public void setObjectRemoval(ArrayList<Long> ids) {
        wc.adapter.setObjectRemoval(ids);
    }

    @Override
    public void setTranslationUpdate(ArrayList<Long> ids, 
            ArrayList<Point> coordinates) {
        wc.adapter.setTranslationUpdate(ids, coordinates);
    }

    @Override
    public void setCopyUpdate(ArrayList<Long> object_ids) {
        wc.adapter.setCopyUpdate(object_ids);
    }

    @Override
    public void setPasteUpdate(ArrayList<Long> ids, ArrayList<Point> coordinates) {
        wc.adapter.setPasteUpdate(ids, coordinates);
    }

    @Override
    public void setRotationUpdate(ArrayList<Long> ids, 
            ArrayList<Point> coordinates, 
            ArrayList<Double> rotation) {
        wc.adapter.setRotationUpdate(ids, coordinates, rotation);
    }

    @Override
    public void setScaleUpdate(ArrayList<Long> ids, 
            ArrayList<Point> coordinates, 
            ArrayList<Double> scale) {
        wc.adapter.setScaleUpdate(ids, coordinates, scale);
    }

    @Override
    public BufferedImage getImage(String name, String dir) {
        return wc.dm.getImage(name, dir);
    }
}
