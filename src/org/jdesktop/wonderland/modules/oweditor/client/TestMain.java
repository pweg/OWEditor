package org.jdesktop.wonderland.modules.oweditor.client;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controler.MainControler;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controlerinterfaces.MainControlerAdapterInterface;

public class TestMain {
	
	
	public static void main(String[] args) {
		MainControlerAdapterInterface main = new MainControler();
		main.setVisible(true);
	}
}
