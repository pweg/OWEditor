package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;

public class MouseCoordinates {

    private Point lastPosition = null;
    private Font coordFont = null;
    private Color coordColor = Color.black;
    private CoordinateTranslatorInterface translator = null;
    private DecimalFormat format = null;
    
    public MouseCoordinates(Font coordFont, Color coordColor) {
        this.coordFont = coordFont;
        this.coordColor = coordColor;
        format = new DecimalFormat("0.00000");
    }

    public void paintCoordinates(WindowDrawingPanel panel, 
            Graphics2D g2, AffineTransform at){
        
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
        
        Rectangle view = panel.getVisibleRect();
        
        int x = view.x + 10;
        int y = view.y + view.height-10;
        
        g2.setFont(coordFont);
        g2.setPaint(coordColor);
        
        double real_x = 0;
        double real_y = 0;
        
        if(translator != null){
            real_x = translator.transformXBack(mouse_x);
            real_y = translator.transformXBack(mouse_y);
        }else{
            real_x = mouse_x;
            real_y = mouse_y;
        }

        g2.drawString("X: " + RoundDouble(real_x) + " Y: " +
                RoundDouble(real_y), x,y);
      
        lastPosition = position;
    }

    public void setCoordinateTranslator(CoordinateTranslatorInterface coordinateTranslator) {
        translator = coordinateTranslator;
    }
    
    private String RoundDouble(double val) {
        return format.format(val);
    }
}
