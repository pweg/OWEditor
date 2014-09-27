package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.IDataObject;

/**
 * The coordinate translator, which is used to translate the coordinates
 * to editor coordiantes and back.
 * @author Patrick
 */
public class CoordinateTranslator implements CoordinateTranslatorInterface{

    private final int globalScale = AdapterSettings.INITIAL_SCALE;
    
    @Override
    public Point transformCoordinatesInt(float x, float y, float width,
            float height) {

        int x_int = (int)Math.round(x * globalScale);
        int y_int = (int)Math.round(y * globalScale);
        
        return new Point(x_int, y_int);
    }

    @Override
    public Point2D.Double transformCoordinatesBack(float x, float y, float width,
            float height) {
        
        double x_d = x / globalScale;
        double y_d = y / globalScale;
        
        return new Point2D.Double(x_d,y_d);
    }
    
    public Vector3D transformCoordinatesBack(int x, int y, float width,
            float height) {

        float x_float = (float)x/globalScale;
        float y_float = (float)y/globalScale;
        
        return new Vector3D(x_float, y_float, 0);
    }

    @Override
    public double getScale(double scale) {
        return scale;
    }

    @Override
    public int transformWidth(float width) {
        return (int)Math.round(width * globalScale);
    }

    @Override
    public int transformHeight(float height) {
        return (int)Math.round(height * globalScale);
    }

    @Override
    public double getRotation(IDataObject object) {
        return object.getRotationZ();
    }
    

}
