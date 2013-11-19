package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

import java.awt.Point;

public interface ExternalShapeToInputFacadeInterface {
    
    
    
    public void translation(int x, int y, Point start);

    public void translationSetUpdate();
    
    public void translationInitialization(Point p);

    /**
     * 
     * @return the point in the center of the copy
     */
    public Point copyInitialize();
    
    /**
     * Looks for copy shape, whether there were shapes
     * copied or not.
     * 
     * @return true, when there are copied shapes,
     * false otherwise
     */
    public boolean copyShapesExist();

    public void pasteInitialize();
    
    public void pasteTranslate(int x, int y, Point start);
    
    public void pasteInsertShapes();
    
    public void rotationInitialize();
    
    public void rotate(Point p);
    
    public void rotateSetUpdate();

    public void rotationCenterSetUpdate();

    public void rotationCenterTranslate(Point start, Point end);

    public void rotationSetUpdate();

    /**
     * Initializes the selection for the 
     * popup menu.
     * 
     * @param p the mouse point, where the popup menu
     * is going to get drawn.
     * 
     * @return true, if there is are shapes selected,
     * false otherwise. This is used for menu items to
     * be enabled/disabled.
     * 
     */
    public boolean popupInitialize(Point p);
    

    public void cleanAll();
    
    public void cleanHelpingShapes();

    public boolean selectionSwitch(Point p);

    public void clearCurSelection();

    public void deleteCurrentSelection();

    public void selectionUpdate(Point start, Point end);
    
    public void selectionReleased();

    public boolean isMouseInObject(Point p);

    public void isMouseInBorder(Point p);






}
