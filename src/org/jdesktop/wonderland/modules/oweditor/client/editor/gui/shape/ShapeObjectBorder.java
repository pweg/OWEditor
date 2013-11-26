package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

public class ShapeObjectBorder extends SimpleShapeObject{
    
    public static final byte MODEONECENTER   = 0;
    public static final byte MODEALLCENTER     = 1;
    
    public static final byte INNOTHING           = 0;
    public static final byte INROTATIONCENTER    = 1;
    public static final byte INEDGES             = 2;
    
    public static final byte UPPERLEFT      = 1;
    public static final byte UPPERRIGHT     = 2;
    public static final byte BOTTOMLEFT     = 3;
    public static final byte BOTTOMRIGHT    = 4;
    

    private Shape originalShape = null;
    private Shape rotationCenter = null;
    
    private Shape transformedShape = null;
    
    ArrayList<Shape> tinyShapes = null;
    ArrayList<Shape> transformedTinyShapes = null;
    
    private Shape currentClicked = null;
    private byte currentClickedCode = 0;
    
    private Paint color = GUISettings.surroundingBorderColor;
    
    private int tinySize = GUISettings.surroundingBorderTinyShapeSize;
    private int margin = GUISettings.surroundingBorderMargin;
    private int tinySizeHalf = 0;
    private byte mode = 0;
    
