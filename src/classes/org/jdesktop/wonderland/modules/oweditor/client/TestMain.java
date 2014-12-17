package org.jdesktop.wonderland.modules.oweditor.client;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controller.MainController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerPluginInterface;

/**
 * Starts the testing without OWL.
 * 
 * @author Patrick
 *
 */
public class TestMain {
    
    public static void main(String[] args) {
        MainControllerPluginInterface main = new MainController();
        main.initialize(MainControllerPluginInterface.DUMMYADAPTER);
        main.setVisible(true);
    }
}
