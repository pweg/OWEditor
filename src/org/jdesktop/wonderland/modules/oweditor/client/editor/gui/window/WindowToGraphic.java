package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IAdapterCommunication;


public class WindowToGraphic implements IWindowToGraphic{
    

    private DrawingPanel drawingPan = null;
    private IAdapterCommunication adapter = null;
    
    public WindowToGraphic(DrawingPanel drawingPan, IAdapterCommunication adapter){
        this.drawingPan = drawingPan;
        this.adapter = adapter;
    }

    @Override
    public void repaint() {
        drawingPan.repaint();
    }

    @Override
    public void setObjectRemoval(long id) {
        adapter.setObjectRemoval(id);
    }

    @Override
    public void setTranslationUpdate(long id, int x, int y) {
        adapter.setTranslationUpdate(id, x, y);
    }

    @Override
    public void setCopyUpdate(ArrayList<Long> object_ids) {
        adapter.setCopyUpdate(object_ids);
    }

    @Override
    public void setPasteUpdate(long id, int x, int y) {
        adapter.setPasteUpdate(id, x, y);
    }

    @Override
    public void setRotationUpdate(long id, int x, int y, double rotation) {
        adapter.setRotationUpdate(id, x, y, rotation);
    }

    @Override
    public void setScaleUpdate(long id, int x, int y, double scale) {
        adapter.setScaleUpdate(id, x, y, scale);
    }
}
