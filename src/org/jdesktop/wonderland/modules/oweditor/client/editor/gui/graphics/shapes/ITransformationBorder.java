package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public interface ITransformationBorder {

    
    public static final byte MODEONECENTER   = 0;
    public static final byte MODEALLCENTER     = 1;
    
    public static final byte INNOTHING           = 0;
    public static final byte INROTATIONCENTER    = 1;
    public static final byte INEDGES             = 2;
    
    public static final byte UPPERLEFT      = 1;
    public static final byte UPPERRIGHT     = 2;
    public static final byte BOTTOMLEFT     = 3;
    public static final byte BOTTOMRIGHT    = 4;
    
    public Shape getTransformedShape();
    
    public Shape getShape();
    
    public void paintOriginal(Graphics2D g, AffineTransform at);
    
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
    
    public int getX();
    
    public int getY();
    
    public int getWidth();
    
    public int getHeight();
    
    public void setRotation(double rotation);
    
    public byte checkShapes(Point p);
    
    /**
     * Returns the original rotation center, which uses the coordinates
     * of the virtual world, except the scaling value. 
     * This can be needed when doing operations on other shapes.
     * @return The original rotation center as point.
     */
    public Point getOriginalCenter();
    
    public Point getEdge();
    
    public byte getCurrentClicked();
    
    public void setRotationCenterUpdate();
    
    public void setScale(double scale);

}
