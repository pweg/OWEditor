package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class ExternalShapeToFrame implements ExternalShapeToFrameInterface{


    ShapeManager sm = null;
    
    ExternalShapeToFrame(ShapeManager sm){
        this.sm = sm;
    }

    @Override
    public void drawShapes(Graphics2D g2, AffineTransform at, double scale) {
        sm.drawShapes(g2, at, scale);
    }

}
