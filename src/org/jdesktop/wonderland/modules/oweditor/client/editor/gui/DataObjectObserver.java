package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import org.jdesktop.wonderland.modules.oweditor.client.editor.datainterfaces.DataObjectInterface;
import org.jdesktop.wonderland.modules.oweditor.client.editor.guiinterfaces.DataObjectObserverInterface;

public class DataObjectObserver implements 
								DataObjectObserverInterface{

	private GUIController gc = null;
	
	public DataObjectObserver(GUIController gc){
		this.gc = gc;
	}
	
	@Override
	public void notify(DataObjectInterface dataObject) {
        gc.sm.setDataUpdate(dataObject);
        gc.drawingPan.repaint();
	}

}
