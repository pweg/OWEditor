package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * This is the class for the border surrounding the selection, when using
 * rotation or scaling.
 * 
 * @author Patrick
 */
public class TransformationBorder extends SimpleShapeObject implements ITransformationBorder{
    
    private Shape originalShape = null;
    private Shape transformedShape = null;
    
    private Shape originalCenter = null;
    private Shape transformedCenter = null;
    
    ArrayList<Shape> tinyShapes = null;
    ArrayList<Shape> transformedTinyShapes = null;
    
    private Shape currentClicked = null;
    private byte currentClickedCode = 0;
    
    private final Paint color = GUISettings.SURROUNDINGBORDERCOLOR;

    //these are the sizes for the tiny corner shapes and the rotation
    //center.
    private int tinySizeX = GUISettings.SURROUNDINGBORDEREDGESSIZE;
    private int tinySizeY = GUISettings.SURROUNDINGBORDEREDGESSIZE;
    private int tinySizeHalfX = 0;
    private int tinySizeHalfY = 0;

    private final int margin = GUISettings.SURROUNDINGBORDERMARGIN;
    private byte mode = 0;
    
    private double rotation = 0;
    
    private double scale = 1;
    
    private double scaleTranslationX = 0;
    private double scaleTranslationY = 0;

    private double initialScaleX = 0;
    private double initialScaleY = 0;
    
    
    public TransformationBorder(int x, int y, int width, int height, AffineTransform at,
            byte mode){
        this.mode = mode;
        
        initialScaleX = at.getScaleX();
        initialScaleY = at.getScaleY();
        
        //AffineTransform transform = new AffineTransform();
        //transform.scale(initialScaleX, initialScaleY);
        
        //calculate tiny half sizes for later use.
        tinySizeHalfX = (int) Math.round((tinySizeX/2)/initialScaleX);
        tinySizeHalfY = (int) Math.round((tinySizeY/2)/initialScaleY);
        tinySizeX = (int) Math.round(tinySizeX/initialScaleX);
        tinySizeY = (int) Math.round(tinySizeY/initialScaleY);

        tinyShapes = new ArrayList<Shape>();
        transformedTinyShapes = new ArrayList<Shape>();
        
        int center_x = (int) Math.round(x+(width/2));
        int center_y = (int) Math.round(y+(height/2));
        
        originalCenter = new Rectangle(center_x-tinySizeHalfX,
                center_y-tinySizeHalfY,
                tinySizeX,tinySizeY);    
        //rotationCenter = transform.createTransformedShape(rotationCenter);
        //rotationCenter = scaleShape(rotationCenter, initialScaleX, initialScaleY);

        x = x-margin;
        y = y-margin;
        originalShape = new Rectangle (x, y, width+2*margin, height+2*margin);
        //originalShape = transform.createTransformedShape(originalShape);

        tinyShapes = setTinyRectangle(originalShape);
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

        double rotationX = originalCenter.getBounds().getCenterX();
        double rotationY = originalCenter.getBounds().getCenterY();
        
        transformedShape = ShapeUtilities.rotateShape(originalShape, 
                rotation, rotationX, rotationY);
        
        transformedTinyShapes.clear();

        transformedShape = at.createTransformedShape(transformedShape);
        
        double globalScale = at.getScaleX();
        
        if(scale != 1){
            //translate();
            transformedShape = ShapeUtilities.scaleShape(transformedShape, scale, 
                    scaleTranslationX*globalScale, scaleTranslationY*globalScale);
                    
                    //scaleShape(transformedShape, scale,scale, globalScale);
            transformedTinyShapes = setTinyRectangle(transformedShape);
        }else{
            for(Shape r : tinyShapes){
                Shape transformedRect = ShapeUtilities.rotateShape(r, 
                        rotation, rotationX, rotationY);
                transformedRect = at.createTransformedShape(transformedRect);
                //transformedRect = scaleShapeCenter(transformedRect, at.getScaleX(), at.getScaleY());
                transformedTinyShapes.add(transformedRect);
            }
        }
        
        g.draw(transformedShape); 
        
        for(Shape r : transformedTinyShapes){
            g.draw(r);
        }

        transformedCenter = at.createTransformedShape(originalCenter);
        if(mode == TransformationBorder.MODEONECENTER){
            g.draw(transformedCenter); 
        }
    }