    private double rotation = 0;
    private double scale = 1;
    private double distance_x = 0;
    private double distance_y = 0;
    
    
    public ShapeObjectBorder(int x, int y, int width, int height, AffineTransform at,
            byte mode){
        this.mode = mode;
        
        double scaleX = at.getScaleX();
        double scaleY = at.getScaleY();
        
        AffineTransform transform = new AffineTransform();
        transform.scale(scaleX, scaleY);
        
        
        tinySizeHalf = (int) Math.round(tinySize/scaleX/2);
        tinySize = (int) Math.round(tinySize/scaleX);

        tinyShapes = new ArrayList<Shape>();
        transformedTinyShapes = new ArrayList<Shape>();
        
        int center_x = (int) Math.round(x+(width/2));
        int center_y = (int) Math.round(y+(height/2));
        
        rotationCenter = new Rectangle(center_x-tinySizeHalf,
                center_y-tinySizeHalf,tinySize,tinySize);    
        rotationCenter = transform.createTransformedShape(rotationCenter);

        x = x-margin;
        y = y-margin;
        originalShape = new Rectangle (x, y, width+2*margin, height+2*margin);
        originalShape = transform.createTransformedShape(originalShape);
                
        Shape tiny = new Rectangle(x-tinySizeHalf,y-tinySizeHalf,tinySize,tinySize);
        tiny = transform.createTransformedShape(tiny);
        tinyShapes.add(tiny);
        
        Shape tiny2 = new Rectangle(x+width+margin*2-tinySizeHalf,y-tinySizeHalf,
                tinySize,tinySize);
        tiny2 = transform.createTransformedShape(tiny2);
        tinyShapes.add(tiny2);
        
        Shape tiny3 = new Rectangle(x-tinySizeHalf,y+height+margin*2
                -tinySizeHalf,tinySize,tinySize);
        tiny3 = transform.createTransformedShape(tiny3);
        tinyShapes.add(tiny3);
        
        Shape tiny4 = new Rectangle(x+width+margin*2-tinySizeHalf,
                y+height+margin*2-tinySizeHalf,tinySize,tinySize);
        tiny4 = transform.createTransformedShape(tiny4);
        tinyShapes.add(tiny4);

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
        
        AffineTransform transform = new AffineTransform();
        transform.scale(scale, scale);
        transform.rotate(Math.toRadians(rotation), 
                rotationCenter.getBounds().getCenterX(), 
                rotationCenter.getBounds().getCenterY());
        transformedShape = transform.createTransformedShape(originalShape);
        
        
        transformedTinyShapes.clear();
        
        for(Shape r : tinyShapes){
            Shape transformedRect = transform.createTransformedShape(r);
            transformedTinyShapes.add(transformedRect);
        }
        
        try {
            translate();
        } catch (NoninvertibleTransformException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        g.draw(transformedShape); 
        
        for(Shape r : transformedTinyShapes){
            g.draw(r);
        }

        if(mode != ShapeObjectBorder.MODEALLCENTER){
            g.draw(rotationCenter); 
        }
        
    }
    
    @Override
    public void setLocation(int x, int y) { 
        originalShape.getBounds().setLocation(x, y);
    }

    private void translate() throws NoninvertibleTransformException{
        AffineTransform transform2 = new AffineTransform();
        transform2.scale(scale, scale);
        Shape s = transform2.createTransformedShape(transformedShape);
        System.out.println("have to be "+s.getBounds().x);
        
        
        AffineTransform transform = new AffineTransform();
        Point point = originalShape.getBounds().getLocation();
        //transform2.inverseTransform(transformedShape.getBounds().getLocation(), point);
        
        System.out.println(point.x + " xxxxxxxxxxxxxxxxxxxxx");
        
        Rectangle bound = transformedShape.getBounds();
        
        transformedShape = new Rectangle(point.x, point.y, bound.width, bound.height);
        //transformedShape.getBounds().x = point.x;
        //transformedShape.getBounds().y = point.y;

        System.out.println("Now "+ transformedShape.getBounds().x);
        
        transform.translate(distance_x, distance_y);
        //transformedShape = transform.createTransformedShape(transformedShape);
        
        int i=0;
        for(Shape r : transformedTinyShapes){
            transformedTinyShapes.set(i, transform.createTransformedShape(r));
            i++;
        }
        
        

        transform2 = new AffineTransform();
        transform2.scale(scale, scale);
        s = transform2.createTransformedShape(transformedShape);
        System.out.println("scaled now " +s.getBounds().x);
    }
    
    @Override
    public void setTranslation(double distance_x, double distance_y) { 
        this.distance_x = distance_x;
        this.distance_y = distance_y;   
        
    }

    public void setCenterLocation(int x, int y) { 
        rotationCenter.getBounds().setLocation(x, y);
    }

    public void setCenterTranslation(double distance_x, double distance_y) { 
        AffineTransform transform = new AffineTransform();
        transform.translate(-distance_x, -distance_y);
        rotationCenter = transform.createTransformedShape(rotationCenter);
    }

    @Override
    public int getX() {
        return originalShape.getBounds().x;
    }

    @Override
    public int getY() {
        return originalShape.getBounds().y;
    }

    @Override
    public int getWidth() {
        return originalShape.getBounds().width;
    }

    @Override
    public int getHeight() {
        return originalShape.getBounds().height;
    }

    @Override
    public void set(int x, int y, int width, int height) {
        // TODO Auto-generated method stub
        
    }


    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public byte checkShapes(Point p) {
        
        if(rotationCenter.contains(p)){
            return ShapeObjectBorder.INROTATIONCENTER;
        }
        int i = 0;
        for(Shape r : transformedTinyShapes){
            if(r.contains(p)){
                currentClicked = tinyShapes.get(i);
                currentClickedCode = (byte) (i+1);
                return ShapeObjectBorder.INEDGES;
            }
            i++;
        }
        
        return ShapeObjectBorder.INNOTHING;
    }
    
    public Point getCenter(){
        int x = (int) Math.round(rotationCenter.getBounds().getCenterX());
        int y = (int) Math.round(rotationCenter.getBounds().getCenterY());
        
        Point p = new Point(x,y);
        return p;
    }
    
    public Point getOriginalCenter(){
        return new Point (rotationCenter.getBounds().x, rotationCenter.getBounds().y);
    }
    
    public Point getEdge(){
        int x = currentClicked.getBounds().x;
        int y = currentClicked.getBounds().y;
        return new Point(x, y);
    }
    
    public byte getCurrentClicked(){
        return currentClickedCode;
    }

    public void setRotationCenterUpdate() {        
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), 
                rotationCenter.getBounds().getCenterX(), 
                rotationCenter.getBounds().getCenterY());
        originalShape = transform.createTransformedShape(originalShape);
        
        ArrayList<Shape> list = new ArrayList<Shape>();
        list.addAll(tinyShapes);
        tinyShapes.clear();
        for (Shape shape : list){
            shape = transform.createTransformedShape(shape);
            tinyShapes.add(shape);
        }
        rotation = 0;
        
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

}
