package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

public class ShapeToolTip {
    
    private Point coordinates = null;
    private Rectangle shape = null;
    private boolean firstPaint = true;

    private String content = "";
    private int contentX = 0;
    private int contentY = 0;
    private int fontSize = 0;
    
    public ShapeToolTip(Point coordinates, String content){
        this.content = content;
        this.coordinates = coordinates;
        

    }
    
    public String getContent(){
        return content;
    }
    
    public void paint(Graphics2D g, AffineTransform at, double scale){
        if(firstPaint)
            createRectangle(g, at, scale);
        

        g.setPaint(GUISettings.TOOLTIPBGCOLOR);
        g.fill(shape);
        
        g.setPaint(GUISettings.TOOLTIPTEXTCOLOR);
        g.draw(shape);
        

        contentX = (int) (shape.getX() + Math.round(GUISettings.TOOLTIPIN*scale));
        contentY = (int) (shape.getY() + Math.round(GUISettings.TOOLTIPIN*scale)+fontSize);
        
        g.drawString(content, contentX, contentY);
        
    }
    
    private void createRectangle(Graphics2D g, AffineTransform at, double scale){


        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        fontSize = (int)Math.round(GUISettings.OBJECTNAMESIZE*scale * screenRes / 72.0);

        Font font = new Font(GUISettings.OBJECTNAMEFONTTYPE, Font.PLAIN, fontSize);  
        
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(content, context);

        int width = (int) Math.round(bounds.getWidth()+2*scale*GUISettings.TOOLTIPIN);
        int height = (int) Math.round(bounds.getHeight()+2*scale*GUISettings.TOOLTIPIN);
        System.out.println(height + " " +bounds.getHeight() + " " );
        
        shape = new Rectangle(coordinates.x, coordinates.y, width, height );
        
        firstPaint = false;
    }
    
    public void setCoordinates(Point p){
        shape.setLocation(p);
    }

    public void set(Point coordinates, String content) {
        this.coordinates = coordinates;
        this.content = content;
        
        firstPaint = true;
        
    }

}
