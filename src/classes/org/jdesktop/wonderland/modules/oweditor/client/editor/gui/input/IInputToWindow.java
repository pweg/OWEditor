package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

/**
 * This interface is used to set the input mode, which comes from 
 * the window package..
 * 
 * @author Patrick
 */
public interface IInputToWindow {
    
    public static final byte NOMODE = 0;
    public static final byte COPY = 1;
    public static final byte PASTE = 2;
    public static final byte ROTATE = 3;
    public static final byte SCALE = 4;
    public static final byte CUT = 5;
    public static final byte TRANSLATE = 6;
    
    /**
     * Sets the input mode.
     * 
     * @param mode The mode.
     */
    public void setInputMode(byte mode);

    /**
     * Throws a key pressed event.
     * 
     * @param key The key-code of the pressed
     * key.
     */
    public void keyPressed(int key);
    
    /**
     * Returns the mouse listener.
     * 
     * @return The mouse listener.
     */
    public MouseInputAdapter getMouseListener();
    
    /**
     * Returns the key listener.
     * 
     * @return The key listener.
     */
    public KeyListener getKeyListener();
    
    /**
     * Returns the mouse wheel listener.
     * 
     * @return The mouse wheel listener.
     */
    public MouseWheelListener getMouseWheelListener();

}
