package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

/**
 * This class is used for displaying the mouse coordinates on the 
 * bottom toolbar.
 * 
 * @author Patrick
 *
 */
public class MouseCoordinates {

    private WindowController wc = null;

    private DecimalFormat format = null;
    
    public MouseCoordinates(WindowController wc) {
        format = new DecimalFormat("0.000");
        this.wc = wc;
    }

    /**
     * Draws the coordinates on the toolbar.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void paintCoordinates(int x, int y){
        
        if(wc.dm != null){
            Point2D.Double coords = wc.dm.transformCoordsBack(new Point(x,y));
            wc.frame.setToolbarCoords(format.format(coords.x), format.format(coords.y));
        }
    }
}
