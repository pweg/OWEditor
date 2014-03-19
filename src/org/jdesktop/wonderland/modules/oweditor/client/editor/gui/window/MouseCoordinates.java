package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataToGUI;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.frames.toolbar.BottomToolBar;

/**
 * This class is used for displaying the mouse coordinates on the 
 * bottom toolbar.
 * 
 * @author Patrick
 *
 */
public class MouseCoordinates {

    private IDataToGUI dm = null;
    private BottomToolBar toolBar = null;

    private DecimalFormat format = null;
    
    public MouseCoordinates() {
        format = new DecimalFormat("0.000");
    }

    /**
     * Draws the coordinates on the toolbar.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void paintCoordinates(int x, int y){
        
        if(dm != null){
            Point2D.Double coords = dm.transformCoordsBack(new Point(x,y));
            toolBar.setCoordinates(format.format(coords.x), format.format(coords.y));
        }
    }
    
    /**
     * Sets the tool bar.
     * 
     * @param toolBar The toolbar instance.
     */
    public void setToolBar(BottomToolBar toolBar){
        this.toolBar = toolBar;
    }

    /**
     * Registers the data manager. It is needed
     * to revert the coordinates back to their origin.
     * 
     * @param dm The data manager.
     */
    public void registerDataManager(IDataToGUI dm) {
        this.dm = dm;
    }
}
