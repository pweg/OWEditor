package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Sets the import state, which is done during the select position
 * of the import frame.
 * 
 * @author Patrick
 *
 */
public class stateImport implements stateInput{
    
    private WindowController wc = null;
    private int width = 0;
    private int height = 0;
    
    
    public stateImport(WindowController wc){
        this.wc = wc;
    }

    @Override
    public void setBounds(int width, int height) {
        this.width = width;
        this.height= height;
    }

    @Override
    public void finished() {
        Point coords = wc.graphic.getDraggingCoords();
        Point2D.Double coords_origin = wc.dm.transformCoordsBack(coords,
                width, height);
        
        wc.frame.setImportLocation(coords_origin.x, coords_origin.y);
        wc.frame.showImportFrame();
    }

}
