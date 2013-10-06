package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.EnvironmentObserverInterface;

public class EnvironmentObserver implements EnvironmentObserverInterface{

	private GUIController gc = null;
			
	EnvironmentObserver(GUIController gc){
		this.gc = gc;
	}
	
	@Override
	public void notifyWidthChange(int width) {
		gc.drawingPan.setNewWidth(width);
	}

	@Override
	public void notifyHeightChange(int height) {
		gc.drawingPan.setNewHeight(height);
	}

	@Override
	public void notifyMinXChange(int x) {
		gc.drawingPan.setNewMinX(x);
	}

	@Override
	public void notifyMinYChange(int y) {
		gc.drawingPan.setNewMinY(y);
	}
	

}
