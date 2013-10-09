package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.event.MouseEvent;

public interface MouseStrategy {
	
	public void mousePressed(MouseEvent e);
	
	public void mouseReleased(MouseEvent e);
	
	public void mouseDragged(MouseEvent e);

}
