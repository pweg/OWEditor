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
    
    private int marginMouse = GUISettings.TOOLTIPMARGIN;
    private int marginText = GUISettings.TOOLTIPIN;
    
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
        

        contentX = (int) (shape.getX() + marginText);
        contentY = (int) (shape.getY() + marginText+fontSize);
        
        AffineTransform original =  g.getTransform();
        AffineTransform invert = new AffineTransform();
        double inverted_scale = 1/scale;
        invert.scale(1/scale, 1/scale);

        contentX = (int)Math.round(contentX/inverted_scale);
        contentY = (int)Math.round(contentY/inverted_scale);
        
        g.transform(invert);
        g.drawString(content, contentX, contentY);
        g.setTransform(original);
        
    }
    
    private void createRectangle(Graphics2D g, AffineTransform at, double scale){


        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        fontSize = (int)Math.round(GUISettings.OBJECTNAMESIZE * screenRes / 72.0);

        Font font = new Font(GUISettings.OBJECTNAMEFONTTYPE, Font.PLAIN, fontSize);  
        
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(content, context);

        int width = (int) Math.ceil(bounds.getWidth()+2*marginText);
        int height = (int) Math.round(bounds.getHeight()+2*marginText);
        
        shape = new Rectangle(coordinates.x+marginMouse, coordinates.y+marginMouse,
                width, height );
        
        firstPaint = false;
    }
    
    public void setCoordinates(Point p){
        p.x = p.x + marginMouse;
        p.y = p.y + marginMouse;
        shape.setLocation(p);
    }

    public void set(Point coordinates, String content) {
        this.coordinates.x = coordinates.x+marginMouse;
        this.coordinates.y = coordinates.y+marginMouse;
        this.content = content;
        
        firstPaint = true;
    }

}
