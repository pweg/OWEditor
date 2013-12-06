package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Point;
import java.text.DecimalFormat;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;

public class MouseCoordinates {

    private CoordinateTranslatorInterface translator = null;
    private BottomToolBar toolBar = null;

    private Point lastPosition = null;
    private DecimalFormat format = null;
    
    public MouseCoordinates() {
        format = new DecimalFormat("0.000");
    }

    public void paintCoordinates(WindowDrawingPanel panel){
        
        Point position = panel.getMousePosition();
        if(position == null)
        {
            if(lastPosition == null)
                return;
            else
                position = lastPosition;
        }
        double translation_x = panel.getTranslationX();
        double translation_y = panel.getTranslationY();
        double scale = panel.getScale();
        
        double mouse_x = (position.x/scale - translation_x);
        double mouse_y = (position.y/scale - translation_y);
        
        double real_x = 0;
        double real_y = 0;
        
        if(translator != null){
            real_x = translator.transformXBack(mouse_x);
            real_y = translator.transformXBack(mouse_y);
        }else{
            real_x = mouse_x;
            real_y = mouse_y;
        }
        
        toolBar.setCoordinates(RoundDouble(real_x), RoundDouble(real_y));
        lastPosition = position;
    }

    public void setCoordinateTranslator(CoordinateTranslatorInterface coordinateTranslator) {
        translator = coordinateTranslator;
    }
    
    public void setToolBar(BottomToolBar toolBar){
        this.toolBar = toolBar;
    }
    
    private String RoundDouble(double val) {
        return format.format(val);
    }
}
