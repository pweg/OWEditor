package org.jdesktop.wonderland.modules.oweditor.client.dummyadapter;

import java.awt.Point;

import org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces.CoordinateTranslatorInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;

public class CoordinateTranslator implements CoordinateTranslatorInterface{

    private int globalScale = AdapterSettings.initalScale;
    
    @Override
    public Point transformCoordinates(float x, float y, float width,
            float height) {

        int x_int = (int)Math.round(x * globalScale);
        int y_int = (int)Math.round(y * globalScale);
        
        return new Point(x_int, y_int);
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
    public double getRotation(DataObjectInterface object) {
        return object.getRotationX();
    }

    public double transformXBack(double x) {
        return x/globalScale;
    }

    public double transformYBack(double y) {
        return y/globalScale;
    }
    

}
