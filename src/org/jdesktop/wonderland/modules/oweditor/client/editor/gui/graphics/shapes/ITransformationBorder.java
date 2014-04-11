package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public interface ITransformationBorder {

    //rotation mode, with either one center, or every shape uses their
    //own center
    public static final byte MODEONECENTER   = 0;
    public static final byte MODEALLCENTER     = 1;
    
    //determining wheter a point is in the four edges, in the 
    //rotation center shape, or in nothing
    public static final byte INNOTHING           = 0;
    public static final byte INROTATIONCENTER    = 1;
    public static final byte INEDGES             = 2;
    
    //determines which of the four edges is used.
    public static final byte UPPERLEFT      = 1;
    public static final byte UPPERRIGHT     = 2;
    public static final byte BOTTOMLEFT     = 3;
    public static final byte BOTTOMRIGHT    = 4;
    
    /**
     * Returns the transformed shape.
     * 
     * @return The shape.
     */
    public Shape getTransformedShape();
    
    /**
     * Returns the original shape.
     * 
     * @return The shape.
     */
    public Shape getShape();
    
    /**
     * Paints the shape.
     * 
     * @param g Graphics2D instance.
     * @param at AffineTransform instance.
     */
    public void paintOriginal(Graphics2D g, AffineTransform at);
    
    /**
     * Moves the shape to the given position.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void setLocation(int x, int y);
    
    /**
     * This has to be called, after one scaling operation, in order
     * to do another scaling and the border not going back to its old
     * position.
     */
    public void scaleUpdate();

    /**
     * This translates the border, but only after scaling operations have 
     * been completed. 
     * 
     * @param distanceX The distance in x.
     * @param distanceY The distance in y.
     */
    public void setScaleDistance(double distanceX, double distanceY);

    /**
     * Translates the center rectangle, which is used as rotation center.
     * 
     * @param distanceX The distance in x direction. The sign should not
     * be changed, because it will be subtracted.
     * @param distanceY The distance in y direction. The sign should not
     * be changed, because it will be subtracted.
     */
    public void setCenterTranslation(double distanceX, double distanceY);
    
    /**
     * Returns the x coordinate.
     * 
     * @return The coordinate.
     */
    public int getX();
    
    /**
     * Returns the y coordinate.
     * 
     * @return The coordinate.
     */
    public int getY();
    
    /**
     * Returns the width.
     * 
     * @return The width.
     */
    public int getWidth();
    
    /**
     * Returns the height.
     * 
     * @return The height.
     */
    public int getHeight();
    
    /**
     * Sets the rotation.
     * 
     * @param rotation The rotation.
     */
    public void setRotation(double rotation);
    
    /**
     * Checks, if a point is in one of the shapes.
     * 
     * @param p The point.
     * @return The byte code, given above, where the point is, whether in
     * the rotation center, or in one of the edges.
     */
    public byte checkShapes(Point p);
    
    /**
     * Returns the original rotation center, which uses the coordinates
     * of the virtual world, except the scaling value. 
     * This can be needed when doing operations on other shapes.
     * @return The original rotation center as point.
     */
    public Point getOriginalCenter();
    
    /**
     * Returns the edge.
     * 
     * @return The edge as point.
     */
    public Point getEdge();
    
    /**
     * Returns where it currently was clicked.
     * 
     * @return The byte code, concerning the 4 edges.
     */
    public byte getCurrentClicked();
    
    /**
     * Updates the rotation center.
     */
    public void setRotationCenterUpdate();
    
    /**
     * Sets the scale.
     * 
     * @param scale The scale.
     */
    public void setScale(double scale);

}
