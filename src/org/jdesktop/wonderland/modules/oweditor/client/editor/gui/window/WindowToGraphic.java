package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Point;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;


public class WindowToGraphic implements IWindowToGraphic{
    

    private IAdapterCommunication adapter = null;
    private WindowController wc = null;
    
    public WindowToGraphic(WindowController wc, IAdapterCommunication adapter){
        this.adapter = adapter;
        this.wc = wc;
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
}
