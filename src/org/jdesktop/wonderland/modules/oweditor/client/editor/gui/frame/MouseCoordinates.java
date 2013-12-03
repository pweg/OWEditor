package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class MouseCoordinates {

    private Point lastPosition = null;
    
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
        
        Font font = new Font("Arial", Font.PLAIN, 12);
        
        g2.setFont(font);
        g2.setPaint(Color.gray);

        g2.drawString("X: " + Math.round(mouse_x) + " Y: " +
                Math.round(mouse_y) +" " + font.getSize(), x,y);
      
        lastPosition = position;
    }
}