    @Override
    public void setLocation(int x, int y, double z) { 
        originalShape.getBounds().setLocation(x, y);
    }
    
    /**
     * Creates the four tiny rectangles that are at the corners of a
     * main rectangle. 
     * 
     * @param main The main rectangle as a shape instance, where the 
     * tiny rectangles will sit.
     * @param rights The list, where the rectangles will be saved.
     */
    private ArrayList<Shape> setTinyRectangle(Shape main){
        ArrayList<Shape> list = new ArrayList<Shape>();
        
        Rectangle bounds = main.getBounds();
        int x = bounds.x;
        int y = bounds.y;
        int width = bounds.width;
        int height = bounds.height;

        AffineTransform transform = new AffineTransform();
        transform.scale(initialScaleX, initialScaleY);
        
        Shape tiny = new Rectangle(x-tinySizeHalfX,y-tinySizeHalfY,
                tinySizeX,tinySizeY);
        list.add(tiny);
        
        Shape tiny2 = new Rectangle(x+width+-tinySizeHalfX,y-tinySizeHalfY,
                tinySizeX,tinySizeY);
        list.add(tiny2);
        
        Shape tiny3 = new Rectangle(x-tinySizeHalfX,y+height+
                -tinySizeHalfY,tinySizeX,tinySizeY);
        list.add(tiny3);
        
        Shape tiny4 = new Rectangle(x+width-tinySizeHalfX,
                y+height-tinySizeHalfY,tinySizeX,tinySizeY);
        list.add(tiny4);
        
        return list;
    }

    @Override
    public void scaleUpdate(){
        originalShape = ShapeUtilities.scaleShape(originalShape, scale, 
                scaleTranslationX, scaleTranslationY);
        tinyShapes = setTinyRectangle(originalShape);
        scale = 1;
    }

    @Override
    public void setScaleDistance(double distanceX, double distanceY) { 
        this.scaleTranslationX = distanceX;
        this.scaleTranslationY = distanceY;   
        
    }

    @Override
    public void setCenterTranslation(double distanceX, double distanceY) { 
        AffineTransform transform = new AffineTransform();
        transform.translate(-distanceX, -distanceY);
        originalCenter = transform.createTransformedShape(originalCenter);
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
        //should not be used
    }
    
    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public byte checkShapes(Point p) {

        if(originalCenter.contains(p)){
            return TransformationBorder.INROTATIONCENTER;
        }
        int i = 0;
        for(Shape r : tinyShapes){
            if(r.contains(p)){
                currentClicked = tinyShapes.get(i);
                currentClickedCode = (byte) (i+1);
                return TransformationBorder.INEDGES;
            }
            i++;
        }
        return TransformationBorder.INNOTHING;
    }

    @Override
    public Point getOriginalCenter(){
        int x = (int) Math.round(originalCenter.getBounds().getCenterX());
        int y = (int) Math.round(originalCenter.getBounds().getCenterY());
        return new Point (x, y);
    }

    @Override
    public Point getEdge(){
        int x = currentClicked.getBounds().x;
        int y = currentClicked.getBounds().y;
        return new Point(x, y);
    }

    @Override
    public byte getCurrentClicked(){
        return currentClickedCode;
    }

    @Override
    public void setRotationCenterUpdate() {  

        double rotationX = originalCenter.getBounds().getCenterX();
        double rotationY = originalCenter.getBounds().getCenterY();
        
        originalShape = ShapeUtilities.rotateShape(originalShape, 
                rotation, rotationX, rotationY);
        
        ArrayList<Shape> list = new ArrayList<Shape>();
        list.addAll(tinyShapes);
        tinyShapes.clear();
        
        for (Shape shape : list){
            shape = ShapeUtilities.rotateShape(shape, 
                    rotation, rotationX, rotationY);
            tinyShapes.add(shape);
        }
        rotation = 0;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {
        //should not be used 
    }

}
