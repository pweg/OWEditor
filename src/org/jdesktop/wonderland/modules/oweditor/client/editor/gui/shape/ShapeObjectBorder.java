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
    
    private double realScale = 1;
    private double workingScale = 1;
    
    private double distanceX = 0;
    private double distanceY = 0;

    private double initialScaleX = 0;
    private double initialScaleY = 0;
    
    
    public ShapeObjectBorder(int x, int y, int width, int height, AffineTransform at,
            byte mode){
        this.mode = mode;
        
        initialScaleX = at.getScaleX();
        initialScaleY = at.getScaleY();
        
        AffineTransform transform = new AffineTransform();
        transform.scale(initialScaleX, initialScaleY);
        
        
        tinySizeHalf = (int) Math.round(tinySize/2);
        tinySize = (int) Math.round(tinySize);

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

        setTinyRectangle(originalShape, tinyShapes);
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
        transform.scale(workingScale, workingScale);
        transform.rotate(Math.toRadians(rotation), 
                rotationCenter.getBounds().getCenterX(), 
                rotationCenter.getBounds().getCenterY());
        transformedShape = transform.createTransformedShape(originalShape);
        
        transformedTinyShapes.clear();
        
        
       
        if(workingScale != 1){
             translate();
        }else{
            for(Shape r : tinyShapes){
                Shape transformedRect = transform.createTransformedShape(r);
                transformedTinyShapes.add(transformedRect);
            }
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

    private void translate() {
        
        Point point = originalShape.getBounds().getLocation();        
        
        Rectangle bound = transformedShape.getBounds();

        int x = (int) Math.round((double)point.x - distanceX);
        int y = (int) Math.round((double)point.y - distanceY);
        
        /**
         * Not the best way to change the shape, but currently the simplest.
         */
        transformedShape = new Rectangle(x, y, bound.width, bound.height);
        setTinyRectangle(transformedShape, transformedTinyShapes);
        
    }
    
    /**
     * Creates the four tiny rectangles that are at the corners of a
     * main rectangle. 
     * 
     * @param main The main rectangle as a shape instance, where the 
     * tiny rectangles will sit.
     * @param list The list, where the rectangles will be saved.
     */
    private void setTinyRectangle(Shape main, ArrayList<Shape> list){
        list.clear();
        
        Rectangle bounds = main.getBounds();
        int x = bounds.x;
        int y = bounds.y;
        int width = bounds.width;
        int height = bounds.height;

        AffineTransform transform = new AffineTransform();
        transform.scale(initialScaleX, initialScaleY);
        
        Shape tiny = new Rectangle(x-tinySizeHalf,y-tinySizeHalf,tinySize,tinySize);
        list.add(tiny);
        
        Shape tiny2 = new Rectangle(x+width+-tinySizeHalf,y-tinySizeHalf,
                tinySize,tinySize);
        list.add(tiny2);
        
        Shape tiny3 = new Rectangle(x-tinySizeHalf,y+height+
                -tinySizeHalf,tinySize,tinySize);
        list.add(tiny3);
        
        Shape tiny4 = new Rectangle(x+width-tinySizeHalf,
                y+height-tinySizeHalf,tinySize,tinySize);
        list.add(tiny4);
    }
    
    /**
     * This has to be called, after one scaling operation, in order
     * to do another scaling and the border not going back to its old
     * position.
     */
    public void updateTranslation(){
        Rectangle bounds = transformedShape.getBounds();
        originalShape = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
        realScale -= (1-workingScale)*realScale;
        setTinyRectangle(originalShape, tinyShapes);
        workingScale = 1;
    }
    
    /**
     * This translates the border, but only after scaling operations have 
     * been completed. 
     */
    @Override
    public void setTranslation(double distance_x, double distance_y) { 
        this.distanceX = distance_x;
        this.distanceY = distance_y;   
        
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
        //workingScale = workingScale+(scale-this.scale); 
        this.workingScale = scale;
    }

}
