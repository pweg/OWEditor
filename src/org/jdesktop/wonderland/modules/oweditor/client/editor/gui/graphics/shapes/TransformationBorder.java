package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * This is the class for the border sorounding the selection, when using
 * rotation or scaling.
 * 
 * @author Patrick
 *
 * Scaling part is from JFreeReport : a free Java reporting library
 * found under
 * http://www.java2s.com/Code/Java/2D-Graphics-GUI/ResizesortranslatesaShape.htm
 */
public class TransformationBorder extends SimpleShapeObject implements TransformationBorderInterface{
    

    private Shape originalShape = null;
    private Shape transformedShape = null;
    
    private Shape originalCenter = null;
    private Shape transformedCenter = null;
    
    ArrayList<Shape> tinyShapes = null;
    ArrayList<Shape> transformedTinyShapes = null;
    
    private Shape currentClicked = null;
    private byte currentClickedCode = 0;
    
    private Paint color = GUISettings.SURROUNDINGBORDERCOLOR;
    
    private int tinySize = GUISettings.SURROUNDINGBORDEREDGESSIZE;
    private int margin = GUISettings.SURROUNDINGBORDERMARGIN;
    private int tinySizeHalf = 0;
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
        
        
        tinySizeHalf = (int) Math.round(tinySize/2);
        tinySize = (int) Math.round(tinySize);

        tinyShapes = new ArrayList<Shape>();
        transformedTinyShapes = new ArrayList<Shape>();
        
        int center_x = (int) Math.round(x+(width/2));
        int center_y = (int) Math.round(y+(height/2));
        
        originalCenter = new Rectangle(center_x-(int)Math.round(tinySizeHalf/initialScaleX),
                center_y-(int)Math.round(tinySizeHalf/initialScaleY),
                (int) Math.round(tinySize/initialScaleX),
                (int) Math.round(tinySize/initialScaleY));    
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
        
        AffineTransform transform = new AffineTransform();
        //transform.scale(workingScale, workingScale);
        transform.rotate(Math.toRadians(rotation), 
                originalCenter.getBounds().getCenterX(), 
                originalCenter.getBounds().getCenterY());
        transformedShape = transform.createTransformedShape(originalShape);
        
        transformedTinyShapes.clear();

        transformedShape = at.createTransformedShape(transformedShape);
        
        double globalScale = at.getScaleX();
        
        if(scale != 1){
            //translate();
            transformedShape = scaleShape(transformedShape, scale,scale, globalScale);
            transformedTinyShapes = setTinyRectangle(transformedShape);
        }else{
            for(Shape r : tinyShapes){
                Shape transformedRect = transform.createTransformedShape(r);
                transformedRect = at.createTransformedShape(transformedRect);
                transformedRect = scaleShapeCenter(transformedRect, at.getScaleX(), at.getScaleY());
                transformedTinyShapes.add(transformedRect);
            }
        }
        
        g.draw(transformedShape); 
        
        for(Shape r : transformedTinyShapes){
            g.draw(r);
        }

        transformedCenter = at.createTransformedShape(originalCenter);
        if(mode != TransformationBorder.MODEALLCENTER){
            g.draw(transformedCenter); 
        }
        
    }

    @Override
    public void setLocation(int x, int y) { 
        originalShape.getBounds().setLocation(x, y);
    }
    
    /**
     * Scales the shape, without changing its coordinates and also
     * adding the scale translation, which is needed, when the border
     * needs to move its coordinates in order to give the impression it
     * is resizing to the mouse point.
     * 
     * @param shape The shape to scale
     * @param scaleX The scale x value.
     * @param scaleY The scale y value.
     * @return The scaled shape.
     */
    private Shape scaleShape(Shape shape, double scaleX, double scaleY,
            double globalScale){        
        Rectangle2D bounds = shape.getBounds2D();
        AffineTransform af = AffineTransform.getTranslateInstance(0 - bounds.getX(), 
                0 - bounds.getY());
        // apply normalisation translation ...
        Shape s = af.createTransformedShape(shape);
        af = AffineTransform.getScaleInstance(scaleX, scaleY);
        // apply scaling ...
        s = af.createTransformedShape(s);

        // now retranslate the shape to its original position ...
        af = AffineTransform.getTranslateInstance(bounds.getX()-scaleTranslationX*globalScale, 
                bounds.getY()-scaleTranslationY*globalScale);
        return af.createTransformedShape(s);
    }
    
    /**
     * Scales shapes in a way they keep their initial center value.
     * It does NOT add the scaling translation.
     * 
     * @param shape The shape to scale
     * @param scaleX The scale x value.
     * @param scaleY The scale y value.
     * @return The scaled shape.
     */
    private Shape scaleShapeCenter(Shape shape, double scaleX, double scaleY){
        Rectangle2D bounds = shape.getBounds2D();
        AffineTransform af = AffineTransform.getTranslateInstance(0 - bounds.getX(), 
                0 - bounds.getY());
        // apply normalisation translation ...
        Shape s = af.createTransformedShape(shape);
        
        AffineTransform at = new AffineTransform();
        at.scale(scaleX, scaleY);
        try {
            at = at.createInverse();
            // apply scaling ...
            s = at.createTransformedShape(s);

            // now retranslate the shape to its original position ...
            Rectangle2D bounds2 = s.getBounds2D();
            af = AffineTransform.getTranslateInstance(
                    bounds.getX()-(bounds2.getWidth()/2-bounds.getWidth()/2), 
                    bounds.getY()-(bounds2.getHeight()/2-bounds.getHeight()/2));
            return af.createTransformedShape(s);
        } catch (NoninvertibleTransformException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return shape;
        
    }
    
    /**
     * Creates the four tiny rectangles that are at the corners of a
     * main rectangle. 
     * 
     * @param main The main rectangle as a shape instance, where the 
     * tiny rectangles will sit.
     * @param list The list, where the rectangles will be saved.
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
        
        return list;
    }

    @Override
    public void scaleUpdate(){
        //Rectangle bounds = transformedShape.getBounds();
        //originalShape = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
        //realScale -= (1-workingScale)*realScale;
        originalShape = scaleShape(originalShape, scale,scale,1);
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
        // TODO Auto-generated method stub
        
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
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), 
                originalCenter.getBounds().getCenterX(), 
                originalCenter.getBounds().getCenterY());
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

    @Override
    public void setScale(double scale) {
        //workingScale = workingScale+(scale-this.scale); 
        this.scale = scale;
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {
        // TODO Auto-generated method stub
        
    }

}
