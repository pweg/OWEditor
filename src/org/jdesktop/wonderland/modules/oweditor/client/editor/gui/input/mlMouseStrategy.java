package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.Point;

public interface mlMouseStrategy {
    
    public void mousePressed(Point p);
    
    public void mouseReleased(Point p);
    
    public void mouseDragged(Point p);
    
    public void mouseMoved(Point p);

}
