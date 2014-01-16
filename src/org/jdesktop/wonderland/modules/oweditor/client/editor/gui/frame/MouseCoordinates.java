package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.text.DecimalFormat;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;

public class MouseCoordinates {

    private CoordinateTranslatorInterface translator = null;
    private BottomToolBar toolBar = null;

    private DecimalFormat format = null;
    
    public MouseCoordinates() {
        format = new DecimalFormat("0.000");
    }

    public void paintCoordinates(int x, int y){
        
        double real_x = 0.0;
        double real_y = 0.0;
        
        if(translator != null){
            real_x = translator.transformXBack(x);
            real_y = translator.transformXBack(y);
        }
        
        toolBar.setCoordinates(RoundDouble(real_x), RoundDouble(real_y));
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
