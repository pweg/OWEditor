package org.jdesktop.wonderland.modules.oweditor.client.adapterinterfaces;

import java.awt.Point;

public interface CoordinateTranslatorInterface {
    
    public Point transformCoordinates(float x, float y, float width, float height);
    
    public int transformWidth(float width);
    
    public int transformHeight(float height);
    
    public double getScale();

}
