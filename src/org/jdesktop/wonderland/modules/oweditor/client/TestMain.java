package org.jdesktop.wonderland.modules.oweditor.client;

import org.jdesktop.wonderland.modules.oweditor.client.editor.controller.MainController;
import org.jdesktop.wonderland.modules.oweditor.client.editor.controllerinterfaces.MainControllerPluginInterface;

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
     *  command: for gui and undo
     *  visitor: for gui shape/copy/translate manager etc.
     *  bridge
     *  proxy
     * 
     */
    
    public static void main(String[] args) {
        MainControllerPluginInterface main = new MainController();
        main.initialize(MainControllerPluginInterface.DUMMYADAPTER);
        main.setVisible(true);
    }
}
