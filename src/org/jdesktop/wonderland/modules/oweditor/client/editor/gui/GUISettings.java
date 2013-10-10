package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Color;
import java.awt.Paint;

/**
 * A simple class with containing every static value for setting
 * up the editor.
 * 
 * @author Patrick
 *
 */
public class GUISettings {

    /*
     * Colors for shapes, background, selection rectangle and so one.
     */
    public static Paint backgroundColor = Color.white;
    public static Paint draggingColor = Color.lightGray;
    public static Paint draggingCollisionColor = Color.red;
    public static Paint objectColor = Color.gray;
    public static Paint objectNameColor = Color.black;
    
    public static Paint selectionBorderColor = Color.red;
    public static Paint selectionRectangleColor = Color.lightGray;
    
    public static Paint avatarColor = Color.LIGHT_GRAY;
    
    /*
     * Size and type of object name, shown in the drawing panel, as well
     * as the position in the shape and out of it, if the shape is too
     * small.
     */
    public static int objectNameSize = 12;
    public static String objectNameTextType = "Verdana";
    
    public static int namePositionInYAdd = 8;
    public static int namePositionInX = 5;
    public static int namePositionInY = objectNameSize+namePositionInYAdd;
    public static int namePositionOutX = 0;
    public static int namePositionOutY = -5;
    
    /*
     * Width/Height, position and title of the window frame,
     * which will be shown, when the editor starts.
     */
    public static int frameWidth = 800;
    public static int frameHeight = 600;
    public static int framePositionX = 200;
    public static int framePositionY = 200;
    public static String frameTitle = "Open Wonderland Editor" ;
    
    /*
     * These are two values, which are used to add a empty space
     * around the existing shapes. They are used to divide the
     * frame size;
     */
    public static int widthDivisor = 2;
    public static int heightDivisor = 2;
    
    /*
     * The zoomSpeed. It will add/remove this amount from the scale
     * when zoomed with the mouse wheel.
     */
    public static double zoomSpeed = 0.1;
}
