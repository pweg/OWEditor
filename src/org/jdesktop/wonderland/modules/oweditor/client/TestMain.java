package org.jdesktop.wonderland.modules.oweditor.client;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controller.MainController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerAdapterInterface;

/**
 * Starts the testing without OW.
 * 
 * @author Patrick
 *
 */
public class TestMain {
	
	/*
	 * usefull patterns which probably can be implemented:
	 * 
	 *  observer
	 *  mediator
	 *  command
	 *  bridge
	 *  proxy
	 * 
	 */
	
	public static void main(String[] args) {
		MainControllerAdapterInterface main = new MainController();
		main.setVisible(true);
	}
}
