package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.input;

public interface IInputToWindow {
    
    public static final byte NOMODE = 0;
    public static final byte COPY = 1;
    public static final byte PASTE = 2;
    public static final byte ROTATE = 3;
    public static final byte SCALE = 4;
    public static final byte CUT = 5;
    
    public void setInputMode(byte mode);

}
