package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.IDataManager;

/**
 * Sets the import state, which is done during the select position
 * of the import frame.
 * 
 * @author Patrick
 *
 */
public class stateImport implements stateInput{
    
    private WindowController wc = null;
    private IDataManager dm = null;
    private int width = 0;
    private int height = 0;
    
    
    public stateImport(WindowController wc,
            IDataManager dm){
        this.wc = wc;
        this.dm = dm;
    }

    @Override
    public void setBounds(int width, int height) {
        this.width = width;
        this.height= height;
    }

    @Override
    public void finished() {
        Point coords = wc.graphic.getDraggingCoords();
        Point2D.Double coords_origin = dm.transformCoordsBack(coords,
                width, height);
        
        wc.frame.setImportLocation(coords_origin.x, coords_origin.y);
        wc.frame.showImportFrame();
    }

}
