package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectManagerGUIInterface;

public class MouseCoordinates {

    private DataObjectManagerGUIInterface dm = null;
    private BottomToolBar toolBar = null;

    private DecimalFormat format = null;
    
    public MouseCoordinates() {
        format = new DecimalFormat("0.000");
    }

    public void paintCoordinates(int x, int y){
        
        if(dm != null){
            Point2D.Double coords = dm.transformCoordsBack(new Point(x,y));
            toolBar.setCoordinates(RoundDouble(coords.x), RoundDouble(coords.y));
        }
        
    }
    
    public void setToolBar(BottomToolBar toolBar){
        this.toolBar = toolBar;
    }
    
    private String RoundDouble(double val) {
        return format.format(val);
    }

    public void registerDataManager(DataObjectManagerGUIInterface dm) {
        this.dm = dm;
    }
}
