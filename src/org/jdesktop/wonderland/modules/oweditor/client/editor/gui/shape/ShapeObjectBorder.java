package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

public class ShapeObjectBorder extends SimpleShapeObject{
    
    public static final byte MODENOCENTER   = 0;
    public static final byte MODECENTER     = 1;
    
    public static final byte INNOTHING           = 0;
    public static final byte INROTATIONCENTER    = 1;
    public static final byte INEDGES             = 2;

    private Rectangle originalShape = null;
    private Rectangle rotationCenter = null;
    
    private Shape transformedShape = null;
    private Shape transformedCenter = null;
    
    ArrayList<Rectangle> tinyShapes = null;
    ArrayList<Shape> transformedTinyShapes = null;
    ArrayList<Double> angles = null;
    private Shape currentClicked = null;
    
    private Paint color = GUISettings.surroundingBorderColor;
    
    private int tinySize = GUISettings.surroundingBorderTinyShapeSize;
    private int margin = GUISettings.surroundingBorderMargin;
    private int tinySizeHalf = 0;
    private byte mode = 0;
    
    private double  rotation = 0;
    
    public ShapeObjectBorder(int x, int y, int width, int height, byte mode){
        tinySizeHalf = (int) Math.round(tinySize/2);

        tinyShapes = new ArrayList<Rectangle>();
        transformedTinyShapes = new ArrayList<Shape>();

        x = x-margin;
        y = y-margin;
        originalShape = new Rectangle (x, y, width+2*margin, height+2*margin);
        
        
        tinyShapes.add(new Rectangle(x-tinySizeHalf,y-tinySizeHalf,tinySize,tinySize));
        tinyShapes.add(new Rectangle(x+width+margin+tinySizeHalf,y-tinySizeHalf,
                tinySize,tinySize));
        tinyShapes.add(new Rectangle(x-tinySizeHalf,y+height+margin
                +tinySizeHalf,tinySize,tinySize));
        tinyShapes.add(new Rectangle(x+width+margin+tinySizeHalf,
                y+height+margin+tinySizeHalf,tinySize,tinySize));

        int center_x = (int) Math.round(x+(width/2));
        int center_y = (int) Math.round(y+(height/2));
        
        rotationCenter = new Rectangle(center_x+tinySizeHalf,
                center_y+tinySizeHalf,tinySize,tinySize);       
        calculateAngles();
        
    }
    
    private void calculateAngles(){
        angles = new ArrayList<Double>();
    }
    
    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public Shape getShape() {
        return originalShape;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        
        g.setPaint(color);  
        
        transformedCenter = at.createTransformedShape(rotationCenter);
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), 
                rotationCenter.getBounds().getCenterX(), 
                rotationCenter.getBounds().getCenterY());
        transformedShape = transform.createTransformedShape(originalShape);
        
        transformedShape = at.createTransformedShape(transformedShape);
        g.draw(transformedShape); 
        
        transformedTinyShapes.clear();
        
        for(Rectangle r : tinyShapes){
            Shape transformedRect = transform.createTransformedShape(r);
            transformedRect = at.createTransformedShape(transformedRect);
            g.draw(transformedRect);
            transformedTinyShapes.add(transformedRect);
        }

        if(mode != ShapeObjectBorder.MODECENTER){
            g.draw(transformedCenter); 
        }
        
    }

    @Override
    public void setLocation(int x, int y) { 
        rotationCenter.setLocation(x, y);
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) { 
        int new_x = (int) Math.round(rotationCenter.x-distance_x);
        int new_y = (int) Math.round(rotationCenter.y-distance_y);
        rotationCenter.setLocation(new_x, new_y);
    }

    @Override
    public int getX() {
        return originalShape.x;
    }

    @Override
    public int getY() {
        return originalShape.y;
    }

    @Override
    public int getWidth() {
        return originalShape.width;
    }

    @Override
    public int getHeight() {
        return originalShape.height;
    }

    @Override
    public void set(int x, int y, int width, int height) {
        // TODO Auto-generated method stub
        
    }


    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public byte checkShapes(Point p) {
        
        if(transformedCenter.contains(p)){
            return ShapeObjectBorder.INROTATIONCENTER;
        }
        int i = 0;
        for(Shape r : transformedTinyShapes){
            if(r.contains(p)){
                currentClicked = tinyShapes.get(i);
                return ShapeObjectBorder.INEDGES;
            }
            i++;
        }
        
        return ShapeObjectBorder.INNOTHING;
    }
    
    public Point getCenter(){
        int x = (int) Math.round(transformedCenter.getBounds().getCenterX());
        int y = (int) Math.round(transformedCenter.getBounds().getCenterY());
        
        Point p = new Point(x,y);
        return p;
    }
    
    public Point getOriginalCenter(){
        return new Point (rotationCenter.x, rotationCenter.y);
    }
    
    public Point getEdge(){
        int x = currentClicked.getBounds().x;
        int y = currentClicked.getBounds().y;
        return new Point(x, y);
    }

    public void setRotationCenterUpdate() {
    }

}
