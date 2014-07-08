package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * This class represents the tooltip shape, which shows up on hovering
 * over an object.
 * 
 * @author Patrick
 */
public class ToolTip implements IToolTip{
    
    private Point coordinates = null;
    private Rectangle shape = null;
    private boolean firstPaint = true;

    private String text = "";
    private int textX = 0;
    private int textY = 0;
    private int fontSize = 0;
    
    private final int marginMouse = GUISettings.TOOLTIPMARGIN;
    private final int marginText = GUISettings.TOOLTIPIN;
    
    public ToolTip(Point coordinates, String text){
        this.text = text;
        this.coordinates = coordinates;
    }
    
    @Override
    public String getText(){
        return text;
    }
    
    @Override
    public void paint(Graphics2D g, AffineTransform at){
        
        //scale is needed for calculating the inverted scale
        //it has to be inverted in order for the text not to
        //change size, when zoomed in/out.
        double scale = at.getScaleX();
        
        if(firstPaint)
            createRectangle(g);
        
        Shape transformed = at.createTransformedShape(shape);
        transformed = ShapeUtilities.resizeShape(transformed, shape.width, shape.height);
        transformed = ShapeUtilities.translateShape(transformed, marginMouse, marginMouse);
        
        g.setPaint(GUISettings.TOOLTIPBGCOLOR);
        g.fill(transformed);
        
        g.setPaint(GUISettings.TOOLTIPTEXTCOLOR);
        g.draw(transformed);

        textX = (int) (transformed.getBounds().getX() + marginText);
        textY = (int) (transformed.getBounds().getY() + marginText+fontSize);
        
        AffineTransform original =  g.getTransform();
        AffineTransform invert = new AffineTransform();
        double inverted_scale = 1/scale;
        invert.scale(1/scale, 1/scale);

        textX = (int)Math.round(textX/inverted_scale);
        textY = (int)Math.round(textY/inverted_scale);
        
        g.transform(invert);
        g.drawString(text, textX, textY);
        g.setTransform(original);
    }
    
    /**
     * Creates a rectangle according to the coordinates and the
     * text, which has to be displayed.
     * 
     * @param g The graphics2d instance.
     */
    private void createRectangle(Graphics2D g){
        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        fontSize = (int)Math.round(GUISettings.OBJECTNAMESIZE * screenRes / 72.0);

        Font font = new Font(GUISettings.OBJECTNAMEFONTTYPE, Font.PLAIN, fontSize);  
        
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(text, context);

        int width = (int) Math.ceil(bounds.getWidth()+2*marginText);
        int height = (int) Math.round(bounds.getHeight()+2*marginText);
        
        shape = new Rectangle(coordinates.x, coordinates.y,
                width, height );
        
        firstPaint = false;
    }
    
    @Override
    public void setCoordinates(Point p){
        //Do not use the point directly, because it 
        //is used for dragging shapes too.
        int x = p.x;
        int y = p.y;
        shape.setLocation(x,y);
    }

    @Override
    public void set(Point coordinates, String text) {
        this.coordinates.x = coordinates.x;
        this.coordinates.y = coordinates.y;
        this.text = text;
        
        firstPaint = true;
    }

}
