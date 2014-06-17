package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Color;
import java.awt.Font;
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
     * BGCOLOR: color of the background.
     * OBJECTCOLOR: color of a shape.
     * OBJECTNAMECOLOR: color of the objects name.
     * BGOBJECTCOLOR: color of a shape which is in the background.
     * BGBORDERCOLOR: color of a shape border which is in the background.
     * AVATARCOLOR: color of an avatar shape.
     * DRAGGINGCOLOR: color of the shapes, when dragged.
     * COLLISIONCOLOR: shape color when a collision is detected.
     * 
     * SELECTIONCOLOR: color of a selected shape.
     * SELECTIONRECTCOLOR: color of the selection rectangle.
     */
    public static final Paint BGCOLOR = Color.white;
    public static final Paint OBJECTCOLOR = Color.gray;
    public static final Paint BGOBJECTCOLOR = new Color(232,232,232);
    public static final Paint BGBORDERCOLOR = Color.LIGHT_GRAY;
    public static final Paint OBJECTNAMECOLOR = Color.black;
    public static final Paint AVATARCOLOR = Color.LIGHT_GRAY;
    public static final Paint DRAGGINGCOLOR = Color.lightGray;
    public static final Paint COLLISIONCOLOR = Color.red;
    
    public static final Paint SELECTIONCOLOR = Color.red;
    public static final Paint SELECTIONRECTCOLOR = Color.lightGray;
    
    public static final Paint TOOLTIPTEXTCOLOR = Color.black;
    public static final Paint TOOLTIPBGCOLOR = new Color(255,255,200);
    
    /*
     * This color is used for the border surrounding the selected
     * objects, when rotating and scaling. The edges size is used 
     * for the tiny rectangles on the edges of the border.
     */
    public static final Paint SURROUNDINGBORDERCOLOR = Color.lightGray;
    public static final int SURROUNDINGBORDERMARGIN = 12;
    public static final int SURROUNDINGBORDEREDGESSIZE = 12;
    
    
    /*
     * Size and type of object name, shown in the drawing panel, as well
     * as the position in the shape and out of it, if the shape is too
     * small.
     */
    public static final int OBJECTNAMESIZE = 10;
    public static final String OBJECTNAMEFONTTYPE = "Verdana";
    
    public static final int NAMEPOSITIONINYADD = 8;
    public static final int NAMEPOSITIONINX = 5;
    public static final int NAMEPOSITIONINY = OBJECTNAMESIZE+NAMEPOSITIONINYADD;
    public static final int NAMEPOSITIONOUTX = 0;
    public static final int NAMEPOSITIONOUTY = -5;
    
    //The margin of images in objects and its watermark value.
    public static final int IMGMARGIN = 5;
    public static final int IMGMARGINPANEL = 3;
    public static final float ALPHA = 0.65f;
    
    /*
     * Tooltipin states the distance of the text to the border surrounding it.
     * Tooltipmagrin gives the distance of the tooltip to the mouse.
     */
    public static final int TOOLTIPIN = 4;
    public static final int TOOLTIPMARGIN = 10;
    
    /*
     * Width/Height, position and title of the window frame,
     * which will be shown, when the editor starts.
     */
    
    //main frame
    public static final int MAINWIDTH = 850;
    public static final int MAINHEIGHT = 650;
    public static final int MAINPOSX = 200;
    public static final int MAINPOSY = 180;
    
    //other frames
    public static final int FRAMEPOSX = MAINPOSX +100;
    public static final int FRAMEPOSY = MAINPOSY +50;
    
    /*
     * These are two values, which are used to add a empty space
     * around the existing shapes. They are used to divide the
     * frame size;
     */
    public static final int WIDTHDIVISOR = 2;
    public static final int HEIGHTDIVISOR = 2;
    
    /*
     * The zoomSpeed. It will add/remove this amount from the scale
     * when zoomed with the mouse wheel.
     */
    public static final double ZOOMSPEED = 0.1;
    
    /*
     * Settings for the Coordinates, which are shown
     * at the left corner.
     */
    public static final Font COORDFONT = new Font(GUISettings.OBJECTNAMEFONTTYPE, Font.PLAIN, 12); 
    public static final Color COORDCOLOR = Color.gray;
    
    /*
     * The color for image selection (aka the image button)
     */
    public static final Color IMAGESELECT = new Color(0.64f, 0.78f, 0.93f, 0.4f);
    
    public static final int MAXUNDO = 100;
}
