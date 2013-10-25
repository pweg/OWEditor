package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;

public abstract class ShapeDraggingObject extends SimpleShapeObject{
    

    public abstract long getID();
    
    public abstract void setRotation(double rotation, Point p);
    
    public abstract double getRotation();
    
    public abstract void setState(stateDraggingShape state);
    
    public abstract void setRotationCenterUpdate();
    
    public abstract void setCollision(boolean col);
}
